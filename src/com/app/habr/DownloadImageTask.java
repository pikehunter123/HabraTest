package com.app.habr;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class DownloadImageTask extends AsyncTask<String, Void, Integer> {
	PressureUpdateService s;

    public DownloadImageTask(PressureUpdateService s) {
        this.s = s;
    }

    protected Integer doInBackground(String... urls) {
    	 my.pack.Point p = my.pack.WebDataReciver.getWeather(urls[0]);
  		int temp = p.x;
  		int press = p.y;
  		//System.out.println("-----**"+temp+" "+press);
           //bashtemp = GetTemper("http://be.bashkirenergo.ru/weather/ufa/");
          // отображение температуры /*catch (Exception e) {
         //   Log.e("Error", e.getMessage());
         //   e.printStackTrace();     
  		Log.i(DownloadImageTask.class.getName(), "-------returned"+temp+" "+press );
  		
        return press;
    }

    protected void onPostExecute(Integer result) {
        s.returnPressure(result);
    }

	
}
