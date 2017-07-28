package com.upay.sdk_gdt_ad;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.upay.sdk_gdt_ad.adApi.UpayAd;
import com.upay.sdk_gdt_ad.adApi.UpayAdListener;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String APPID = "1101152570";
		String BannerPosID = "9079537218417626401";
		String InterteristalPosID = "8575134060152130849";
		String SplashPosID = "8863364436303842593";
		String NativePosID = "5000709048439488";
		String NativeVideoPosID = "2050206699818455";
		String NativeExpressPosID = "7030020348049331";
		UpayAd.init(APPID);
		RelativeLayout relativeLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		relativeLayout.setLayoutParams(rlp);

		RelativeLayout relativeLayout2 = new RelativeLayout(this);
		RelativeLayout.LayoutParams rl2p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		relativeLayout2.setLayoutParams(rl2p);
		final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(MainActivity.this);
		UpayAd.whichShowAD(this, "5", NativeVideoPosID, relativeLayout2,null,new UpayAdListener() {
			@Override
			public void onResult(String adType, String ret, String rcodes, String misClick, String seqId) {
				Toast.makeText(MainActivity.this,"adType :"+ adType +", ret :" + ret,Toast.LENGTH_LONG).show();
			}
		});
		customizeDialog.setTitle("我是一个自定义Dialog");
		customizeDialog.setView(relativeLayout2);
		customizeDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		customizeDialog.show();
		setContentView(relativeLayout);

	}

	public void showDialog(){

	}

}
