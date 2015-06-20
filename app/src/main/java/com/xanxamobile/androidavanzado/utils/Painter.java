package com.xanxamobile.androidavanzado.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Painter {

	//=====================================
	// Constants
	//=====================================

	//=====================================
	// Fields
	//=====================================

	//=====================================
	// Constructors
	//=====================================

	//=====================================
	// Override Methods
	//=====================================

	//=====================================
	// Methods
	//=====================================
	public static void pintaCuadrados(int width, int height, Canvas canvas, int MaxRect, Paint paint, int offSetWidth, int offsetHeight) {
		for (int i = 0; i < MaxRect; i++) { 
			paint.setColor(getRandomColor());
			canvas.drawRect(Randomize.random(width)+offSetWidth, Randomize.random(height)+offsetHeight, Randomize.random(width)+offSetWidth, Randomize.random(height)+offsetHeight, paint);
		}
	}
	
	/**
	 * 
	 * @return un color cualquiera
	 */
	public static int getRandomColor() {
		int maxColor = 255;
		int result = Color.rgb(Randomize.random(maxColor), Randomize.random(maxColor), Randomize.random(maxColor));
		return result;
	}
	//=====================================
	// Getters And Setters
	//=====================================

	//=====================================
	// Classes
	//=====================================
}
