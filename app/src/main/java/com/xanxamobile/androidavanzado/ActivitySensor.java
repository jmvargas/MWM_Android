package com.xanxamobile.androidavanzado;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Es una activity que devuelve los valores de un sensor.
 * @author Manuel Sánchez Palacios.
 *
 */
public class ActivitySensor extends Activity {

	

	//=====================================
	// Constants
	//=====================================

	//=====================================
	// Fields
	//====================================
	/**
	 * Servicio que te ofrece Android para acceder a los distintos sensores del sistema
	 */
	private SensorManager sensorManager;
	/**
	 * Cualquiera de los sensores a los que tiene acceso el sensorManager
	 */
	private Sensor sensor;
	/**
	 * Es el que recoge los distintos valores actualizados del sensor.
	 */
	private SensorEventListener listener;
	//=====================================
	// Constructors
	//=====================================

	//=====================================
	// Override Methods
	//=====================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		initSensor();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		stopSensor();
	}

	public void initSensor(){
		if (sensorManager == null)
		  sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		if (sensor == null)
	      sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		if (listener == null)
			listener = new SensorEventListenerMine();
	      sensorManager.registerListener(listener, sensor,
	                SensorManager.SENSOR_DELAY_NORMAL);     
	}
	
	public void stopSensor(){
		sensorManager.unregisterListener(listener);
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
	private class SensorEventListenerMine implements SensorEventListener{
		Toast toastToShowSensor;
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent event) {
			if (toastToShowSensor == null)
				toastToShowSensor = Toast.makeText(ActivitySensor.this, "", Toast.LENGTH_SHORT);
			toastToShowSensor.setText("Sensor Values: "+event.values[0] + ", " + event.values[1] + ", " + event.values[2] );
			toastToShowSensor.show();
		}
		
	}
}
