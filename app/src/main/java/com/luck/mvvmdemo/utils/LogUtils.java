package com.luck.mvvmdemo.utils;

import android.util.Log;

import com.luck.mvvmdemo.BuildConfig;

public class LogUtils {
	private static final String TAG = "LogUtils";

	//是否输出日志的开关
	public static boolean ISDEBUG = BuildConfig.DEBUG;

	public static void i(String TAG, String msg) {
		if (ISDEBUG) {
			Log.i(TAG, msg);
		}
	}

	public static void i(String TAG, String msg, Throwable e) {
		if (ISDEBUG) {
			Log.i(TAG, msg, e);
		}
	}

	public static void e(String TAG, String msg) {
		if (ISDEBUG) {
			Log.e(TAG, msg);
		}
	}

	public static void e(String TAG, String msg, Throwable e) {
		if (ISDEBUG) {
			Log.e(TAG, msg, e);
		}
	}

	public static void d(String TAG, String msg) {
		if (ISDEBUG) {
			Log.d(TAG, msg);
		}
	}


	public static void i( String msg) {
		if (ISDEBUG) {
			Log.i(TAG, msg);
		}
	}

	public static void i( String msg, Throwable e) {
		if (ISDEBUG) {
			Log.i(TAG, msg, e);
		}
	}

	public static void e( String msg) {
		if (ISDEBUG) {
			Log.e(TAG, msg);
		}
	}

	public static void e( String msg, Throwable e) {
		if (ISDEBUG) {
			Log.e(TAG, msg, e);
		}
	}

	public static void d(String msg) {
		if (ISDEBUG) {
			Log.d(TAG, msg);
		}
	}
}
