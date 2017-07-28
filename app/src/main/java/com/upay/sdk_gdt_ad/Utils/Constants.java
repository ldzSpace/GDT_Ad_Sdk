package com.upay.sdk_gdt_ad.Utils;

public class Constants {

	public static final String BAIDU_ADV = "2579614";

	public static boolean isShow = false;
	public static final String SERVER_HOST = "";
	public static final String APPSTORE_DIR = "adver";

	public static final String PARAMETER_PACKAGENAME = "pkgname";
	public static final String PARAMETER_VERSIONCODE = "versioncode";
	public static final String PARAMETER_VERSIONNAME = "versionname";
	public static final String META_CHANNEL_ID = "AdvSdk_channel_id";

	public static final String ADSDK_KEY = "ADSDK_KEY";

	// UPAY生产环境HOST_BASE地址
	public static String HOST_BASE_URL = "api.1089u.cn";
	// UPAY生产环境BASE地址
	public static String BASE_URL = "http://" + HOST_BASE_URL + "/";

	// // 测试环境
	// public static String HOST_BASE_URL = "115.28.88.136";
	// // 测试环境BASE地址
	// public static String BASE_URL = "http://" + HOST_BASE_URL + ":8085/";

	// 版本号
	public static final String SDKVERSION = "1.2";
	public static final String DEXVERSION = "1.09";

	public static final long SIM_VALIDITY = 24 * 60 * 60 * 1000;

	/***
	 * platform
	 */
	public static final String GDT_TYPE_BANNER_UP = "1";
	public static final String GDT_TYPE_INTER = "2";
	public static final String GDT_TYPE_SPLASH = "3";
	public static final String GDT_TYPE_BANNER_DOWN = "4";
	public static final String GDT_TYPE_VIDEO = "5";

	/**
	 * handle
	 */
	public static final int HANDLE_WHAT1001 = 1001;
	public static final int HANDLE_WHAT1002 = 1002;
	public static final int HANDLE_WHAT1003 = 1003;
	public static final int HANDLE_WHAT1004 = 1004;
	public static final int HANDLE_WHAT1005 = 1005;

	public final static long DAY_MILLIS = 24 * 60 * 60 * 1000; // 一天的毫秒数
	public static long exitTime = 0;

	public static final String PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
	public static final String PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
	public static final String PACKAGE_REPLACE = "android.intent.action.PACKAGE_REPLACED";
	public static final String NETWORK_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

	/* load ads */

	public static final String ADS_NOTINIT = 101 + "";// 未初始化
	public static final String ADS_NOTNET = 102 + "";// 网络原因
	public static final String ADS_IS_SHOW = 103 + "";// 广告已经展示
	public static final String ADS_GET_EXCEP = 104 + "";// 广告获取异常
	public static final String ADS_IN_WHITELIST = 105 + "";// 白名单
	public static final String ADS_LOADFILE = 106 + "";// 广告加载失败
	public static final String ADS_CLASS_EXCEP = 107 + "";// 找不到类库

	public static final String RET_SUCCEED = "0";// 广告展示成功
	public static final String RET_CLOSE = "2";// 广告展示关闭
	public static final String RET_FAILED = "1";// 广告展示失败
	public static final String RET_CLICK = "3";// 广告点击
	public static final String RET_MISCLICK = "4";// 广告误点
	public static final String RET_DWONLOAD = "5";// 广告下载
	public static final String RET_INSTALL = "6";// 广告安装
	public static final String RET_ACTIVATE = "7";// 广告激活
	public static final String RET_TICK ="8";//倒计时回调，开屏，视屏需要

	public static final String EXURL_SHW = "shw";// 展示回调地址列表
	public static final String EXURL_CLK = "clk";// 点击回调地址列表
	public static final String EXURL_DL = "dl";// 下载回调地址列表
	public static final String EXURL_IST = "ist";// 安装回调地址列表
	public static final String EXURL_ACTV = "actv";// 激活回调地址列表

	public static final String EVENT_SHOW = "0";// 广告展示日志
	public static final String EVENT_CLICK = "1";// 广告点击日志
	public static final String EVENT_DOWNLOAD = "4";// 广告下载日志
	public static final String EVENT_INSTALL = "2";// 广告安装日志
	public static final String EVENT_ACTIVE = "5";// 广告激活日志
	public static final String EVENT_AD_RESULT = "8";// 广告处理结果日志

	public static final String MISCLICK_NO = "0";
	public static final String MISCLICK_YES = "1";
	public static final String MISCLICK_FAKE = "2";
	public static final String MISCLICK_HIDE_REAL = "-1";

}
