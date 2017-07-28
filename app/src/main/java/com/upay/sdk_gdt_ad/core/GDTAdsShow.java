package com.upay.sdk_gdt_ad.core;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.upay.sdk_gdt_ad.inf.AdvListener;

/**
 * 广点通广告展示方法调用
 * 
 * @author cgliu
 * @data 2016/9/20
 *
 */
public class GDTAdsShow {

	public static GDTAdsShow mGdtAdsShow = null;

	public static GDTAdsShow getInstance() {
		if (mGdtAdsShow == null) {
			synchronized (GDTAdsShow.class) {
				if (mGdtAdsShow == null) {
					mGdtAdsShow = new GDTAdsShow();
				}
			}
		}
		return mGdtAdsShow;
	}

	/**
	 * Banner展示
	 * 
	 * @param oActivity
	 * @param container
	 * @param APPID
	 * @param BannerPosID
	 * @param advListener
	 */
	public void showBanner(Activity oActivity, ViewGroup container, String APPID, String BannerPosID, AdvListener advListener) {
		GDTBannerAD.showAD(oActivity, container, APPID, BannerPosID, advListener);
	}

	/**
	 * 插屏展示
	 * @param oActivity
	 * @param APPID
	 * @param InterteristalPosID
     */
	public void showInterstitialAD(Activity oActivity, String APPID, String InterteristalPosID) {
		GDTInterstitialAD.showAD(oActivity, APPID, InterteristalPosID);
	}

	/**
	 * 开屏展示
	 * @param oActivity
	 * @param APPID
	 * @param splashPosId
     */
	public void showSplash(Activity oActivity, ViewGroup container, View skipContainer, String APPID, String splashPosId) {
		GDTSplashAD.showAD(oActivity, container,skipContainer, APPID, splashPosId);
	}

	/**
	 * 展示视屏广告
	 * @param oActivity
	 * @param container
	 * @param APPID
	 * @param NativeVideoPosID
	 */
	public void showVedioAD(Activity oActivity, ViewGroup container, String APPID, String NativeVideoPosID){
		GDTVideoAD.getInstance().showVideoAD(oActivity, container, APPID,NativeVideoPosID);
	}

}
