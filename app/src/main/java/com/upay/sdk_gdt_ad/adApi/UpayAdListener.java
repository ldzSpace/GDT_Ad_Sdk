package com.upay.sdk_gdt_ad.adApi;

/**
 * ================================================
 * 作    者：刘大志
 * 版    本：1.0
 * 创建日期：2017/7/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface UpayAdListener {
	/**
	 * @param adType       广告类型 1 banner;13 桌面图标;2 插屏;3 原生视频
	 * @param ret          状态码
	 * @param rcodes       请求广告商回调接口的状态码
	 * @param misClick     误点状态码，1:是误点，0:非误点，2:伪造点击（展示/下载/安装/激活）回调 ，-1: 隐藏真实点击。
	 * @param seqId        广告流水号
	 */
	void onResult(String adType, String ret, String rcodes, String misClick, String seqId);
}
