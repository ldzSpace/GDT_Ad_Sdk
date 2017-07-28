package com.upay.sdk_gdt_ad.core;

import android.app.Activity;
import android.content.Context;

import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.upay.sdk_gdt_ad.Utils.Constants;
import com.upay.sdk_gdt_ad.Utils.LogUtils;
import com.upay.sdk_gdt_ad.adApi.UpayAd;

/**
 * 广点通插屏广告
 * 
 * @author cgliu
 *
 */
public class GDTInterstitialAD {

	protected static final String TAG = "GDTInterstitialAD";

	private static InterstitialAD iad;

	/**
	 * 展示广告
	 * 
	 * @param context
	 */
	public static void showAD(Context context, String APPID, String InterteristalPosID) {

		iad = new InterstitialAD((Activity) context, APPID, InterteristalPosID);

		iad.setADListener(new AbstractInterstitialADListener() {

			@Override
			public void onADExposure() {
				super.onADExposure();
				LogUtils.v(TAG, "onADExposure");
			}

			@Override
			public void onADLeftApplication() {
				super.onADLeftApplication();
				LogUtils.v(TAG, "onADLeftApplication");
			}

			@Override
			public void onADOpened() {
				super.onADOpened();
				LogUtils.v(TAG, "onADOpened");
			}

			@Override
			public void onADClosed() {
				super.onADClosed();
				LogUtils.v(TAG, "GDTInterstitialAD广告关闭--->bodyActivity关闭");
				UpayAd.advListener.onResult("2", Constants.RET_CLOSE, "", Constants.MISCLICK_NO, "", "", "");
			}

			@Override
			public void onNoAD(int arg0) {
				LogUtils.v(TAG, "GDTInterstitialAD展示失败--->" + arg0);
				UpayAd.advListener.onResult("2", Constants.RET_FAILED, "", Constants.MISCLICK_NO, "", "", "");
			}

			@Override
			public void onADReceive() {
				/*
				 * 展示插屏广告，仅在回调接口的adreceive事件发生后调用才有效。
				 */
				LogUtils.v(TAG, "GDTInterstitialAD展示成功");
				iad.show();
				UpayAd.advListener.onResult("2", Constants.RET_SUCCEED, "", Constants.MISCLICK_NO, "", "", "");
			}

			@Override
			public void onADClicked() {
				LogUtils.v(TAG, "GDTInterstitialAD点击成功");
				UpayAd.advListener.onResult("2", Constants.RET_CLICK, "", Constants.MISCLICK_NO, "", "", "");
			}
		});
		// 请求插屏广告，每次重新请求都可以调用此方法。
		iad.loadAD();
	}

	public static void iadDestroy() {
		if (iad != null) {
			iad.closePopupWindow();
			iad.destory();
			iad = null;
			LogUtils.v(TAG, "GDTInterstitialAD广告销毁--->bodyActivity关闭");
		}
	}

}
