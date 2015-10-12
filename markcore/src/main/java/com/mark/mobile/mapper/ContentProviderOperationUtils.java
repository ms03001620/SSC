package com.mark.mobile.mapper;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderOperationUtils {

	public interface IOperationListener<T> {
		boolean isAddList(T object);

		ContentProviderOperation createOperation(Uri uri, ContentValues values);
	}

	/**
	 * delete line
	 *
	 * @param <T>
	 */
	public static class DefaultDeleteOperationListener<T> implements IOperationListener<T> {
		public boolean isAddList(T object) {
			return true;
		}

		public ContentProviderOperation createOperation(Uri uri, ContentValues values) {
			Long o = Long.valueOf(values.get(BaseColumns._ID).toString());
			
			Builder builder = ContentProviderOperation.newDelete(uri);
			builder.withSelection(BaseColumns._ID + "=?", new String[] {String.valueOf(o.longValue())});
			return builder.build();
		}
	}

	/**
	 * delete all
	 *
	 * @param <T>
	 */
	public static class DeleteAllOperationListener<T> implements IOperationListener<T> {
		public boolean isAddList(T object) {
			return true;
		}

		public ContentProviderOperation createOperation(Uri uri, ContentValues values) {
			Builder builder = ContentProviderOperation.newDelete(uri);
			// builder.withSelection(BaseColumns._ID + "=?", new String[] {
			// String.valueOf(values.get(BaseColumns._ID)) });
			return builder.build();
		}
	}

	/**
	 * insert line
	 *
	 * @param <T>
	 */
	public static class DefaultInsertOperationListener<T> implements IOperationListener<T> {
		public boolean isAddList(T object) {
			return true;
		}

		public ContentProviderOperation createOperation(Uri uri, ContentValues values) {
			Builder builder = ContentProviderOperation.newInsert(uri);
			builder.withValues(values);
			return builder.build();
		}
	}
	
	/**
	 * update line
	 *
	 * @param <T>
	 */
	public static class DefaultUpdateOperationListener<T> implements IOperationListener<T> {
		public boolean isAddList(T object) {
			return true;
		}

		public ContentProviderOperation createOperation(Uri uri, ContentValues values) {
			Builder builder = ContentProviderOperation.newUpdate(uri);
			//builder.withSelection("json=?", new String[] { String.valueOf(values.get("json"))});
			builder.withSelection(BaseColumns._ID + "=?", new String[] { String.valueOf(0)});
			builder.withValues(values);
			return builder.build();
		}
	}
	
	public static class DefaultUpdateOperationListenerById<T> implements IOperationListener<T> {
		public boolean isAddList(T object) {
			return true;
		}

		public ContentProviderOperation createOperation(Uri uri, ContentValues values) {
			Long o = Long.valueOf(values.get(BaseColumns._ID).toString());
			Builder builder = null;
			builder = ContentProviderOperation.newUpdate(uri);
			builder.withSelection(BaseColumns._ID + "=?", new String[] {String.valueOf(o.longValue())});

			builder.withValues(values);
			return builder.build();
		}
	}
	




	public static <T> List<ContentProviderOperation> getOperations(Uri uri, T[] objects, IOperationListener<T> listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		if (objects != null && objects.length >= 1) {
			for (T obj : objects) {
				ContentValues values = null;//ContentMapper.getDefaultContentMapper().toContentValues(obj);
				if (listener.isAddList(obj)) {
					ContentProviderOperation operation = listener.createOperation(uri, values);
					if (operation == null) {
						throw new NullPointerException();
					}

					operations.add(operation);
				}
			}
		}

		return operations;
	}

	public static <T> List<ContentProviderOperation> getOperations(Uri uri, IOperationListener<T> listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();

		ContentProviderOperation operation = listener.createOperation(uri, null);
		if (operation == null) {
			throw new NullPointerException();
		}

		operations.add(operation);

		return operations;
	}
	
	public static <T> List<ContentProviderOperation> getOperations(List<Uri> uris, IOperationListener<T> listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		for(Uri uri:uris){
			ContentProviderOperation operation = listener.createOperation(uri, null);
			if (operation == null) {
				throw new NullPointerException();
			}
			operations.add(operation);
		}

		return operations;
	}

	public static <T> List<ContentProviderOperation> getOperations(Uri uri, List<T> objects, IOperationListener<T> listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		if (objects != null && objects.size() >= 1) {
			for (T obj : objects) {
				ContentValues values = null;//ContentMapper.getDefaultContentMapper().toContentValues(obj);
				if (listener.isAddList(obj)) {
					ContentProviderOperation operation = listener.createOperation(uri, values);
					if (operation == null) {
						throw new NullPointerException();
					}

					operations.add(operation);
				}
			}
		}

		return operations;
	}
	
	public static <T extends Updateable> List<ContentProviderOperation> getOperations(Uri uri, T objects, IOperationListener<T> listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		ContentValues values = new ContentValues();
		Gson gson = ContentMapper.getDefaultContentMapper().getGson();
		String json = gson.toJson(objects);
		
		values.put("json", json);
		values.put(BaseColumns._ID, objects.getId());

		ContentProviderOperation operation = listener.createOperation(uri, values);
		if (operation == null) {
			throw new NullPointerException();
		}
		operations.add(operation);
		return operations;
	}
	
	public static <T> List<ContentProviderOperation> getOperations(Uri uri, T objects, IOperationListener<T> listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		//ContentValues values = null;
		//ContentMapper.getDefaultContentMapper().toContentValues(objects);
		ContentValues values = new ContentValues();
		Gson gson = ContentMapper.getDefaultContentMapper().getGson();
		String json = gson.toJson(objects);
		
		values.put("json", json);
		
		if (listener.isAddList(objects)) {
			
			ContentProviderOperation operation = listener.createOperation(uri, values);
			if (operation == null) {
				throw new NullPointerException();
			}
			operations.add(operation);
		}

		return operations;
	}
}
