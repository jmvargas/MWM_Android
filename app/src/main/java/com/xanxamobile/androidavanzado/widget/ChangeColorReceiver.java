package com.xanxamobile.androidavanzado.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.xanxamobile.androidavanzado.R;
import com.xanxamobile.androidavanzado.utils.Painter;

/**
 * Es la clase que cambiará el color de fondo del widget cuando este sea picado.
 * @author Manuel Sánchez Palacios
 *
 */
public class ChangeColorReceiver extends BroadcastReceiver {

	// =====================================
	// Constants
	// =====================================

	// =====================================
	// Fields
	// =====================================

	// =====================================
	// Constructors
	// =====================================

	// =====================================
	// Override Methods
	// =====================================
	@Override
	public void onReceive(Context context, Intent intent) {
		// 1. Obtener el App Widget ID.
		int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
		Bundle extras = intent.getExtras();
		if (extras != null) {
			appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID)
		{
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.change_color_widget);
			Bitmap bitmap = createBitmapMine();
			remoteViews.setImageViewBitmap(R.id.imageViewUniveristyIcon, bitmap);
			// 3. Llamamos a AppWidgetManager
			AppWidgetManager appWManager = AppWidgetManager
					.getInstance(context);
			// 4. Actualizamos el App Widget mediante RemoteViews llamando a:
			appWManager.updateAppWidget(appWidgetId, remoteViews);
		}
	}
	// =====================================
	// Methods
	// =====================================

	private Bitmap createBitmapMine() {
		int width = 110;
		Bitmap bitmap = Bitmap.createBitmap(width*2, width, Config.RGB_565);
		Painter.pintaCuadrados(width*2, width, new Canvas(bitmap),10,new Paint(), 0, 0);
		return bitmap;
	}

	// =====================================
	// Getters And Setters
	// =====================================

	// =====================================
	// Classes
	// =====================================
}
