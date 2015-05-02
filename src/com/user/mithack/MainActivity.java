package com.brianna.mithack;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

public class MainActivity extends ActionBarActivity {
	
	Contacts contact = new Contacts();
	EditText result;
	EditText userInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		result = (EditText) findViewById(R.id.editTextResult);
		String docNum = result.getText().toString();
		contact.setContact(docNum);
	}
	
	private void sendSMS(String number, String message)
	{
		String sent = "SMS_SENT";
		String delivered = "SMS_DELIVERED";
		
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(sent), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(delivered), 0);
		
		registerReceiver(new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context arg0, Intent arg1)
			{
				switch(getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio Off", Toast.LENGTH_SHORT).show();
					break;
				}
			}		
		}, new IntentFilter(sent));
		
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(number, null, message, sentPI, deliveredPI);
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
}
