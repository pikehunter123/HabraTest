package com.app.habr;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
public class MyAppWidget extends AppWidgetProvider {
	public static String FORCE_WIDGET_UPDATE = "com.paad.chapter9.FORCE_WIDGET_UPDATE";
	//context.sendBroadcast(new Intent("com.paad.chapter9.FORCE_WIDGET_UPDATE"));
	@Override
	public void onReceive(Context context, Intent intent) {
	super.onReceive(context, intent);
	if (FORCE_WIDGET_UPDATE.equals(intent.getAction())) {
		Log.i(MyAppWidget.class.getName(), " **********************update by receive call ");
		// Получите экземпляр AppWidgetManager.
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		// Получите идентификаторы каждого экземпляра выбранного виджета.
		ComponentName thisWidget = new ComponentName(context, MyAppWidget.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	// TODO Обновить пользовательский интерфейс виджета.
	}
	}
	
@Override
public void onUpdate(Context context,
AppWidgetManager appWidgetManager,
int[] appWidgetIds) {

	
	Log.i(MyAppWidget.class.getName(), " **********************update call ");
	final int N = appWidgetIds.length;
	for (int i = 0; i < N; i++) {
	int appWidgetId = appWidgetIds[i];
	// Создайте новый объект RemoveViews
	RemoteViews views = new RemoteViews(context.getPackageName(),
	R.layout.pressure_widget);
	// TODO Обновить пользовательский интерфейс.
	views.setTextViewText(R.id.widget_text, "hhhhhhhhh111");
	// Обновите виджет с помощью AppWidgetManager, используя
	// измененный объект RemoveViews.
	appWidgetManager.updateAppWidget(appWidgetId, views);
	}
// TODO Обновить пользовательский интерфейс виджета.
}
}