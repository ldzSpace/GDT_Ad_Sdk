package com.upay.sdk_gdt_ad.inf;

public interface AdvListener {

	/**
	 * @param adType       广告类型 1.4 banner;2 插屏;3 开屏; 5 原生视频
	 * @param ret          状态码
	 * @param rcodes       请求广告商回调接口的状态码
	 * @param misClick     误点状态码，1:是误点，0:非误点，2:伪造点击（展示/下载/安装/激活）回调 ，-1: 隐藏真实点击。
	 * @param seqId        广告流水号
	 * @param act          广告推广类型，api，直客专有
	 * @param adAppId      广告ID（只有直客的时候有值）
	 */
	void onResult(String adType, String ret, String rcodes, String misClick, String seqId, String act, String adAppId);

}
