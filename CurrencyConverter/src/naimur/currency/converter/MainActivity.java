package naimur.currency.converter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public String givenValue;
	// true = bdTOus; false = usTObd
	public Boolean convertType;
	MyBroadcastReceiver myBroadcastReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		myBroadcastReceiver = new MyBroadcastReceiver();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onResume()
	{
		IntentFilter intentFilter = new IntentFilter(MyBroadcastReceiver.RESPONSE);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(myBroadcastReceiver, intentFilter);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unregisterReceiver(myBroadcastReceiver);
		super.onPause();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		EditText editText = (EditText)findViewById(R.id.editText);
		TextView textView = (TextView)findViewById(R.id.textView);
		
		outState.putString("EditText", editText.getText().toString());
		outState.putString("TextView", textView.getText().toString());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		EditText editText = (EditText)findViewById(R.id.editText);
		TextView textView = (TextView)findViewById(R.id.textView);
		
		editText.setText(savedInstanceState.getString("EditText"));
		textView.setText(savedInstanceState.getString("TextView"));
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	public void bdTOus (View v)
	{
		try {
			convertType = true;
			EditText editText = (EditText)findViewById(R.id.editText);
			givenValue = editText.getText().toString();
			if (givenValue.equals(""))
			{
				Toast toast = Toast.makeText(getApplicationContext(), "Please, Input Number To Convert", Toast.LENGTH_SHORT);
	    		toast.show ();
			}
			else
			{
				Intent intent = new Intent(this, HttpConnection.class);
				intent.putExtra(HttpConnection.GIVENVALUE, givenValue);
				intent.putExtra(HttpConnection.CONVERTTYPE, convertType);
				startService(intent);
			}
		}
		catch (Exception exception)
		{
			
		}
	}
	
	public void usTObd (View v)
	{
		try {
			convertType = false;
			EditText editText = (EditText)findViewById(R.id.editText);
			givenValue = editText.getText().toString();
			if (givenValue.equals(""))
			{
				Toast toast = Toast.makeText(getApplicationContext(), "Please, Input Number To Convert", Toast.LENGTH_SHORT);
	    		toast.show ();
			}
			else {
				Intent intent = new Intent(this, HttpConnection.class);
				intent.putExtra(HttpConnection.GIVENVALUE, givenValue);
				intent.putExtra(HttpConnection.CONVERTTYPE, convertType);
				startService(intent);
			}
		}
		catch (Exception exception)
		{
			
		}
	}
	
	public void Clear (View v)
	{
		try {
			EditText editText = (EditText)findViewById(R.id.editText);
			TextView textView = (TextView)findViewById(R.id.textView);
			editText.setText("");
			textView.setText("");
			Toast toast = Toast.makeText(getApplicationContext(), "All Field Is Cleared", Toast.LENGTH_SHORT);
			toast.show ();
		}
		catch (Exception exception)
		{
			
		}
	}
	
	public class MyBroadcastReceiver extends BroadcastReceiver
	{
		public static final String RESPONSE = "naimur.currency.converter.intent.action.RESPONSE";
		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				String convertedValue = intent.getStringExtra(HttpConnection.VALUE);
				EditText editText = (EditText)findViewById(R.id.editText);
				TextView textView = (TextView)findViewById(R.id.textView);
				if (convertType)
				{
					textView.setText(givenValue + " BDT = " + convertedValue + " USD");
					editText.setText("");
				}
				else
				{
					textView.setText(givenValue + " USD = " + convertedValue + " BDT");
					editText.setText("");
				}
			}
			catch (Exception exception)
			{
				
			}
		}
	}
}
