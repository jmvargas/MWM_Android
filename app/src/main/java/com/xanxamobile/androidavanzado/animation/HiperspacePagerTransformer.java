package com.xanxamobile.androidavanzado.animation;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * Este es un ejemplo simple de cómo hacer una animación con el pageTransformer
 * @author Manuel Sánchez Palacios
 *
 */
public class HiperspacePagerTransformer implements PageTransformer {

	public void transformPage(View view, float position) {
		view.setRotation(position*720);
	}

	//=================================================
	// Constants
	//=================================================

	//=================================================
	// Fields
	//=================================================

	//=================================================
	// Constructors
	//=================================================
	//=================================================
	// override function
	//=================================================

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
