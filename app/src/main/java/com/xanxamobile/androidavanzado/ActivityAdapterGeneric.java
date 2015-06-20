package com.xanxamobile.androidavanzado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.utils.Randomize;
import com.xanxamobile.androidavanzado.utils.ViewUtil;

/**
 * En esta activity mostramos cómo con una sola fuente de datos (Adapter) podemos crear muchas formas de mostrarlos en disitintas Views de forma optimizada.
 * @author Manuel Sánchez Palacios
 *
 */
public class ActivityAdapterGeneric extends Activity {

	private List<Integer> colores;
	private Adapter adapter;
	private ViewGroup viewGroupToAddAdapterView;
	private AdapterView actualAdapterView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adapter_generic);
		colores = new ArrayList<Integer>();
		colores.add(Color.BLUE);
		colores.add(Color.GREEN);
		colores.add(Color.RED);
		adapter = new AdapterGeneric(this, colores);
		viewGroupToAddAdapterView = (ViewGroup) findViewById(R.id.linearLayoutToAddAdapterView);
		Toast.makeText(this, "Pulsa uno de los botones para ver las AdapterViews", Toast.LENGTH_LONG).show();
	}

	/**
	 * método para cuando se pulsa el botón que hace que aparezca la listView; 
	 * @param view
	 */
	public void onClickListView (View view){
		//Primero creamos un listView (lo optimo sería cachearlo, pero no vamos a hacerlo en este ejemplo para que cada vez sea uno nuevo)
		actualAdapterView = new ListView(this);
		((ListView)actualAdapterView).setDivider(view.getResources().getDrawable(R.drawable.divider));
		attachToViewAndAdapter(actualAdapterView);

	}

	/**
	 * método para cuando se pulsa el botón que hace que aparezca el gridView; 
	 * @param view
	 */
	public void onClickGridView (View view){
		actualAdapterView = new GridView(this);
		((GridView)actualAdapterView).setNumColumns(3);
		attachToViewAndAdapter(actualAdapterView);		
	}
	
	/**
	 * método para cuando se pulsa el botón que hace que aparezca la Gallery; 
	 * @param view
	 */
	public void onClickGallery (View view){
		actualAdapterView = new Gallery(this);
		attachToViewAndAdapter(actualAdapterView);		
	}
	
	/**
	 * //TODO En clase: hacer que se añadan nuevos colores al adapter
	 * Se llama cuando se quiere agnadir un nuevo elemento al ArrayAdapter
	 * @param view
	 */
	public void onClickAddItem(View view){
		//Podemos agnadir objetos de dos formas:
		//1. agnadiendoselo al adapter
		//2. agnadiendoselo directamente a la lista.
		//En realidad las dos formas hacen básicamente lo mismo puesto que modificarán la lista.
		ArrayAdapter arrayAdapter = ((ArrayAdapter<Integer>)adapter);
		arrayAdapter.add(Color.rgb(Randomize.random(255), Randomize.random(255), Randomize.random(255)));
		//3. Hacemos que se seleccione el item que acabamos de agnadir.
		if (actualAdapterView != null){
			actualAdapterView.setSelection(arrayAdapter.getCount()-1);
		}else{
			Toast.makeText(this, "Añade alguna interfaz dándole a los botones de la izquierda para que se muestre algo", Toast.LENGTH_LONG).show();

		}

	}
	
	/**
	 * Primero agnade el AdapterView a la pantallla
	 * Tras esto enlaza los datos con el AdapterView asignandole el adapter al AdapterView
	 * @param listView
	 */
	private void attachToViewAndAdapter(AdapterView adapterView) {
		//Borramos todos los hijos de la view donde vamos a agnadir el adapterView por si había ya otro adapterView.
				viewGroupToAddAdapterView.removeAllViews();
				viewGroupToAddAdapterView.addView(adapterView);
		//Finalmente añadimos el adapter
		adapterView.setAdapter(adapter);
	}
	
	/**
	 * Este es el adapter generico para todas las AdapterViews definidas
	 */
	private class AdapterGeneric extends ArrayAdapter<Integer>{
		HashMap<Integer, String> colorTextHashMap;
		public AdapterGeneric(Context context, List<Integer> objects) {
			super(context, -1, objects);
			colorTextHashMap = new HashMap<Integer, String>();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//1. Si el convertView es nulo hemos de inicializarlo, si no, lo reutilizamos:
			if (convertView == null){
				//Inflamos la vista pasando de del xml adapter_generic_item a un objeto visible.
				convertView = ViewUtil.inflateView(parent.getContext(), R.layout.adapter_generic_item, parent,false);
				Wrapper wrapper = new Wrapper(convertView);
				//Dentro del convertView alojamos el wrapper para reutilizar las búsquedas del textView.
				convertView.setTag(wrapper);
			}
			
			//Recuperamos el wrapper de la convert View
			Wrapper wrapper = (Wrapper)convertView.getTag();
			int color = getItem(position);
			//Obtenemos el texto del color
			String text = colorTextHashMap.get(color);
			//Si no lo tenemos cacheado lo calculamos y lo cacheamos en un hashMap.
			//Si este paso fuese muy pesado tendríamos que hacerlo en segundo plano.
			if (text == null)
			{
				text = getColorText(color);
				colorTextHashMap.put(color, text);
			}
			//Finalmente usamos el texto obtenido y lo mostramos.
			wrapper.getTextViewColorName().setText(text);
			//Cambiamos el fondo de la convertView
			convertView.setBackgroundColor(color);
			//Devolvemos la convertView con el fondo del color deseado
			return convertView;
		}
	}
	
	/**
	 * Es una clase que cachea las búsquedas de Hijos de la View base
	 * @author Manuel Sánchez Palacios
	 *
	 */
	private class Wrapper {
		View base;
		TextView textViewColorName;

		public Wrapper(View base) {
			super();
			this.base = base;
		}
		/**
		 * Con este método nos ahorramos tener que hacer un findViewById, que es muy lento, cada vez que preguntemos por el textView
		 * @return el text view actual
		 */
		public TextView getTextViewColorName() {
			if (textViewColorName == null)
				textViewColorName = (TextView)base.findViewById(R.id.textViewColorName);
			return textViewColorName;
		}
	}
	
	//================================================
	// Getters and setters
	//================================================
	private String getColorText(Integer color) {
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		StringBuffer stringBuffer = new StringBuffer();
		String comma = ", ";
		stringBuffer.append(red);
		stringBuffer.append(comma);
		stringBuffer.append(green);
		stringBuffer.append(comma);
		stringBuffer.append(blue);
		return stringBuffer.toString();//red+", "+green+", "+blue;
	}
}
