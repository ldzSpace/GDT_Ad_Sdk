package com.upay.sdk_gdt_ad.Utils;

import android.os.Environment;
import android.os.SystemClock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFileHandle {
	static boolean LOG = true;
	public static final String LOG_NAME = "expand_log.txt";

	// static {
	// LOG = BuildConfig.DEBUG;
	// Log.d("LogFileHandle", "debug " + (LOG ? "on" : "off"));
	// }
	public static void setIsLog(Boolean isLog) {
		LOG = isLog;
	}

	public static void writeLOG(String log) {
		if (isLog())
			writeLOGWithoutTime(getDateString(System.currentTimeMillis()) + "---->" + log);
	}

	public static void writeLOGwithElapsedRealtime(String log) {
		if (isLog())
			writeLOGWithoutTime(getDateString(System.currentTimeMillis()) + "--" + SystemClock.elapsedRealtime() + "---->" + log);
	}

	public static void writeLOGWithoutTime(String log) {
		if (isLog()) {
			String path = Environment.getExternalStorageDirectory().getPath();
			File file = new File(path + "/" + LOG_NAME);
			FileWriter fo = null;
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				fo = new FileWriter(file, true);
				fo.write(log + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fo.flush();
					fo.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public static Boolean isLog() {
		// return LOG || isForceLog();
		return LOG;
	}

	public static String getDateString(Long timemills) {
		SimpleDateFormat daFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return daFormat.format(new Date(timemills));
	}

	/**
	 * 判断是否进行日志打印
	 * 
	 * @return true : 打印日志 /false 不打印日志
	 */
	public static boolean isForceLog() {
		try {
			String path = Environment.getExternalStorageDirectory().getPath();
			File file = new File(path + "/" + LOG_NAME);
			if (file.exists()) {
				setIsLog(true);
				return LOG;
			} else {
				setIsLog(false);
				return LOG;
			}
		} catch (Exception e) {
		}

		return false;

	}

}
