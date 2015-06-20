package com.xanxamobile.androidavanzado;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xanxamobile.androidavanzado.utils.Randomize;

public class ActivityNotifications extends Activity{

	

	//=====================================
	// Constants
	//=====================================

	//=====================================
	// Fields
	//=====================================
	List<Integer> ids;
	//=====================================
	// Constructors
	//=====================================

	//=====================================
	// Override Methods
	//=====================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notifications);
		ids = new ArrayList<Integer>();
	}
	//=====================================
	// Methods
	//=====================================
	public void onClickShowNotification(View view){
		NotificationManager mNotificationManager = (NotificationManager)
		this.getSystemService(Context.NOTIFICATION_SERVICE);
		long when = getWhen();
		Notification notification = new Notification(R.drawable.ic_launcher, "Notification ETSII \n "+getTextDescription(), when);
		//El when no funciona todo lo bien que debería funcionar.
		notification.when = when;
		//Se cancela sola
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_NO_CLEAR;

		//4. Definimos la Activity a la que irá si el usuario pulsa sobre la notification:
		Intent notificationIntent = new Intent(this, AAListActivityMenu.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
		notificationIntent, 0);

//		5. Ponemos el título y la descripción de la notification:
		notification.setLatestEventInfo(this, getTextTitle(), getTextDescription(),	contentIntent);

		
//		6. Establecemos algunos parámetros como que suene el sonido por defecto, vibre, el
//		led se ponga verde y dure 300 Milisegundos encendido y 1000 apagado en un bucle,
//		Mostramos luces.
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
//		7. Finalmente Notificamos con un ID que deberemos conocer si queremos cancelar o
//		actualizar nuestra notificación.
		int id = Randomize.random(1000);
		ids.add(id);
		mNotificationManager.notify(id, notification);

	}
	private CharSequence getTextTitle() {
		return getTextFromView(R.id.editTextTitle);
	}
	
	
	/**
	 * Este es el nuevo mode de crear una notificación para android 3.0
	 * @return una notification de android 3.0
	 */
	private Notification createNotificationNewMode(){
		Notification.Builder notificationBuilder = new Notification.Builder(this);
		notificationBuilder.setAutoCancel(false);
		Intent notificationIntent = new Intent(this, AAListActivityMenu.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
		notificationIntent, 0);
		notificationBuilder.setContentIntent(contentIntent);
		notificationBuilder.setContentText("text");
		notificationBuilder.setContentTitle("Title");
		//Aquí podemos setear más atributos si lo deseamos.
		return notificationBuilder.getNotification();
	}
	
	//=====================================
	// Getters And Setters
	//=====================================
	private CharSequence getTextDescription() {
		return getTextFromView(R.id.EditTextDescription);
	}
	
	private long getWhen() {
		String timeToWaitString = getTextFromView(R.id.editTextWhen);
		long timeToWait = System.currentTimeMillis();
		if (timeToWaitString != null && !timeToWaitString.equals("")){
			timeToWait += 210046+Long.parseLong(timeToWaitString)*1000;//Multiplicamos por 1000 para que sean segundos.
		}
		Log.d("tag", timeToWait+", "+Long.parseLong(timeToWaitString));
		return timeToWait;
	}

	/**
	 * 
	 * @param resource
	 * @return el texto de un TextView 
	 */
	private String getTextFromView(int resource){
		TextView editText = (TextView)findViewById(resource);
		return editText.getText().toString();
	}
	//=====================================
	// Classes
	//=====================================
}
