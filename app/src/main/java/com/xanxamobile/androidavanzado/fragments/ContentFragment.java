package com.xanxamobile.androidavanzado.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xanxamobile.androidavanzado.R;

public class ContentFragment extends Fragment {



	// ===========================================================
	// Constants
	// ===========================================================
	/**
	 * Es el texto que hemos de ponerle al fragment
	 */
	public final static String EXTRA_TEXT = "extra_text"; 
	public final static String EXTRA_COLOR = "extra_color"; 
	
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        //Si hemos guardado un texto anteriormente
        String text = null;
        int color = Color.BLACK;
        if (savedInstanceState != null){
        	text = savedInstanceState.getString(EXTRA_TEXT);
        	color = savedInstanceState.getInt(EXTRA_COLOR);
        }
        if (text == null){
        	Bundle bundle = getArguments();
        	if (bundle != null){
        		text = bundle.getString(EXTRA_TEXT);
        		color = bundle.getInt(EXTRA_COLOR);
        	}
        }else {
        	text += " - Text from Saved Instance State";
        }
        getTextView(view).setText(text);
        initGradient(view, color);
        return view;
    }
	
	

	
	
	
	// ===========================================================
	// Methods
	// ===========================================================
    
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================





	/**
	 * Creamos un background para la view del fragment
	 * @param view
	 */
	private void initGradient(View view, int color) {
		int colors[] = {Color.BLACK, color};
		GradientDrawable gradient = new GradientDrawable(Orientation.LEFT_RIGHT,colors);
		view.setBackgroundDrawable(gradient);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
    public TextView getTextView(View view){
    	return ((TextView)view.findViewById(R.id.textViewContentView));
    }
	// ===========================================================
	// Test
	// ===========================================================
    
    
}
