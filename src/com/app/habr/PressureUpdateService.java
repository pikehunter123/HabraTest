package com.app.habr;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class PressureUpdateService extends Service {

	private Timer updateTimer;
	int i = 0;

	private TimerTask doRefresh = new TimerTask() {
		public void run() {
			Log.i(PressureUpdateService.class.getName(), "timer tik tak "+ (i++));
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(PressureUpdateService.class.getName(),
				"*****************************onStartCommand timer tik tak ");
		// �������� ����� ���������
		/*
		 * SharedPreferences prefs =
		 * getSharedPreferences(Preferences.USER_PREFERENCE
		 * ,Activity.MODE_PRIVATE);
		 */
		if (true) {
			updateTimer = new Timer("earthquakeUpdates");
			updateTimer.scheduleAtFixedRate(doRefresh, 0, 1 * 5 * 1000);
		}
		// else
		// refreshEarthquakes();
		return Service.START_STICKY;
	}

	@Override
	public void onCreate() {
		// TODO: ��������������� ����������, �������� ������ �� �������� GUI
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
			updateTimer.cancel();
			Log.i(PressureUpdateService.class.getName(), "timer cancel");
		}
	}

}
