package com.mark.mobile.mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.mark.mobile.utils.LogUtils;
import com.mark.mobile.utils.StringUtils;


public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";

	private static final String STEP_CREATE = "create";

	private final Context mContext;
	private final String mDataBaseName;
	private final int mVersion;

	public DBHelper(Context context, String databaseName, CursorFactory factory, int version) {
		super(context, databaseName, factory, version);
		this.mContext = context;
		this.mDataBaseName = databaseName;
		this.mVersion = version;
	}

	protected String getSQLFilePath(String databaseName, int newVersion,
			int oldVersion) {
		StringBuilder sb = new StringBuilder();
		sb.append(databaseName).append("/");
		if (oldVersion > 0) {
			sb.append(oldVersion).append("-").append(newVersion);
		} else {
			sb.append(newVersion);
		}
		return sb.toString();
	}

	protected void execSqlOn(String phase, String version, SQLiteDatabase db) {
		db.beginTransaction();
		try {
			execSqls(db, "sql/" + phase + "/" + version);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			LogUtils.paintE("execSqlOn(" + phase + ", " + db + ") " + e.getMessage(),this);
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LogUtils.paintD(TAG, "onCreate");
		execSqlOn(STEP_CREATE, getSQLFilePath(mDataBaseName, /*mVersion*/1, 0), db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LogUtils.paintD(TAG, "onUpgrade oldVersion:"+oldVersion+", newVersion:"+newVersion);
		execSqlDel(STEP_CREATE, getSQLFilePath(mDataBaseName, 1, 0), db);
		onCreate(db);
	}

	private List<String> readFiles(InputStream is) throws IOException {
		List<String> result = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = br.readLine()) != null) {
				String line = str.trim();
				int len = line.length();
				int index = line.lastIndexOf(";");
				if (index != -1 && index == (len - 1)) {
					sb.append(str + "\n");
					result.add(StringUtils.convertAndReplace(sb.toString()));
					sb = new StringBuilder();
					continue;
				}
				sb.append(str + "\n");
			}
			return result;
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	private void execSqls(SQLiteDatabase db, String assetsDir)
			throws SQLException, IOException {
		AssetManager as = mContext.getResources().getAssets();
		String files[] = as.list(assetsDir);
		Arrays.sort(files);
		for (int i = 0; i < files.length; i++) {
			List<String> lines = readFiles(as.open(assetsDir + "/" + files[i]));
			for (String line : lines) {
				db.execSQL(line);
			}
		}
	}
	
	protected void execSqlDel(String phase, String version, SQLiteDatabase db) {
		db.beginTransaction();
		try {
			execDelSqls(db, "sql/" + phase + "/" + version);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			LogUtils.paintE("execSqlOn(" + phase + ", " + db + ") " + e.getMessage(),this);
		} finally {
			db.endTransaction();
		}
	}
	
	private void execDelSqls(SQLiteDatabase db, String assetsDir) throws SQLException, IOException {
		AssetManager as = mContext.getResources().getAssets();
		String files[] = as.list(assetsDir);
		Arrays.sort(files);
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i];
			String tableName = splitTableName(fileName);
			db.execSQL("drop table if exists "+ tableName);
		}
	}
	
	private String splitTableName(String fileName){
		String result = "";
		
		int start = fileName.lastIndexOf("_")+1;
		int end = fileName.lastIndexOf(".sql");
		
		if(start!=0 && end!=-1){
			result = fileName.substring(start, end);
		}else{
			throw new NullPointerException();
		}
		return result;
	}

}
