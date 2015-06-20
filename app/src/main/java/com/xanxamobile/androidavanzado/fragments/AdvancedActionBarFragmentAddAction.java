package com.xanxamobile.androidavanzado.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.R;


/**
 * Este fragment añadirá al action bar varios action buttons
 * @author Manuel Sánchez Palacios
 *
 */
public class AdvancedActionBarFragmentAddAction extends Fragment{

	//=================================================
	// Constants
	//=================================================

	//=================================================
	// Fields
	//=================================================
	TextView textView;
	//=================================================
	// Constructors
	//=================================================
	
	//=================================================
	// override function
	//=================================================
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Para poder añadir elementos a la action bar tenemos que decir en el onCreate que tenemos items
		setHasOptionsMenu(true);
	}	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		textView = new TextView(getActivity());
		textView.setText("Advanced Action Bar Fragment");
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		textView.setLayoutParams(layoutParams);
		return textView;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_add_menu_to_action_bar, menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean itemSelected = false;
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_add:
			Toast.makeText(this.getActivity(), "Fragment Add", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_camera:
			Toast.makeText(this.getActivity(), "Fragment Camera", Toast.LENGTH_SHORT).show();
			itemSelected = true;
			break;
		case R.id.menu_agenda:
			Toast.makeText(this.getActivity(), "Fragment Agenda", Toast.LENGTH_SHORT).show();
			itemSelected = true;
			break;
		case R.id.menu_call:
			Toast.makeText(this.getActivity(), "Fragment Call", Toast.LENGTH_SHORT).show();
			itemSelected = true;
			break;
		/*case R.id.menu_search:
			Toast.makeText(this.getActivity(), "Fragment Search", Toast.LENGTH_SHORT).show();
			itemSelected = true;
			break;*/
		case android.R.id.home:
			Toast.makeText(this.getActivity(), "Fragment Home", Toast.LENGTH_SHORT).show();
			itemSelected = true;
			break;
		default:
			break;
		}
		return itemSelected;
	}
	//=================================================
	// Functions
	//=================================================

	

	//=================================================
	// Getters and Setters
	//=================================================

	//=================================================
	//Classes
	//=================================================

	//=================================================
	// Test
	//=================================================
}
