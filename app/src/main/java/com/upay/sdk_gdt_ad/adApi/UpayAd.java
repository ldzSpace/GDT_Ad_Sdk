package com.upay.sdk_gdt_ad.adApi;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.qq.e.ads.cfg.MultiProcessFlag;
import com.upay.sdk_gdt_ad.Utils.Constants;
import com.upay.sdk_gdt_ad.Utils.LogUtils;
import com.upay.sdk_gdt_ad.core.GDTAdsShow;
import com.upay.sdk_gdt_ad.core.GDTBannerAD;
import com.upay.sdk_gdt_ad.core.GDTInterstitialAD;
import com.upay.sdk_gdt_ad.core.GDTSplashAD;
import com.upay.sdk_gdt_ad.core.GDTVideoAD;
import com.upay.sdk_gdt_ad.inf.AdvListener;


/**
 * ================================================
 * 作    者：刘大志
 * 版    本：1.0
 * 创建日期：2017/7/28
 * 描    述：sdk入口
 * 修订历史：
 * ================================================
 */
public class UpayAd {
	private static final String TAG = "UpayAd";
	private static UpayAdListener listener;
	private static String appId;
	private static final String BRUSHFLAG = "2";
	/**
	 * 初始化
	 * @param AppID
	 */
	public static void init(String AppID) {
		appId = AppID;
	}

	/**
	 * 展示广告
	 * @param context
	 * @param type
	 * @param advListener
	 */
	public static void whichShowAD(Activity context , String type, String appPos, ViewGroup container, View skipContainer, UpayAdListener advListener) {
		LogUtils.v(TAG, "whichShowAD-：---AdType---->" + type );
		if (type == null || advListener == null) {
			throw new IllegalArgumentException("参数错误，请检查参数");
		}

		if (type != Constants.GDT_TYPE_INTER && container == null) {
			throw new IllegalArgumentException("参数错误，请检查参数container");
		}

		MultiProcessFlag.setMultiProcess(true);
		listener = advListener;
		if (type == Constants.GDT_TYPE_BANNER_UP) {
			LogUtils.v(TAG, "发起  广点通SDk   --->Banner");
			GDTAdsShow.getInstance().showBanner(context, container, appId, appPos, UpayAd.advListener);
		} else if (type == Constants.GDT_TYPE_INTER) {
			LogUtils.v(TAG, "发起  广点通SDK   --->插屏");
			GDTAdsShow.getInstance().showInterstitialAD(context, appId, appPos);
		} else if (type == Constants.GDT_TYPE_SPLASH) {
			LogUtils.v(TAG, "发起  广点通SDK   --->开屏");
			GDTAdsShow.getInstance().showSplash(context,container,skipContainer, appId, appPos);
		} else if (type == Constants.GDT_TYPE_VIDEO) {
			LogUtils.v(TAG, "发起  广点通SDK   --->原生视频");
			GDTAdsShow.getInstance().showVedioAD(context, container, appId, appPos);
		}
	}

	/**
	 * 广告展示返回参数回调
	 */
	public static AdvListener advListener = new AdvListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void onResult(String adType, String ret, String rcodes, String misClick, String seqId,
		                     String act, String adAppId) {
			// 失败
			if (ret.equals(Constants.RET_FAILED)) {
				String m_strADCallBack = adType + ":" + ret + ";";
				LogUtils.v(TAG, "-----------广告展示失败-----------");
				// 回调消息
				listener.onResult(adType, ret, rcodes, misClick, seqId);
				LogUtils.v(TAG, "m_strADCallBack--->" + m_strADCallBack + "---eventType--->" + Constants.EVENT_AD_RESULT);
				if (!BRUSHFLAG.equals(misClick)) {
					Constants.isShow = false;
				}
				// 成功
			} else if (ret.equals(Constants.RET_SUCCEED)) {
				String m_strADCallBack = adType + ":" + ret + ";";
				LogUtils.v(TAG, "-----------广告展示成功-----------");
				listener.onResult(adType, ret, rcodes, misClick, seqId);
				LogUtils.v(TAG, "m_strADCallBack--->" + m_strADCallBack + "eventType" + Constants.EVENT_SHOW);
			} else if (ret.equals(Constants.RET_CLOSE)) {
				if (!BRUSHFLAG.equals(misClick)) {
					Constants.isShow = false;
				}
				listener.onResult(adType, ret, rcodes, misClick, seqId);
			} else if (ret.equals(Constants.RET_CLICK)) {
				LogUtils.v(TAG, "-----------广告点击成功-----------");
				if (!BRUSHFLAG.equals(misClick)) {
					Constants.isShow = false;
				}
				listener.onResult(adType, ret, rcodes, misClick, seqId);
			} else if (ret.equals(Constants.RET_MISCLICK)) {
				LogUtils.v(TAG, "-----------广告误点-----------");
				listener.onResult(adType, ret, rcodes, misClick, seqId);
			} else if (ret.equals(Constants.RET_DWONLOAD)) {
				LogUtils.v(TAG, "-----------应用下载-----------");
				listener.onResult(adType, ret, rcodes, misClick, seqId);
			} else if (ret.equals(Constants.RET_INSTALL)) {
				LogUtils.v(TAG, "-----------应用安装完成-----------");
				listener.onResult(adType, ret, rcodes, misClick, seqId);
			} else if (ret.equals(Constants.RET_ACTIVATE)) {
				LogUtils.v(TAG, "-----------应用激活完成-----------");
				listener.onResult(adType, ret, rcodes, misClick, seqId);
			}
		}
	};

	public static void onGDTResume() {
		LogUtils.v(TAG, "onResume");
		GDTVideoAD.getInstance().vadOnResume();
	}

	public static void onGDTPause() {
		LogUtils.v(TAG, "onPause");
		Constants.isShow = false;
		GDTVideoAD.getInstance().vadOnPause();
	}

	public static void onGDTDestroy() {
		LogUtils.v(TAG, "BodyActivity is real close ");
		GDTVideoAD.getInstance().vadOnDestroy();
		GDTBannerAD.GDTBannerDestroy();
		GDTInterstitialAD.iadDestroy();
		GDTSplashAD.splashADDestroy();
	}


}
