package com.xanxamobile.androidavanzado;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.receiver.AlarmReceiver;

/**
 * 
 * @author Manuel Sánchez Palacios
 *
 */
public class ActivityAlarm extends Activity {

	//=====================================
	// Constants
	//=====================================

	//=====================================
	// Fields
	//=====================================

	//=====================================
	// Constructors
	//=====================================

	//=====================================
	// Override Methods
	//=====================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarms);
	}
	//=====================================
	// Methods
	//=====================================
	/**
	 * Método que lanzará la alarma cuando sea necesario.
	 * @param view
	 */
	public void onClickShowAlarm (View view){
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		AlarmManager am = (AlarmManager)this.getSystemService(Activity.ALARM_SERVICE);
		long time = showAlarmToast(getTime());
		am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
	}
	
	private long showAlarmToast(long timetoAdd) {
		long result = System.currentTimeMillis()+timetoAdd;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(result);
		Toast.makeText(this, "Creada su alarma para las "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND), Toast.LENGTH_SHORT).show();
		return result;
	}
	/**
	 * 
	 * @return el tiempo que hemos puesto en el when
	 */
	private long getTime() {
		long result = 0;
		EditText editTextWhen = (EditText)findViewById(R.id.editTextWhen);
		String whenString = editTextWhen.getText().toString();
		if (whenString != null && !whenString.equals(""))
		{
			result = Long.parseLong(whenString)*1000;//Multiplicamos por 1000 para que sean segundos. 
		}
		return result;
	}
	
	//=====================================
	// Getters And Setters
	//=====================================

	//=====================================
	// Classes
	//=====================================
}
