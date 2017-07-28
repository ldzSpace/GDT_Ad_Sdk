package com.upay.sdk_gdt_ad.core;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qq.e.ads.nativ.MediaListener;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.NativeMediaAD;
import com.qq.e.ads.nativ.NativeMediaADData;
import com.upay.sdk_gdt_ad.Utils.Constants;
import com.upay.sdk_gdt_ad.Utils.ImageLoader;
import com.upay.sdk_gdt_ad.Utils.LogUtils;

import java.util.List;

import static com.upay.sdk_gdt_ad.Utils.DensityUtil.dip2px;
import static com.upay.sdk_gdt_ad.adApi.UpayAd.advListener;

/**
 * ================================================
 * 作    者：刘大志
 * 版    本：1.0
 * 创建日期：2017/7/26
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GDTVideoAD {
	public static final String TAG = GDTVideoAD.class.getSimpleName();
	public static final int AD_COUNT = 1;                 // 一次拉取的广告条数：范围和以前的原生广告一样，是1-10。但是我们强烈建议：需要展示几个广告就加载几个，不要过多加载广告而不去曝光它们。这样会导致曝光率过低。
	public static GDTVideoAD mGdtVideoAds = null;
	private NativeMediaAD mADManager;              // 原生广告manager，用于管理广告数据的加载，监听广告回调
	private NativeMediaADData mAD;                        // 加载的原生视频广告对象，本示例为简便只演示加载1条广告的示例
	private FrameLayout container;
	private MediaView mMediaView;                         // 广点通视频容器，需要开发者添加到自己的xml布局文件中，用于播放视频
	private ImageView mImagePoster;                       // 广告大图，没有加载好视频素材前，先显示广告的大图
	private final Handler tikTokHandler = new Handler();  // 倒计时读取Handler，开发者可以按照自己的设计实现，此处仅供参考
	private TextView mTextCountDown;                      // 倒计时显示的TextView，广点通原生视频广告SDK提供了非常多的接口，允许开发者自由地定制视频播放器控制条和倒计时等UI
    private Context context;

	private Button mDownloadButton;
	private TextView mTextTitle;
	private TextView mTextDes;
	private ImageView mIconiv;
	private ImageLoader imageLoader;
	private RelativeLayout relativeLayout;

	public static GDTVideoAD getInstance() {
		if (mGdtVideoAds == null) {
			synchronized (GDTVideoAD.class) {
				if (mGdtVideoAds == null) {
					mGdtVideoAds = new GDTVideoAD();
				}
			}
		}
		return mGdtVideoAds;
	}
	/**
	 * 展示广告
	 * @param oActivity
	 * @param APPID
	 * @param NativeVideoPosID
	 */
	public void showVideoAD(final Activity oActivity, ViewGroup container, String APPID, String NativeVideoPosID) {
		this.context = oActivity;
		initView(oActivity, container );
		initNativeVideoAd(context, APPID, NativeVideoPosID);
		loadAD();
	}

	/**
	 * 初始化界面
	 * @param oActivity
	 *
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public void initView(Activity oActivity,ViewGroup viewGroup){
		RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		RelativeLayout rootView = new RelativeLayout(oActivity);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		container = new FrameLayout(oActivity);
		container.setLayoutParams(layoutParams);
		container.setId(View.generateViewId());

		FrameLayout.LayoutParams mvLayoutParams = new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
		mMediaView = new MediaView(oActivity);
		container.addView(mMediaView,mvLayoutParams);

		FrameLayout.LayoutParams tvLayoutParams = new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
		tvLayoutParams.gravity = Gravity.TOP| Gravity.END;
		mTextCountDown = new TextView(oActivity);
		mTextCountDown.setTextColor(Color.parseColor("#66111111"));
		mTextCountDown.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
		mTextCountDown.setPadding(dip2px(oActivity, 6),dip2px(oActivity, 3),dip2px(oActivity, 6),dip2px(oActivity, 3));
		container.addView(mTextCountDown);
		mTextCountDown.setVisibility(View.GONE);

		FrameLayout.LayoutParams ivLayoutParams = new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, dip2px(oActivity, 200));
		mImagePoster = new ImageView(oActivity);
		mImagePoster.setScaleType(ImageView.ScaleType.FIT_XY);
		container.addView(mImagePoster,ivLayoutParams);

		rootView.addView(container);

		RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
		layoutParams2.addRule(RelativeLayout.ABOVE,container.getId());
		relativeLayout = new RelativeLayout(oActivity);
		relativeLayout.setLayoutParams(layoutParams2);
		relativeLayout.setGravity(Gravity.CENTER_VERTICAL);
		relativeLayout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

		RelativeLayout.LayoutParams ivIconLayoutParams = new RelativeLayout.LayoutParams(dip2px(oActivity, 64),dip2px(oActivity, 64));
		ivIconLayoutParams.setMargins(dip2px(oActivity, 10),dip2px(oActivity, 10),dip2px(oActivity, 10),dip2px(oActivity, 10));
		ivIconLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		ivIconLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		mIconiv = new ImageView(oActivity);
		mIconiv.setId(View.generateViewId());
		mIconiv.setLayoutParams(ivIconLayoutParams);
		relativeLayout.addView(mIconiv);

		RelativeLayout.LayoutParams btnLayoutParams = new RelativeLayout.LayoutParams(dip2px(oActivity, 60),dip2px(oActivity, 40));
		btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		btnLayoutParams.addRule(RelativeLayout.ALIGN_TOP,mIconiv.getId());
		btnLayoutParams.leftMargin = dip2px(oActivity, 10);
		btnLayoutParams.rightMargin = dip2px(oActivity, 10);
		mDownloadButton = new Button(oActivity);
		mDownloadButton.setId(View.generateViewId());
		mDownloadButton.setLayoutParams(btnLayoutParams);
		mDownloadButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mDownloadButton.setTextColor(Color.WHITE);
		relativeLayout.addView(mDownloadButton);

		RelativeLayout.LayoutParams tvTitleLayoutParams = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
		tvTitleLayoutParams.leftMargin = dip2px(oActivity, 6);
		tvTitleLayoutParams.addRule(RelativeLayout.RIGHT_OF,mIconiv.getId());
		tvTitleLayoutParams.addRule(RelativeLayout.ALIGN_TOP,mIconiv.getId());
		tvTitleLayoutParams.addRule(RelativeLayout.LEFT_OF,mDownloadButton.getId());
		mTextTitle= new TextView(oActivity);
		mTextTitle.setId(View.generateViewId());
		mTextTitle.setLayoutParams(tvTitleLayoutParams);
		mTextTitle.setTextColor(Color.parseColor("#111111"));
		mTextTitle.setPadding(dip2px(oActivity, 6),dip2px(oActivity, 3),dip2px(oActivity, 6),dip2px(oActivity, 3));
		mTextTitle.setSingleLine(true);
		mTextTitle.setEllipsize(TextUtils.TruncateAt.END);
		relativeLayout.addView(mTextTitle);

		RelativeLayout.LayoutParams tvDecLayoutParams = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
		tvDecLayoutParams.leftMargin = dip2px(oActivity, 6);
		tvDecLayoutParams.addRule(RelativeLayout.BELOW,mTextTitle.getId());
		tvDecLayoutParams.addRule(RelativeLayout.RIGHT_OF,mIconiv.getId());
		tvDecLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM,mIconiv.getId());
		tvDecLayoutParams.addRule(RelativeLayout.LEFT_OF,mDownloadButton.getId());
		tvDecLayoutParams.leftMargin = dip2px(oActivity, 6);
		tvDecLayoutParams.rightMargin = dip2px(oActivity, 6);
		mTextDes = new TextView(oActivity);
		mTextDes.setLayoutParams(tvDecLayoutParams);
		mTextDes.setTextColor(Color.parseColor("#66111111"));
		mTextDes.setPadding(dip2px(oActivity, 6),dip2px(oActivity, 3),dip2px(oActivity, 6),dip2px(oActivity, 3));
		mTextDes.setMaxLines(2);
		mTextDes.setEllipsize(TextUtils.TruncateAt.END);
		relativeLayout.addView(mTextDes);
		rootView.addView(relativeLayout);
		viewGroup.addView(rootView,rootLayoutParams);
		imageLoader = new ImageLoader(1, ImageLoader.Type.LIFO);
	}

	/**
	 * 初始化视屏广告
	 * @param context
	 * @param APPID
	 * @param NativeVideoPosID
	 */
	public void initNativeVideoAd(Context context, String APPID, String NativeVideoPosID) {
		NativeMediaAD.NativeMediaADListener listener = new NativeMediaAD.NativeMediaADListener() {
			/**
			 * 广告加载成功
			 * @param adList  广告对象列表
			 */
			@Override
			public void onADLoaded(List<NativeMediaADData> adList) {
				if (adList.size() > 0) {
					mAD = adList.get(0);
					/**
					 * 加载广告成功，开始渲染。
					 */
					initADUI();
					if (mAD.isVideoAD()) {
						/**
						 * 如果该条原生广告是一条带有视频素材的广告，还需要先调用preLoadVideo接口来加载视频素材：
						 *    - 加载成功：回调NativeMediaADListener.onADVideoLoaded(NativeMediaADData adData)方法
						 *    - 加载失败：回调NativeMediaADListener.onADError(NativeMediaADData adData, int errorCode)方法，错误码为700
						 */
						mAD.preLoadVideo();
						LogUtils.v(TAG, "GDTVideo视屏广告--->");
					} else {
						/**
						 * 如果该条原生广告只是一个普通图文的广告，不带视频素材，那么渲染普通的UI即可。
						 */
						LogUtils.v(TAG, "GDTVideo普通广告--->");
					}
				}
			}
			/**
			 * 广告加载失败
			 *
			 * @param errorCode   广告加载失败的错误码，错误码含义请参考开发者文档第4章。
			 */
			@Override
			public void onNoAD(int errorCode) {
				LogUtils.v(TAG, "GDTVideo展示失败--->" + errorCode);
				advListener.onResult(Constants.GDT_TYPE_VIDEO, Constants.RET_FAILED, "", Constants.MISCLICK_NO, "", "", "");
			}

			/**
			 * 广告状态发生变化，对于App类广告而言，下载/安装的状态和下载进度可以变化。
			 *
			 * @param ad    状态发生变化的广告对象
			 */
			@Override
			public void onADStatusChanged(NativeMediaADData ad) {
				if (ad != null && ad.equals(mAD)) {
					updateADUI();   // App类广告在下载过程中，下载进度会发生变化，如果开发者需要让用户了解下载进度，可以更新UI。
				}
			}

			/**
			 * 广告处理发生错误，当调用一个广告对象的onExposured、onClicked、preLoadVideo接口时，如果发生了错误会回调此接口，具体的错误码含义请参考开发者文档。
			 *
			 * @param adData    广告对象
			 * @param errorCode 错误码，700表示视频加载失败，701表示视频播放时出现错误
			 */
			@Override
			public void onADError(NativeMediaADData adData, int errorCode) {
				LogUtils.v(TAG, "GDTVideo展示失败--->" + errorCode +"，错误信息："+adData.getTitle());
				advListener.onResult(Constants.GDT_TYPE_VIDEO, Constants.RET_FAILED, "", Constants.MISCLICK_NO, "", "", "");
			}

			/**
			 * 当调用一个广告对象的preLoadVideo接口时，视频素材加载完成后，会回调此接口，在此回调中可以给广告对象绑定MediaView组件播放视频。
			 *
			 * @param adData  视频素材加载完成的广告对象，很显然这个广告一定是一个带有视频素材的广告，需要给它bindView并播放它
			 */
			@Override
			public void onADVideoLoaded(NativeMediaADData adData) {
				LogUtils.i(TAG, adData.getTitle() + " ---> 视频素材加载完成"); // 仅仅是加载视频文件完成，如果没有绑定MediaView视频仍然不可以播放
				bindMediaView();
			}

			/**
			 * 广告曝光时的回调
			 *
			 * 注意：带有视频素材的原生广告可以多次曝光 按照曝光计费
			 * 没带有视频素材的广告只能曝光一次 按照点击计费
			 *
			 * @param adData  曝光的广告对象
			 */
			@Override
			public void onADExposure(NativeMediaADData adData) {
				LogUtils.i(TAG, adData.getTitle() + " onADExposure");
			}

			/**
			 * 广告被点击时的回调
			 *
			 * @param adData  被点击的广告对象
			 */
			@Override
			public void onADClicked(NativeMediaADData adData) {
				LogUtils.i(TAG, adData.getTitle() + " onADClicked");
				advListener.onResult(Constants.GDT_TYPE_VIDEO, Constants.RET_CLICK, "", Constants.MISCLICK_NO, "", "", "");
			}
		};
		mADManager = new NativeMediaAD(context.getApplicationContext(), APPID, NativeVideoPosID, listener);
	}

	/**
	 * 加载广告
	 */
	public void loadAD() {
		if (mADManager != null) {
			try {
				mADManager.loadAD(AD_COUNT);
			} catch (Exception e) {
				Toast.makeText(context.getApplicationContext(), "加载失败", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void initADUI() {
		imageLoader.loadImage(mAD.getIconUrl(),mIconiv,true,new ImageLoader.ImageLoadingListener() {

			@Override
			public void onLoadingFailed(String imageUri, View view, String failReason) {
				LogUtils.e(TAG,  "广告icon加载失败");
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

			}
		});

		imageLoader.loadImage(mAD.getImgUrl(), mImagePoster, true, new ImageLoader.ImageLoadingListener() {

			@Override
			public void onLoadingFailed(String imageUri, View view, String failReason) {
				LogUtils.e(TAG,  "广告大图加载失败");
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

			}
		});
		mTextTitle.setText(mAD.getTitle());
		mTextDes.setText(mAD.getDesc());
		updateADUI();
		/**
		 * 注意：在渲染时，必须先调用onExposured接口曝光广告，否则点击接口onClicked将无效
		 */
		mAD.onExposured(relativeLayout);
		mDownloadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mAD.onClicked(view);
				advListener.onResult(Constants.GDT_TYPE_VIDEO, Constants.RET_DWONLOAD, "", Constants.MISCLICK_NO, "", "", "");
			}
		});
	}

	private void updateADUI() {
		if (!mAD.isAPP()) {
			mDownloadButton.setText("浏览");
			return;
		}
		switch (mAD.getAPPStatus()) {
			case 0:
				mDownloadButton.setText("下载");
				break;
			case 1:
				mDownloadButton.setText("启动");
				break;
			case 2:
				mDownloadButton.setText("更新");
				break;
			case 4:
				mDownloadButton.setText(mAD.getProgress() + "%");
				break;
			case 8:
				mDownloadButton.setText("安装");
				advListener.onResult(Constants.GDT_TYPE_VIDEO, Constants.RET_INSTALL, "", Constants.MISCLICK_NO, "", "", "");
				break;
			case 16:
				mDownloadButton.setText("下载失败，重新下载");
				break;
			default:
				mDownloadButton.setText("浏览");
				break;
		}
	}


	/**
	 * 将广告实例和MediaView绑定，播放视频。
	 *
	 * 注意：播放视频前需要将广告的大图隐藏，将MediaView显示出来，否则视频将无法播放，也无法上报视频曝光，无法产生计费。
	 */
	private void bindMediaView() {
		if (mAD.isVideoAD()) {
			mImagePoster.setVisibility(View.GONE);
			mMediaView.setVisibility(View.VISIBLE);

			/**
			 * bindView(MediaView view, boolean useDefaultController):
			 *    - useDefaultController: false，不会调用广点通的默认视频控制条
			 *    - useDefaultController: true，使用SDK内置的播放器控制条，此时开发者需要把demo下的res文件夹里面的图片拷贝到自己项目的res文件夹去
			 *
			 * 在这里绑定MediaView后，SDK会根据视频素材的宽高比例，重新给MediaView设置新的宽高
			 */
			mAD.bindView(mMediaView, false);
			mAD.play();
			/** 设置视频播放过程中的监听器 */
			mAD.setMediaListener(new MediaListener() {
				/**
				 * 视频播放器初始化完成，准备好可以播放了
				 *
				 * @param videoDuration 视频素材的总时长
				 */
				@Override
				public void onVideoReady(long videoDuration) {
					LogUtils.i(TAG, "onVideoReady, videoDuration = " + videoDuration);
					duration = videoDuration;
				}
				/** 视频开始播放 */
				@Override
				public void onVideoStart() {
					LogUtils.i(TAG, "onVideoStart");
					tikTokHandler.post(countDown);
					mTextCountDown.setVisibility(View.VISIBLE);
				}
				/** 视频暂停 */
				@Override
				public void onVideoPause() {
					LogUtils.i(TAG, "onVideoPause");
					mTextCountDown.setVisibility(View.GONE);
				}
				/** 视频自动播放结束，到达最后一帧 */
				@Override
				public void onVideoComplete() {
					LogUtils.i(TAG, "onVideoComplete");
					releaseCountDown();
					mTextCountDown.setVisibility(View.GONE);
					advListener.onResult(Constants.GDT_TYPE_VIDEO, Constants.RET_SUCCEED, "", Constants.MISCLICK_NO, "", "", "");
				}
				/** 视频播放时出现错误 */
				@Override
				public void onVideoError(int errorCode) {
					LogUtils.i(TAG, "onVideoError, errorCode: " + errorCode);
					advListener.onResult(Constants.GDT_TYPE_VIDEO, Constants.RET_FAILED, "", Constants.MISCLICK_NO, "", "", "");
				}
				/** SDK内置的播放器控制条中的重播按钮被点击 */
				@Override
				public void onReplayButtonClicked() {
					LogUtils.i(TAG, "onReplayButtonClicked");
				}
				/**
				 * SDK内置的播放器控制条中的下载/查看详情按钮被点击
				 * 注意: 这里是指UI中的按钮被点击了，而广告的点击事件回调是在onADClicked中，开发者如需统计点击只需要在onADClicked回调中进行一次统计即可。
				 */
				@Override
				public void onADButtonClicked() {
					LogUtils.i(TAG, "onADButtonClicked");
					advListener.onResult(Constants.GDT_TYPE_VIDEO, Constants.RET_CLICK, "", Constants.MISCLICK_NO, "", "", "");
				}
				/** SDK内置的全屏和非全屏切换回调，进入全屏时inFullScreen为true，退出全屏时inFullScreen为false */
				@Override
				public void onFullScreenChanged(boolean inFullScreen) {
					LogUtils.i(TAG, "onFullScreenChanged, inFullScreen = " + inFullScreen);
					// 原生视频广告默认静音播放，进入到全屏后建议开发者可以设置为有声播放
					if (inFullScreen) {
						mAD.setVolumeOn(true);
					} else {
						mAD.setVolumeOn(false);
					}
				}
			});
		}
	}

	/**
	 * 在Activity生命周期中一定要调用视频广告的暂停和播放接口，保证用户在Activity之间切换时，视频可以暂停和续播，同时也保证数据上报正常，否则将影响到开发者的收益：
	 *    - Activity.onResume() ：  调用NativeMediaADData.resume()
	 *    - Activity.onPause()  ：  调用NativeMediaADData.stop()，广点通目前在产品概念上要求每次观看视频广告都要重头播放，目前不提供暂停接口。调用stop接口，一个作用是保证用户体验，当用户看不到当前界面时不要再播放视频。另一个作用是触发SDK的播放信息上报事件，保证每次观看都有效果上报。
	 *    - Activity.onDestroy()：  调用NativeMediaADData.destroy()释放资源，不再播放视频广告时，一定要记得调用。
	 */
	public void vadOnResume() {
		Log.i(TAG, "onResume");
		if (mAD != null) {
			mAD.resume();
		}
	}

	public void vadOnPause() {
		Log.i(TAG, "onPause");
		if (mAD != null) {
			mAD.stop();
		}
	}

	public void vadOnDestroy() {
		Log.i(TAG, "onDestroy");
		if (mAD != null) {
			mAD.destroy();
		}
		releaseCountDown();
	}


	/**
	 * 刷新广告倒计时，本示例提供的思路仅开发者供参考，开发者完全可以根据自己的需求设计不同的样式。
	 */
	private static final String TEXT_COUNTDOWN = "广告倒计时：%s ";
	private long currentPosition, oldPosition, duration;
	private Runnable countDown = new Runnable() {
		public void run() {
			if (mAD != null) {
				currentPosition = mAD.getCurrentPosition();
				long position = currentPosition;
				if (oldPosition == position && mAD.isPlaying()) {
					Log.d(TAG, "玩命加载中...");
					mTextCountDown.setTextColor(Color.WHITE);
					mTextCountDown.setText("玩命加载中...");
				} else {
					Log.d(TAG, String.format(TEXT_COUNTDOWN, Math.round((duration - position) / 1000.0) + ""));
					mTextCountDown.setText(String.format(TEXT_COUNTDOWN, Math.round((duration - position) / 1000.0) + ""));
				}
				oldPosition = position;
				if (mAD.isPlaying()) {
					tikTokHandler.postDelayed(countDown, 500); // 500ms刷新一次进度即可
				}
			}
		}

	};

	private void releaseCountDown() {
		if (countDown != null) {
			tikTokHandler.removeCallbacks(countDown);
		}
	}

}
