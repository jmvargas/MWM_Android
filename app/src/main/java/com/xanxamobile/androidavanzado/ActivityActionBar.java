package com.xanxamobile.androidavanzado;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class ActivityActionBar extends Activity{

	

	//=================================================
	// Constants
	//=================================================

	//=================================================
	// Fields
	//=================================================
	List<Integer> elements;
	ListView list1;
	//=================================================
	// Constructors
	//=================================================
	
	//=================================================
	// override function
	//=================================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ej3);
		elements = new ArrayList<Integer>();
		elements.add(new Integer((int) (Math.random()*255)));
		list1 = (ListView) findViewById(R.id.ej3list);
		list1.setAdapter(new ArrayAdapter<Integer>(
				this, android.R.layout.simple_list_item_1, elements));
		//CONFIGURAMOS EL ACTION BAR
		ActionBar actionBar = getActionBar();
		//1. Hacemos que el button home tenga funcialidad
		actionBar.setHomeButtonEnabled(true);
		//2. Ponemos que el botón home tenga un símbolod de back
		actionBar.setHomeButtonEnabled(true);
		//3. Le cambiamos el título a la action bar:
		actionBar.setTitle("Action Bar Example");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_action_bar, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_add:
			elements.add(new Integer((int) (Math.random() * 255)));
			list1.setAdapter(new ArrayAdapter<Integer>(
					this, android.R.layout.simple_list_item_1, elements));
			break;
		case R.id.menu_delete:
			if(elements.size()>0){
				elements.remove(elements.size()-1);
				list1.setAdapter(new ArrayAdapter<Integer>(
						this, android.R.layout.simple_list_item_1, elements));
			}
			break;
		case android.R.id.home:
			finish();//Terminamos la activity y volvemos a la lista principal.
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	//=================================================
	// Functions
	//=================================================
	//=================================================
	// Getters and Setters
	//=================================================

	//=================================================
	// Classes
	//================================================

	//=================================================
	// Test
	//=================================================
}
