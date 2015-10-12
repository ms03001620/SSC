package com.mark.mobile.mapper;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.mark.mobile.utils.LogUtils;

import java.util.ArrayList;

public abstract class SQLiteContentProvider extends ContentProvider {

	private final static String TAG = "SQLiteContentProvider";

	protected final String databaseName;
	protected final int version;
	protected SQLiteOpenHelper dbHelper;
	
	public SQLiteContentProvider(String databaseName, int version) {
		super();
		this.databaseName = databaseName;
		this.version = version;
	}
	
	protected SQLiteOpenHelper createSQLiteOpenHelper() {
		return new DBHelper(getContext(), databaseName, null, version);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if (uri == null) {
			throw new IllegalArgumentException("unsupported uri:" + uri);
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		final int affected = db.delete(getTableName(uri), selection, selectionArgs);
		if(affected>0){
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return affected;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (uri == null) {
			throw new IllegalArgumentException("unsupported uri:" + uri);
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db != null){
			String table = getTableName(uri);
			long id = db.insert(table, null, values);
			if(id>-1){
				getContext().getContentResolver().notifyChange(uri, null);
				return ContentUris.withAppendedId(uri, id);
			}else{
				LogUtils.paintW("insert not effect uri:"+uri, this);
			}
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		dbHelper = createSQLiteOpenHelper();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
		if (uri == null) {
			LogUtils.paintE("unsupported uri", this);
			throw new IllegalArgumentException("unsupported uri:" + uri);
		}
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(getTableName(uri));
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
		if (uri == null) {
			LogUtils.paintE("unsupported uri", this);
			throw new IllegalArgumentException("unsupported uri:" + uri);
		}
		String table = getTableName(uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		final int affected = db.update(table, values, whereClause, whereArgs);
		if(affected>0){
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return affected;
	}
	
	@Override
	public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentProviderResult[] results = super.applyBatch(operations);
			db.setTransactionSuccessful();
			return results;
		} finally {
			db.endTransaction();
		}
	}
	
	protected abstract String getTableName(Uri uri);

}
