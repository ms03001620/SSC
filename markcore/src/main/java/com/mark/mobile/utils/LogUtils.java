package com.mark.mobile.utils;

import android.util.Log;


import com.mark.mobile.MainApp;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class LogUtils {

	private static final String TAG = "RH";

	private LogUtils() {
	}

	public static void paintE(String msg, Object o) {
		paint(Log.ERROR, msg, o, null);
	}
	
	public static void paintE(String msg, String tag) {
		paint(Log.ERROR, msg, tag, null);
	}

	public static void paintW(String msg, Object o) {
		paint(Log.WARN, msg, o, null);
	}

	public static void paintD(String msg, Object o) {
		paint(Log.DEBUG, msg, o, null);
	}

	public static void paintD(String tag, String msg) {
		outputLog(Log.DEBUG, msg, null, tag);
	}
	public static void paintW(String tag, String msg) {
		outputLog(Log.WARN, msg, null, tag);
	}
	public static void paintE(String tag, String msg, Exception e) {
		outputLog(Log.ERROR, msg, e, tag);
	}

	public static void paint(int type, String msg, String tag) {
		outputLog(type, tag + ": " + msg, null);
	}

	public static void paint(int type, String msg, Object o, Exception e) {
		String className = o.getClass().getSimpleName();
		msg = className + ": " + msg;
		outputLog(type, msg, e);
	}
	
	public static void paint(int type, String msg, String className, Exception e) {
		msg = className + ": " + msg;
		outputLog(type, msg, e);
	}

	private static void outputLog(int type, String msg, Exception e) {
		outputLog(type, msg, e, TAG);
	}

	private static void outputLog(int type, String msg, Exception e, String tag) {
		if (!isDebug()){
			return;
		}

		switch (type) {
		case Log.ASSERT:
			break;
		case Log.ERROR:
			Log.e(tag, msg, e);
			break;
		case Log.WARN:
			Log.w(tag, msg);
			break;
		case Log.DEBUG:
			Log.d(tag, msg);
			break;
		case Log.INFO:
			Log.i(tag, msg);
			break;
		case Log.VERBOSE:
			Log.v(tag, msg);
			break;
		}
	}

	public static void outputLog(String directorypath, String msg) {
		File directory = new File(directorypath);
		if (!directory.isDirectory()) {
			directory.mkdirs();
		}

		File f = new File(directorypath + "/log.txt");
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(f, true));
			writer.append(msg + "\r\n");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			outputLog(Log.ERROR, "outputLog : Exception", e);
		}
	}

	public static boolean isDebug() {
		return MainApp.isDebug();
	}
}
