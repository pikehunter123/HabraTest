package com.app.habr;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PressureUpdateService extends Service {

	DbAdapter db = null;

	public PressureUpdateService() {
		super();

	}

	private Timer updateTimer;
	int i = 0;

	private TimerTask doRefresh = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (db == null) {
			db = new DbAdapter();
			db.createDatabase(getApplicationContext());
		}
		Log.i(PressureUpdateService.class.getName(),
				"*****************************onStartCommand timer tik tak ");

		doRefresh = new TimerTask() {
			public void run() {
				Log.i(PressureUpdateService.class.getName(), "timer tik tak "
						+ (i++));
				newPressure();
			}
		};
		// Получите Общие настройки
		/*
		 * SharedPreferences prefs =
		 * getSharedPreferences(Preferences.USER_PREFERENCE
		 * ,Activity.MODE_PRIVATE);
		 */

		if (updateTimer == null)
			try {
				Log.i(PressureUpdateService.class.getName(), "***create task");
				updateTimer = new Timer("earthquakeUpdates");
				updateTimer.scheduleAtFixedRate(doRefresh, 0, 1 * 5 * 1000);
			} catch (Exception e) {
			}
		// else
		// refreshEarthquakes();
		return Service.START_STICKY;
	}

	@Override
	public void onCreate() {
		// TODO: Инициализируйте переменные, получите ссылки на элементы GUI
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(PressureUpdateService.class.getName(), "onDestroy");
		if (updateTimer != null) {
			doRefresh.cancel();
			updateTimer.purge();
			updateTimer.cancel();
			Log.i(PressureUpdateService.class.getName(), "timer cancel");
			updateTimer = null;
		}
	}

	public void returnPressure(Integer p) {
		Log.i(PressureUpdateService.class.getName(), "returnPressure  p=" + p);
		db.insValue(p);
		List<Integer> ll = db.getValues(10);
		for (Integer i : ll) {
			Log.i(PressureUpdateService.class.getName(), "rr "+i);
		}
	}

	private void newPressure() {

		final TextView tTemper = null;// (TextView) findViewById(R.id.temper);
		new DownloadImageTask(this).execute("https://pogoda.yandex.ru/moscow/");
	}

}
