package com.xanxamobile.androidavanzado.fragments;

import android.app.ActionBar.OnNavigationListener;
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
public class AdvancedActionBarFragmentAddActionView extends Fragment{

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
