package my.android;

import android.app.Activity;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import my.android.R;
import my.android.R.drawable;
import my.android.R.id;
import my.android.R.layout;

public class PressureUpdateService extends Service {

	DbAdapter db = null;

	public PressureUpdateService() {
		super();

	}

	private Timer updateTimer;
	int i = 0;

	private TimerTask doRefresh = null;
	private Context ctx = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (db == null) {
			ctx = getApplicationContext();
			db = new DbAdapter();
			db.createDatabase(ctx);
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
		String s="";
		Log.i(PressureUpdateService.class.getName(), "returnPressure  p=" + p);
		db.insValue(p);
		List<ResultP> ll = db.getValues(10);
		Date d1=new Date(),d2= new Date();
		int j=0;
		for (ResultP r : ll) {
			j++;
			if (j==1)d1=r.getDate();
			if (j==ll.size())d2=r.getDate();
			Log.i(PressureUpdateService.class.getName(), "rr " + r.getDate()+ " " + r.getPress());
			s=r.getDate().toString();			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm:ss");

		// Получите экземпляр AppWidgetManager.
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx);
		// Получите идентификаторы каждого экземпляра выбранного виджета.
		ComponentName thisWidget = new ComponentName(ctx, MyAppWidget.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		for (int i = 0; i < appWidgetIds.length; i++) {
			
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(ctx.getPackageName(),
					R.layout.pressure_widget);
			views.setTextViewText(R.id.widget_text, sdf.format(d1)+"..."+sdf.format(d2));//+" P="+ll.get(ll.size()-1).getPress()+"mm");

			// Конвертируем Drawable в Bitmap
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pattern);
			/*Drawable dr = getResources().getDrawable(R.drawable.applogo);
			int h = dr.getIntrinsicHeight();
			int w = dr.getIntrinsicWidth();
			Log.i(PressureUpdateService.class.getName(), "Drawable"+h+"*"+w);*/
			///mBitmap.
			int mPhotoWidth = mBitmap.getWidth();
			int mPhotoHeight = mBitmap.getHeight();
			//Log.i(PressureUpdateService.class.getName(), "bitmap"+mPhotoWidth+"*"+mPhotoHeight);
			
			int h=mPhotoHeight-0;
			int w=mPhotoWidth-0;
			Bitmap mutableBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
			Canvas canvas = new Canvas(mutableBitmap);
			Paint paint= new Paint();
			paint.setColor(Color.WHITE);	
			canvas.drawRoundRect(new RectF(0,0,w,h), 2, 2, paint);
			paint.setColor(Color.BLACK);
			h=h-1;
			w=w-0;
			float y2=0;
			float x1=0;
			float x2=0;
			float y1=0;
			int pBottom=700;
			int pUp=790;
			int textSize=12;
			for (j=0;j<ll.size()-1;j++) {				
				int p1 = ll.get(j).getPress();
				int p2 = ll.get(j+1).getPress();
				x1=(j)*w/(ll.size()-1);
				x2=(j+1)*w/(ll.size()-1);
				y1=(float) (h-1.0*h/(pUp-pBottom)*(p1-pBottom));
				y2=(float) (h-1.0*h/(pUp-pBottom)*(p2-pBottom));
				//Log.i(PressureUpdateService.class.getName(), "drow x1"+x1+" y1"+y1);
				canvas.drawLine(x1, y1, x2, y2, paint);
			}	
			paint.setColor(Color.BLACK);
			paint.setTextSize(textSize);
			int p1 = ll.get(0).getPress();
			int p2 = ll.get(ll.size()-1).getPress();
			y1=(float) (h-1.0*h/(pUp-pBottom)*(p1-pBottom));
			y2=(float) (h-1.0*h/(pUp-pBottom)*(p2-pBottom));
			canvas.drawText(""+Integer.toString(p1),1, y1-textSize, paint);
			canvas.drawText(""+Integer.toString(p2),w-25, y2-textSize,  paint);
			
			 p1=pBottom;
			 p2=pUp;
			y1=(float) (h-1.0*h/(pUp-pBottom)*(p1-pBottom));
			y2=(float) (h-1.0*h/(pUp-pBottom)*(p2-pBottom));
			/*Log.i(PressureUpdateService.class.getName(), "***(pUp-pBottom)="+(pUp-pBottom));
			Log.i(PressureUpdateService.class.getName(), "***(p2-pBottom)="+(p2-pBottom));
			Log.i(PressureUpdateService.class.getName(), "***1.0/(pUp-pBottom)*(p2-pBottom)="+(1.0/(pUp-pBottom)*(p2-pBottom)));
			*/
			//paint.getStyle().setStyle(style);
			paint.setColor(Color.BLUE);				
			canvas.drawLine(w-10, y1, w, y1, paint);
			//Log.i(PressureUpdateService.class.getName(), "drow green x1"+w+" y1"+y1);			
			//Log.i(PressureUpdateService.class.getName(), "drow blue x1"+w+" y2"+y2);			
			canvas.drawLine(w-10, y2, w, y2, paint);
						
			views.setImageViewBitmap(R.id.widget_image, mutableBitmap);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	private void newPressure() {

		final TextView tTemper = null;// (TextView) findViewById(R.id.temper);
		new DownloadImageTask(this).execute("https://pogoda.yandex.ru/moscow/");
	}

}
