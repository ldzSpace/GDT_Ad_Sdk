package com.upay.sdk_gdt_ad;

import android.app.Application;
import android.content.Context;

import com.jyad.AdManager;

/**
 * ================================================
 * 作    者：刘大志
 * 版    本：1.0
 * 创建日期：2017/7/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class UpayAdApp extends Application {
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		AdManager.instance().init(base);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

}
