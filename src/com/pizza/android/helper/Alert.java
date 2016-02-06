package com.pizza.android.helper;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class Alert {

	public static void createToast(final Context ctx, final String msg) {
		Handler mainHandler = new Handler(ctx.getMainLooper());
		mainHandler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
}