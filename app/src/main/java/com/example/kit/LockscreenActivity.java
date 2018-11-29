package com.example.kit;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class LockscreenActivity extends Activity {
	private boolean checkingDrawPermission = false;

	public WindowManager winManager;
	public RelativeLayout wrapperView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			showLockScreen();
		} catch (WindowManager.BadTokenException e) { // On Marshmallow and above this is an issue. We don't have the permission to draw over other applications even though it is declared in the manifest
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				checkDrawOverlayPermission();
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkingDrawPermission && Settings.canDrawOverlays(this)) {
			recreate();
		} else {
			//Todo: Permission was denied. Show user that permission is absolutely needed.
		}
	}

	private void showLockScreen() {
		if (!isMyServiceRunning(StartLockscreenService.class)) {
			startService(new Intent(getBaseContext(), StartLockscreenService.class));
		}

		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
						WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
						WindowManager.LayoutParams.FLAG_FULLSCREEN |
						WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
						WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				PixelFormat.TRANSLUCENT);

		// Unfortunately the flags WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD are not working on some devices (Thanks Samsung!)
		// So to be sure the keyguard is disabled, we disable the keyguard using this deprecated method
		// Or you might want to specifically ask users to disable their system screen lock to get the best experience
		((KeyguardManager) getSystemService(KEYGUARD_SERVICE)).newKeyguardLock("IN").disableKeyguard();

		getWindow().setAttributes(localLayoutParams);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		this.wrapperView = new RelativeLayout(getBaseContext());
		View.inflate(this, R.layout.lock_screen, this.wrapperView);

		this.winManager = ((WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE));
		this.winManager.addView(this.wrapperView, localLayoutParams);
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	public void checkDrawOverlayPermission() {
		if (!Settings.canDrawOverlays(this)) {
			checkingDrawPermission = true;
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
			startActivity(intent);
		}
	}

	public void onUnlock(View view) {
		// Simple unlock by finishing activity and removing views
		this.winManager.removeView(this.wrapperView);
		this.wrapperView.removeAllViews();

		finish();
	}

	/**
	 * Check if a specific service is currently running
	 * Loops through all running services and compares class names
	 * @param serviceClass the class of the service you are unsure is running
	 * @return true if the service is running otherwise false.
	 */
	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
