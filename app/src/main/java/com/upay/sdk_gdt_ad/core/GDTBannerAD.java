package com.upay.sdk_gdt_ad.core;

import android.app.Activity;
import android.view.ViewGroup;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.upay.sdk_gdt_ad.Utils.Constants;
import com.upay.sdk_gdt_ad.Utils.LogUtils;
import com.upay.sdk_gdt_ad.inf.AdvListener;

/**
 * @author: cgliu
 * @date: 2016年9月11日
 * @description:
 */
public class GDTBannerAD {

	private static String TAG = "GDTBanner";
	// banner顶部展示
	public static final int SHOW_TOP = 1;

	private static BannerView banner;

	/**
	 * 展示广告
	 * @param oActivity
	 * @param container
	 * @param APPID
	 * @param BannerPosID
	 * @param advListener
     */
	public static void showAD(final Activity oActivity, ViewGroup container, String APPID, String BannerPosID, AdvListener advListener) {
		initBanner(oActivity, container, APPID, BannerPosID, advListener);
	}

	/**
	 * 加载banner广告
	 * @param oActivity
	 * @param container
	 * @param APPID
	 * @param BannerPosID
     * @param advListener
     */
	private static void initBanner(Activity oActivity, ViewGroup container, String APPID, String BannerPosID, final AdvListener advListener) {
		Constants.isShow = true;

		banner = new BannerView(oActivity, ADSize.BANNER, APPID, BannerPosID);
		// 设置广告轮播时间，为0或30~120之间的数字，单位为s,0标识不自动轮播
		banner.setRefresh(30);

		banner.setADListener(new AbstractBannerADListener() {

			@Override
			public void onNoAD(int arg0) {
				LogUtils.v(TAG, "GDTBanner展示失败");
				advListener.onResult(String.valueOf(SHOW_TOP), Constants.RET_FAILED, "", Constants.MISCLICK_NO, "", "", "");
			}

			@Override
			public void onADReceiv() {
				LogUtils.v(TAG, "GDTBanner展示成功");
				advListener.onResult(String.valueOf(SHOW_TOP), Constants.RET_SUCCEED, "", Constants.MISCLICK_NO, "", "", "");
			}

			@Override
			public void onADClicked() {
				advListener.onResult(String.valueOf(SHOW_TOP), Constants.RET_CLICK, "", Constants.MISCLICK_NO, "", "", "");
				LogUtils.v(TAG, "GDTBanner点击成功");
			}

			@Override
			public void onADClosed() {
				super.onADClosed();
				LogUtils.v(TAG, "GDTBanner点击关闭");
				advListener.onResult(String.valueOf(SHOW_TOP), Constants.RET_CLOSE, "", Constants.MISCLICK_NO, "", "", "");
				Constants.isShow = false;
			}
		});
		container.addView(banner);

		// BannerDialog.showBanner(oActivity, style, banner);
		banner.loadAD();
	}

	public static void GDTBannerDestroy() {
		if (banner != null) {
			banner.destroy();
			banner = null;
		}
	}
}
