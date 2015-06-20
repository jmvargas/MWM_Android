package com.xanxamobile.androidavanzado.widget;

import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.R;

/**
 * @author Manuel Sánchez Palacios
 * 
 */
public class ChangeColorWidgetProvider extends AppWidgetProvider {

	/**
	 * TODO en por el alumno:
	 * Un update como mucho se puede llamar cada 30 Minutos por lo que si queremos un update más continuo deberemos usar alarms.
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for (int i = 0; i < appWidgetIds.length; i++) {
			updateTimeTextView(appWidgetIds[i], context);
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "onDeleted ChangeColorWidget", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Toast.makeText(context, "onDisabled ChangeColorWidget", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Toast.makeText(context, "onEnabled ChangeColorWidget", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Actualiza el textView a la última hora a la que se hizo el el último update.
	 * 
	 * @param appWidgetId
	 * @param context
	 */
	private void updateTimeTextView(int appWidgetId, Context context) {
		
		//Comenzamos obteniendo una RemoteView para actualizar
		AppWidgetManager appWManager = AppWidgetManager.getInstance(context);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.change_color_widget);
		
		remoteViews.setTextViewText(R.id.TextViewDate, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"-"+Calendar.getInstance().get(Calendar.MONTH)+"-"+Calendar.getInstance().get(Calendar.YEAR));
		remoteViews.setTextViewText(R.id.TextViewHour, Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND));
		remoteViews.setImageViewResource(R.id.imageViewUniveristyIcon, R.drawable.ic_launcher);
		
		//Agnadimos el pending intent que acutalizará el fondo del icono. Esto debería de hacerse en la activity de configuración
		addOnClick(appWidgetId, context, remoteViews);
					
		appWManager.updateAppWidget(appWidgetId, remoteViews);
	}

	/**
	 * Registramos cuando el usuario pulsa encima del sello de la universidad para que este cambie.
	 * @param appWidgetId
	 * @param context
	 * @param remoteViews
	 */
	private void addOnClick(int appWidgetId, Context context, RemoteViews remoteViews) {
		Intent intent = new Intent("actionChangeColor");
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.imageViewUniveristyIcon, pendingIntent);
	}
}
