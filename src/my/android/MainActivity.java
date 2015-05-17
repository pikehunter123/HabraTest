package my.android;


import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



import my.android.R;
import my.android.R.id;
import my.android.R.layout;
import my.android.R.menu;

import android.app.Activity;
import android.app.Notification;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	static int i=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 final Button button = (Button) findViewById(R.id.refrbutton);
	        button.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) // ���� �� ������
	            {
	            	/*
	            	// �������� ��������� AppWidgetManager.
	            	AppWidgetManager appWidgetManager = AppWidgetManager.
	            	getInstance(getApplicationContext());
	            	// �������� �������������� ������� ���������� ���������� �������.
	            	ComponentName thisWidget = new ComponentName(getApplicationContext(),MyAppWidget.class);
	            	int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	            	final int N = appWidgetIds.length;
	            	// ���������� �� ��������, �������� � ��������
	            	// ������ RemoteViews ��� ������� �� ���
	            	for (int i = 0; i < N; i++) {
	            	int appWidgetId = appWidgetIds[i];
	            		Log.i(MainActivity.class.getName(), "viget process i="+i+" id="+appWidgetId);	            	
	            	// �������� ����� ������ RemoveViews
	            	RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(),
	            	R.layout.pressure_widget);
	            	Log.i(MainActivity.class.getName(), "views.getLayoutId()="+views.getLayoutId());
	            	views.setTextViewText(R.id.widget_text, "hohohoq");
	            	// TODO ��������� ���������������� ��������� ������� � �������
	            	// ������� views.
	            	// �������� ������ � ������� AppWidgetManager, ���������
	            	// ���������� ������ RemoveViews.
	            	appWidgetManager.updateAppWidget(appWidgetId, views);
	            	}
	            	*/
	            	Log.i(MainActivity.class.getName(), "------refrbutton click");
	            	getApplicationContext().sendBroadcast(new Intent("com.paad.chapter9.FORCE_WIDGET_UPDATE"));
	             //   RefreshTemper();
	            }
	        });
	        
	        final Button buttonSta = (Button) findViewById(R.id.start);
	        buttonSta.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) // ���� �� ������
	            {
	              Log.i(MainActivity.class.getName(), "started");
	                ComponentName service = startService(new Intent(getApplicationContext(),PressureUpdateService.class));
	            }
	        });
	        
	        final Button buttonSto = (Button) findViewById(R.id.stop);
	        buttonSto.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) // ���� �� ������
	            {
	              Log.i(MainActivity.class.getName(), "stop");
	              stopService(new Intent(getApplicationContext(), PressureUpdateService.class));
	                
	            }
	        });
	        
	        

	       // RefreshTemper(); // ��� ������� ������ ����������� �����
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static void main(String[] args){
		 String temp = GetTemper("https://pogoda.yandex.ru/moscow/");
	}
	//----------------------------------------------------------------
    public static String GetTemper(String urlsite) // ������ �������� �����������
    {
        String matchtemper = "zz"+(i++);
        try
        {
                // �������� ��������
            URL url = new URL(urlsite);
            URLConnection conn = url.openConnection();
            InputStreamReader rd = new InputStreamReader(conn.getInputStream());
            StringBuilder allpage = new StringBuilder();
            int n = 0;
            char[] buffer = new char[40000];
            while (n >= 0)
            {
                n = rd.read(buffer, 0, buffer.length);
                if (n > 0)
                {
                    allpage.append(buffer, 0, n);                    
                }
            }
            // �������� � �����������
            System.out.println("---"+allpage.toString());
           /* final Pattern pattern = Pattern.compile
            ("<span style=\"color:#[a-zA-Z0-9]+\">[^-+0]+([-+0-9]+)[^<]+</span>[^(�-��-߸�a-zA-Z0-9)]+([�-��-߸�a-zA-Z ]+)");
            Matcher matcher = pattern.matcher(allpage.toString());
            if (matcher.find())
            {    
                matchtemper = matcher.group(1);            
            }*/        
            return matchtemper;
        }
        catch (Exception e)
        {
            
        }
        return matchtemper; 
    };
    //----------------------------------------------------------------
    public void RefreshTemper()
    {
    	/*DbAdapter dbb = new DbAdapter();
    	dbb.createDatabase(getApplicationContext() );
    	dbb.insValue(getApplicationContext());
    	dbb.getValue(getApplicationContext());
    	
        final TextView tTemper = (TextView) findViewById(R.id.temper);
         String bashtemp = "";
         new DownloadImageTask(tTemper)
         .execute("https://pogoda.yandex.ru/moscow/");
         */
         /*int duration = Toast.LENGTH_SHORT;
         Toast toast = Toast.makeText(getApplicationContext(), "yohoho", duration);
         toast.show();*/
         
      // �������� ����������� ������, ������� ����� ������������ � ��������
      // ������ � ��������� ������
   /*   int icon = R.drawable.applogo;
      // �����, ������� ����� ����� � ��������� ������ � ������ ���������
      // �����������
      String tickerText = "Notification";
      // � ����������� ��������� ������ ����������� ����������� �� �������
      // ���������
      long when = System.currentTimeMillis();
      Notification notification = new Notification(icon, tickerText, when);*/
        
    };

}
