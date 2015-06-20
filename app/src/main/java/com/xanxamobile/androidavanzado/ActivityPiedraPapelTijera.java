package com.xanxamobile.androidavanzado;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityPiedraPapelTijera extends Activity {

	// =====================================
	// Constants
	// =====================================
	private final static int PIERDES = 0, EMPATE = 1, GANAS = 2;
	private final static int PIEDRA = 0, PAPEL = 1, TIJERA = 2;
	private final static int[] RES = {R.drawable.piedra, R.drawable.papel, R.drawable.tijera};
	// =====================================
	// Fields
	// =====================================
	/**
	 * Es el image View donde se mostrará la opción elejida por el usuario.
	 */
	ImageView imageViewMyResult;
	/**
	 * Es el imageView donde se mostrará la opción que ha elegido la máquina.
	 */
	ImageView imageViewComputerResult;
	
	/**
	 * Es el textView de puntuación
	 */
	TextView textViewScore;
	
	/**
	 * Es la puntuación en sí.
	 */
	int score;
	// =====================================
	// Constructors
	// =====================================
	
	// =====================================
	// Override Methods
	// =====================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piedra_papel_tijera);
		imageViewMyResult = (ImageView)findViewById(R.id.imageViewResultUser);
		imageViewComputerResult = (ImageView)findViewById(R.id.imageViewResultComputer);
		textViewScore = (TextView)findViewById(R.id.textViewScore);
	}

	// =====================================
	// Methods
	// =====================================
	public void onClickPiedra(View v) {
		changeGraphicAndCalculate(PIEDRA);
	}

	public void onClickPapel(View v) {
		changeGraphicAndCalculate(PAPEL);
	}

	public void onClickTijera(View v) {
		changeGraphicAndCalculate(TIJERA);
	}
	
	public void changeGraphicAndCalculate(int result){
		changeGraphic(imageViewMyResult, result);
		calculateComputer(result);
	}
	
	private void calculateComputer(int myResult) {
		int computerResult = (int)Math.round(Math.random()*((float)TIJERA));
		imageViewComputerResult.setImageResource(RES[computerResult]);
		changeGraphic(imageViewComputerResult, computerResult);
		int result = whoWin (myResult, computerResult);
		updateScore(result);
	}

	private void updateScore(int result) {
		if (result == GANAS){
			score++;
		}else if (result == PIERDES)
			score--;
		this.textViewScore.setText("Score "+score);
	}

	private void changeGraphic(ImageView imageView, int result){
		imageView.setImageResource(RES[result]);
	}
	
	/**
	 * 
	 * @param myResult
	 * @param computerResult
	 * @return 0 has perdido, 1 empatado, 2 ganado
	 */
	private int whoWin(int myResult, int computerResult) {
		int result = EMPATE;
		if (myResult == PIEDRA){
			if (computerResult == TIJERA)
				result = GANAS;
			else if (computerResult == PAPEL)
				result = PIERDES;
		}else if (myResult == PAPEL){
			if (computerResult == PIEDRA)
				result = GANAS;
			else if (computerResult == TIJERA)
				result = PIERDES;
		}else if (myResult == TIJERA){
			if (computerResult == PAPEL)
				result = GANAS;
			else if (computerResult == PIEDRA)
				result = PIERDES;
		}
		return result;
	}
	// =====================================
	// Getters And Setters
	// =====================================


	// =====================================
	// Classes
	// =====================================
}
