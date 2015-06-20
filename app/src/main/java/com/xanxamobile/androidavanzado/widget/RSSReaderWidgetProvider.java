package com.xanxamobile.androidavanzado.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
/**
 * TODO por el alumno:
 * <li>
 * Terminar la implementación del RSSReaderWidgetProvider y todos los archivos necesarios para que el widgetFuncione.
 * </li>
 * <li>
 * Hacer que el widget diga qué día de la semana y del mes es. cambiando entre día de la semana (lun,mar,mier,jue,vier) y día del mes/mes/agno (21-02-2012) en cada update.
 * </li>
 * <li>
 * Hacer que el widget lance la activity WidgetRSSReaderActivity.
 * </li>
 * @author Manuel Sánchez Palacios
 *
 */
public class RSSReaderWidgetProvider extends AppWidgetProvider{

	/**
	 * TODO por el alumno: AlarmManager
	 * <li>
	 * Extra --> Hacer uso de alarmas para la acutalización en vez de utilizar onUpdate
	 * </li>
	 * <li>
	 * Extra --> Incorporar a la Activity de Preferencias un modo de que el usuario pueda modificar este periodo de actualización (Un EditText o una SeekBar...)
	 * </li>
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}	
}
