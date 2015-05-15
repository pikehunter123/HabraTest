package my.pack;

//import java.awt.Point;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class WebDataReciver {
	public static void main(String[] args) {
		Point p = getWeather("https://pogoda.yandex.ru/moscow/");
		int temp = p.x;
		int press = p.y;
		System.out.println("---"+temp+" "+press);
		
	}

	// ----------------------------------------------------------------
	public static Point getWeather(String urlsite) // ������ ��������
													// �����������
	{
		Point ret= new Point(0, 0);
		Integer matchtemper = null;
		
		try {
			
			// �������� ��������
			URL url = new URL(urlsite);
			URLConnection conn = url.openConnection();
			//System.out.println(conn.getContentEncoding());
			InputStreamReader rd = new InputStreamReader(conn.getInputStream(),"UTF-8");
			//rd.
			//System.out.println(rd.getEncoding());
			StringBuilder allpage = new StringBuilder();
			int n = 0;
			char[] buffer = new char[40000];
			while (n >= 0) {
				n = rd.read(buffer, 0, buffer.length);
				if (n > 0) {
					allpage.append(buffer, 0, n);
				}
			}
			
			// �������� � �����������
			System.out.println("---"+allpage.toString());
			//String patt_temp = "<div\\s+class=\"current-weather__thermometer current-weather__thermometer_type_now\"\\s*>[^&]{1,}";
			String patt_temp = "current-weather__condition-icon\"\\s+data-width=\"46\"></i>[^&]{1,}";
			//<i class="icon icon_size_46 icon_thumb_skc-n current-weather__condition-icon" data-width="46"></i>
			String stemp = tryMatch(allpage, patt_temp);
			stemp=stemp.split(">", 3)[2].replace("+", "");
			if (stemp!=null) matchtemper = Integer.parseInt(stemp);
			System.out.println("t---" + matchtemper);
			
			String patt_press = "��������:\\s+[^<]{1,}";
			String spress = tryMatch(allpage, patt_press).trim();
			System.out.println("p---" + spress);
			Integer pressure= Integer.parseInt(spress.split(" ")[1]);
			System.out.println("---" + pressure);
			
			/*String patt_raise = "������:\\s*</span>[^<]*";
			String sraise = tryMatch(allpage, patt_raise);
			System.out.println("---" + sraise);
			
			String patt_wind = "�����:\\s*</span>[^<]*";
			String swind = tryMatch(allpage, patt_wind);
			System.out.println("---" + swind);
			
			String patt_gro = "���������:\\s*</span>[^<]*";
			String sgro = tryMatch(allpage, patt_gro);
			System.out.println("---" + sgro);
			
			*/
			
			ret.x=matchtemper;
			ret.y=pressure;
			
			
			//<span\s+class="current-weather__info-label"\s*>������:\s*</span>[^<]*
			
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error", e.getMessage());
			

		}
		return ret;
	}

	private static String tryMatch(StringBuilder allpage, String patt) {
		final Pattern pattern = Pattern.compile(patt);
		Matcher matcher = pattern.matcher(allpage.toString());
		String stemp=null;
		if (matcher.find()) {
			stemp = matcher.group(0);
			
		}
		return stemp;
	};
	

	
}
