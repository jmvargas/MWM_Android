package com.xanxamobile.androidavanzado.utils;

public class Randomize {

	/**
	 * 
	 * @param max
	 * @return un entero aletorio dentro del rango [0, max]
	 */
	public static int random(int max){
		return (int)(Math.random()*(double)max);
		
	}
}
