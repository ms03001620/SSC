package com.mark.mobile.mapper;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mark.mobile.MainApp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContentMapper {
	private static ContentMapper defaultContentMapper;
	private static Gson defaultGson;
	private static GsonBuilder gsonBuilder;
	
	public static synchronized ContentMapper getDefaultContentMapper() {
		if (defaultContentMapper == null) {
			ContentMapper mapper = new ContentMapper();
			defaultContentMapper = mapper;
			gsonBuilder = new GsonBuilder(); 
			gsonBuilder.registerTypeAdapter(Uri.class, URIStringConvertor.INSTANCE);
			defaultGson = gsonBuilder.create();
		}
		return defaultContentMapper;
	}
	
	public Gson getGson(){
		return defaultGson;
	}
	
	public static void registerTypeAdapter(Type type, Object typeAdapter){
		gsonBuilder.registerTypeAdapter(type, typeAdapter);
		defaultGson = gsonBuilder.create();
	}
	
	public <T> List<T> toObjectList(Class<? extends T> cls, Cursor c) {
		List<T> list = new ArrayList<T>();
		c.moveToPosition(-1);
		while (c.moveToNext()) {
			T bean = toObject(cls, c);
			list.add(bean);
		}
		return list;
	}
	
	
	public <T> T toObject(Class<? extends T> cls, Cursor c) {
		T bean = null;
		int id = c.getColumnIndexOrThrow("json");
		String json = c.getString(id);
		bean = defaultGson.fromJson(json, cls);
		return bean;
	}

	public <T> List<T> getLocalData(ContentProviderClient provider, Class<T> cls, Uri uri) throws RemoteException {
		List<T> list = null;
		Cursor c = null;
		try {
			c = provider.query(uri, null, null, null, null);
			list = ContentMapper.getDefaultContentMapper().toObjectList(cls, c);
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return list != null ? list : new ArrayList<T>();
	}

	/**
	 * 判断target是否和uri同对象
	 * @param provider
	 * @param target
	 * @param uri
	 * @param <T>
	 * @return
	 * @throws RemoteException
	 */
	public <T> boolean isNew(ContentProviderClient provider, T target, Uri uri) throws RemoteException {
		Cursor c = null;
		try {
			c = provider.query(uri, null, null, null, null);
			c.moveToPosition(-1);
			while (c.moveToNext()) {
				int id = c.getColumnIndexOrThrow("json");
				String jsonLocal = c.getString(id);
				String jsonTarget = defaultGson.toJson(target);
				if(jsonLocal.equals(jsonTarget)){
					return true;
				}
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return false;
	}

	public static <T> void overwrite(T datas, Uri uri, Class<T> cls) {
		ContentResolver contentResolver = MainApp.getContext().getContentResolver();
		ContentProviderClient provider = contentResolver.acquireContentProviderClient(uri);
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		try {
			boolean isNew = defaultContentMapper.isNew(provider, datas, uri);

			if(!isNew){
				operations.addAll(ContentProviderOperationUtils.getOperations(uri, new ContentProviderOperationUtils.DeleteAllOperationListener<T>() {
					public boolean isAddList(T object) {
						return true;
					}
				}));
				operations.addAll(ContentProviderOperationUtils.getOperations(uri, datas, new ContentProviderOperationUtils.DefaultInsertOperationListener<T>() {
					public boolean isAddList(T object) {
						return true;
					}
				}));
				provider.applyBatch(operations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			provider.release();
		}
	}
}
