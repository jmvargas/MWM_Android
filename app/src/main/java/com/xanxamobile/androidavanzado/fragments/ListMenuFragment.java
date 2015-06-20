package com.xanxamobile.androidavanzado.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xanxamobile.androidavanzado.R;

/**
 * 
 * @author manu
 *
 */
public class ListMenuFragment extends ListFragment{
	public static String textos[] = 
		{
			"Este es el primer texto de todos",
			"El segundo texto no es tan intuitivo como el segundo pero también vale",
			"tercer texto",
			"cuarto texto",
			"5 Los botones del action bar de arriba están creados por el ListFragment de la izquierda",
			"6 Pulsa el botón de + para añadir un elemento",
			"7 Pulsa la papelera para eliminar un elemento",
			"8 Los nuevos elementos no tendrán texto y si se les pica pondrán a negro el fragment de contenido puesto que no tienen nada escrito",
			"9",
			"10 Ejemplo Realizado por Manuel Sánchez Palacios",
			"11",
			"12",
			"13",
			"14",
			"15",
			"16",
			"17",
			"18 último elemento del ListView",
		};
	
	private static int colors [] = new int[textos.length];

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		List<String> menuLeftItems = new ArrayList<String>();
		for (int i = 0; i < textos.length; i++) {
			menuLeftItems.add((i+1)+".- Menu");	
		}
		this.setListAdapter(new ArrayAdapter<String> (activity, android.R.layout.simple_list_item_1, menuLeftItems){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				 View view = super.getView(position, convertView, parent);
				 if (view != null){
					 //Le ponemos un color aleatorio de background a cada view
					 int color = Color.rgb((int)(255*Math.random()), (int)(255*Math.random()), (int)(255*Math.random()));
					 view.setBackgroundColor(color);
					 if (position < colors.length){
						 colors[position] = color;
					 }
				 }
				 return view;
			}
			
		});
	}
	
	

	@Override
	public void onResume() {
		super.onResume();
		//Accedemos a la ListView y le ponemos un divider customizado por nosotros
		this.getListView().setDivider(getResources().getDrawable(R.drawable.divider));			
	}



	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		
		//Ahora sustituimos un fragmetn por otro
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		ContentFragment contentFragmentNew = new ContentFragment();
		//le ponemos el texto al fragment pasandole los argomentos mediante un Mapa llamado bundle
		Bundle bundle = new Bundle();
		if (position < textos.length){
			bundle.putString(ContentFragment.EXTRA_TEXT, textos[position]);
			bundle.putInt(ContentFragment.EXTRA_COLOR, colors[position]);
		}
		
		contentFragmentNew.setArguments(bundle);
		fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
		fragmentTransaction.replace(R.id.contentFragment, contentFragmentNew, "contentFragmentNew");
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_action_bar, menu);
		//Ponemos invisible el search
		//menu.findItem(R.id.menu_search).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		boolean result = false;
		//Aquí podemos tratar los items
		int id = menu.getItemId();
		ArrayAdapter<String> adapter = ((ArrayAdapter<String>)getListView().getAdapter());
		switch (id) {
		case R.id.menu_add:
			adapter.add((adapter.getCount()+1)+".- Menu");
			result = true;//Hemos capturado el evento por lo que devolveremos true
			break;
		case R.id.menu_delete:
			//Borramos el item con mayor número de la lista
			adapter = ((ArrayAdapter<String>)getListView().getAdapter()); 
			int positionToRemove = adapter.getCount()-1;
			if (positionToRemove >= 0){
				adapter.remove(adapter.getItem(positionToRemove));
			}
			
			result = true;
			break;
		default:
			break;
		}
		if (result){
			adapter.notifyDataSetChanged();
		}
			
		return result;
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Test
	// ===========================================================
	
	
}
