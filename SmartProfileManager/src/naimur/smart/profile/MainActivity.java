package naimur.smart.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public void StartService (View v)
	{
		try
		{
			Intent intent = new Intent(this, MySmartService.class);
			startService(intent);
		}
		catch (Exception e)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT);
			toast.show ();
			Log.d("Service", "Exception");
		}
	}
	
	public void StopService(View v)
	{
		try
		{
			Intent intent = new Intent(this, MySmartService.class);
			stopService(intent);
		}
		catch (Exception e)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT);
			toast.show ();
			Log.d("Service", "Exception");
		}
	}
}
