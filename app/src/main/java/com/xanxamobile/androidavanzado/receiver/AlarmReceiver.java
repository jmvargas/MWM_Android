package com.xanxamobile.androidavanzado.receiver;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

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
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Hola, Soy la Alarma de las "+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND), Toast.LENGTH_SHORT).show();
	}
	//=====================================
	// Methods
	//=====================================

	//=====================================
	// Getters And Setters
	//=====================================

	//=====================================
	// Classes
	//=====================================
}
