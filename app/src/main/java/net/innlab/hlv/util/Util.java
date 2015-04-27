package net.innlab.hlv.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class Util {

	public static int screenWidth = 0;
	public static int screenHeight = 0;
	public static int densityDpi = 1;
	public static float density = 160;
	private static Context mContext = null;

	public static void init(Context context) {
		mContext = context;
		if (context == null) {
			return;
		}
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		densityDpi = dm.densityDpi;
		density = dm.density;
	}

	public static float pxToDp(int px) {
		return px * (160.0F / densityDpi);
	}

	public static int dpToPx(float dp) {
		return (int) (0.5F + density * dp);
	}

}