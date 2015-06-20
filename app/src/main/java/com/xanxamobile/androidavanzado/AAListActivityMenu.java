package com.xanxamobile.androidavanzado;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * VERSION ANDROID STUDIO.
 * ESTA ES LA ACTIVITY INICIAL, EL PUNTO DE PARTIDA.
 * <pre>
 * Este es un menú donde se muestran todos los ejemplos.
 * 
 * Los ejercicios para hacer en casa están etiquetados como <b>"TODO por el alumno"</b> 
 * para buscar estos TODO pulsa la pestaña TODO abajo a la izquierda.
 * En esta pantalla te aparecerán todos los ejercicios que tienes que hacer.
 * Estos ejercicios no son entregables tan sólo servirán para que aprendas
 * Todos están resueltos en código de otras clases java.
 * 
 * Los distintos ejercicios estarán ordenados con una lista de puntos.
 *    Si encuentras un ejercicio en el que pone "<b>EXTRA --></b>" este será un apartado más complejo
 *    Tan sólo opcional.
 * </pre>
 *
 * TODO por el alumno2: Piedra, papel o tijera ver HTML
 * @author Manuel Sánchez Palacios
 *
 */
public class AAListActivityMenu extends ListActivity {
	/**
	 * ESTAS SON TODAS LAS ACTIVITIES QUE SE UTILIZAN EN NUESTOS EJEMPLOS
	 * Está puesta igual que la lista que aparece en la Pantalla del teléfono
	 */
	public final static Object activityEjemplos[][] = {
		{"Ej 1",CalculatorActivity.class},
		{"Ej 2.1", Ej2_1Activity.class},
			{"Ej 3",ActivityActionBar.class},
			{"Ej 4",Ej4Activity.class},
			{"Ej 6",Ej6Activity.class},
		{"Action Bar Avanzado",AdvancedActionBarActivity.class},
		{"Fragments Example",FragmentActivityExample.class},
		{"Animations",GreenHouseActivity.class},
		{"Animations VS Animator VS LayoutTransitions", ActivityLayoutTransition.class},
		{"Notifications", ActivityNotifications.class},
		{"Alarm", ActivityAlarm.class},
		{"Sensors", ActivitySensor.class},
		{"Gestos", GestureActivity.class},
		{"Reconocimiento de voz", VoiceRecognitionActivity.class},
		{"De texto A voz", TTSActivity.class},
		{"RSS", WidgetRSSReaderActivity.class},
		{"Canvas AsyncTask Animation",ActivityCanvasBackgroundAnimations.class}, 
		{"Activity --> Broadcast --> Service", ActivityBroadCastReceiverAndService.class},
		{"Piedra, papel, tijera",ActivityPiedraPapelTijera.class},
		};
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        Intent intent = getIntent();
	        String path = intent.getStringExtra("com.example.android.apis.Path");
	        
	        if (path == null) {
	            path = "";
	        }
	        String nombreEjemplos[] = new String[this.activityEjemplos.length];
	        for (int i = 0; i < nombreEjemplos.length; i++) {
				nombreEjemplos[i] = (String)this.activityEjemplos[i][0];
			}
	        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nombreEjemplos));
	        //Le ponemos fondo a la activity
	        this.getListView().setBackgroundResource(R.drawable.background);
	    }
	  
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		 Intent intent = null;
		intent = new Intent(this, (Class)activityEjemplos[position][1]);//Cargamos la activity
		startActivity(intent);
    }
}
