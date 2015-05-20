package my.android;

import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class DownloadImageTask extends AsyncTask<String, Void, Integer> {
	PressureUpdateService s;

    public DownloadImageTask(PressureUpdateService s) {
        this.s = s;
    }

    protected Integer doInBackground(String... urls) {
    	//if (NetworkInfo.isRoaming()) return
    	 my.android.Point p = my.android.WebDataReciver.getWeather(urls[0]);
  		int temp = p.x;
  		int press = p.y;
  		//System.out.println("-----**"+temp+" "+press);
           //bashtemp = GetTemper("http://be.bashkirenergo.ru/weather/ufa/");
          // ����������� ����������� /*catch (Exception e) {
         //   Log.e("Error", e.getMessage());
         //   e.printStackTrace();     
  		Log.d(DownloadImageTask.class.getName(), "-------returned"+temp+" "+press );
  		
        return press;
    }

    protected void onPostExecute(Integer result) {
        s.returnPressure(result);
    }

	
}
