package naimur.currency.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import android.app.IntentService;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import naimur.currency.converter.MainActivity.MyBroadcastReceiver;

public class HttpConnection extends IntentService {
	public static final String GIVENVALUE = "givenValue";
	public static final String CONVERTTYPE = "type";
	public static final String VALUE = "convertedValue";
	public String[] twoPart = new String[2];
	public String rate = "";
	public String webSiteString = "";
	public String givenValue;
	public Boolean type;
	HttpURLConnection urlConnection = null;
	InputStream inputStream = null;
	
	public HttpConnection() {
		super("Connection");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		type = intent.getExtras().getBoolean(HttpConnection.CONVERTTYPE);
		givenValue = intent.getExtras().getString(HttpConnection.GIVENVALUE);
		
		if (android.os.Build.VERSION.SDK_INT > 9)
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		try {
			URL url = new URL("http://www.hrhafiz.com/converter/index.php");
			
			urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.connect();
			
			inputStream = urlConnection.getInputStream();
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String s = "";
            while ((s = buffer.readLine()) != null) {
            	webSiteString += s;
            }
			int i = 0;
			for (String temp:webSiteString.split(",", 2))
			{
				twoPart[i++] = temp;
			}
			
			if (type)
			{
				rate = twoPart[1].substring(twoPart[1].indexOf(":") + 1, twoPart[1].indexOf("}"));
				givenValue = String.valueOf(Double.valueOf(givenValue)*Double.valueOf(rate));
			}
			else
			{
				rate = twoPart[0].substring(twoPart[0].indexOf(":") + 1, twoPart[0].indexOf("}"));
				givenValue = String.valueOf(Double.valueOf(givenValue)*Double.valueOf(rate));
			}	
		}
		catch (MalformedURLException e) {
			
		} catch (IOException e) {

		}
		catch (Exception e) {

		}
		finally {
			urlConnection.disconnect();
			try {
				inputStream.close();
			} catch (IOException e) {

			}
		}
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(MyBroadcastReceiver.RESPONSE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(VALUE, givenValue);
		sendBroadcast(broadcastIntent);
	}
}
