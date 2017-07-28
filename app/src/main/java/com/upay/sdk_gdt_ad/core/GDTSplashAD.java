package com.upay.sdk_gdt_ad.core;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.upay.sdk_gdt_ad.Utils.Constants;
import com.upay.sdk_gdt_ad.Utils.LogUtils;
import com.upay.sdk_gdt_ad.adApi.UpayAd;

/**
 * 广点通开屏广告
 * 
 * @author cgliu
 *
 */
public class GDTSplashAD {
	public static boolean canJump = false;
	private static RelativeLayout container = null;
	private static RelativeLayout.LayoutParams rl = null;
	protected static final String TAG = "GDTSplash";
	private static SplashAD splashAD;

	/**
	 * 展示广告
	 * @param oActivity
	 * @param APPID
	 * @param SplashPosID
     */
	public static void showAD(final Activity oActivity, ViewGroup container,View skipContainer, String APPID, String SplashPosID) {
		initSplash(oActivity, container, skipContainer, APPID, SplashPosID);
	}

	/**
	 * 加载开屏广告
	 * @param oActivity
	 * @param APPID
	 * @param SplashPosID
     */
	private static void initSplash(final Activity oActivity, ViewGroup container, View skipContainer,String APPID, String SplashPosID) {
		splashAD = new SplashAD(oActivity, container, skipContainer, APPID, SplashPosID, new SplashADListener() {
			@Override
			public void onNoAD(int arg0) {
				LogUtils.v(TAG, "GDTSplash展示失败--->" + arg0);
				UpayAd.advListener.onResult("3", Constants.RET_FAILED, "", Constants.MISCLICK_NO, "", "", "");
			}

			@Override
			public void onADPresent() {
				LogUtils.v(TAG, "GDTSplash展示成功");
				UpayAd.advListener.onResult("3", Constants.RET_SUCCEED, "", Constants.MISCLICK_NO, "", "", "");
			}

			@Override
			public void onADDismissed() {
				LogUtils.v(TAG, "container.removeAllViews()");
				// next();
				UpayAd.advListener.onResult("3", Constants.RET_CLOSE, "", Constants.MISCLICK_NO, "", "", "");
			}

			@Override
			public void onADClicked() {
				LogUtils.v(TAG, "GDTSplash点击成功");
				UpayAd.advListener.onResult("3", Constants.RET_CLICK, "", Constants.MISCLICK_NO, "", "", "");
			}
			/**
			 * 倒计时回调，返回广告还将被展示的剩余时间。
			 * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
			 *
			 * @param millisUntilFinished 剩余毫秒数
			 */
			@Override
			public void onADTick(long millisUntilFinished) {
				Log.i("AD_DEMO", "SplashADTick " + millisUntilFinished + "ms");
				UpayAd.advListener.onResult("3", Constants.RET_TICK, "", Constants.MISCLICK_NO, "", "", "");
			}
		},0);

	}

	public static void splashADDestroy() {
		if (splashAD != null) {
			container.removeAllViews();
			splashAD = null;
		}
	}
}
