package com.example.kit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Sebastian Rask on 19-12-2016.
 */

public class StartLockscreenReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(getClass().getSimpleName(), "Lockscreen Intent received - " + intent.getAction());
		Intent lockscreenIntent = new Intent(context, LockscreenActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(lockscreenIntent);
	}
}
