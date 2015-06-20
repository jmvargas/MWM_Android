package com.xanxamobile.androidavanzado;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.utils.Painter;
import com.xanxamobile.androidavanzado.utils.Randomize;

/**
 * En esta clase veremos cómo hacer uso del proceso en background para realizar una tarea costosa.
 * La tarea será pintar mediante un Canvas en un Bitmap multiples puntos y líneas.
 * Mientras se informa al usuario y la interfaz gráfica se mueve a derecha e izquierda sin inmutarse hasta que es pintada.
 * 
 * @author Manuel Sánchez Palacios
 *
 */
public class ActivityCanvasBackgroundAnimations extends Activity {
	
	////////////////////////////////////////////////
	// Constants
	///////////////////////////////////////////////
	/**
	 * Número de líneas que vamos a pintar
	 */
	private final static int MAX_LINES = 10000;
	
	////////////////////////////////////////////////
	// Fields
	///////////////////////////////////////////////
	/**
	 * Características de la brocha con la que pintamos
	 */
	private Paint paint = new Paint();
	/**
	 * Toast de información para saber cómo está el porcentaje.
	 */
	private Toast actualPercentageToast;
	////////////////////////////////////////////////
	// functions
	///////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas_background_animations);
	}
	
	/**
	 * Es la función que se llama automáticamente cuando presionamos la imagen con el degradado
	 * @param image
	 */
	public void onImagePressed(View image){
		animaImageButton();
		AsyncTask<ImageButton, Integer, Bitmap> asyncTask = new PintaAsyncTask();
		asyncTask.execute((ImageButton)image);
	}
	
	

	/**
	 * Es la función que pinta todos los píxeles aleatoriamente de un color.
	 * 
	 * @param width
	 * @param height
	 * @param canvas
	 */
	public void pintaPixeles(int width, int height, Canvas canvas){
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				paint.setColor(Painter.getRandomColor());
				canvas.drawPoint(i, j, paint);
			}
		}
	}


	
	/**
	 * Pinta MAX_LINES líneas "aleatorias"
	 * @param width
	 * @param height
	 * @param canvas
	 */
	private void pintaLineas(int width, int height, Canvas canvas) {
		for (int i = 0; i < MAX_LINES; i++) { 
			paint.setColor(Painter.getRandomColor());
			canvas.drawLine(Randomize.random(width)+width/4, Randomize.random(height)+height/4, Randomize.random(width)+width/4, Randomize.random(height)+height/4, paint);
		}
	}
	

	
	/**
	 * Crea un bitmap con un tamaño definido en píxeles
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap createBitmap(int width, int height) {
		//No vamos a necesitar transparencias por lo que RGB en vez de ARGB
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
		return bitmap;
	}
	

	
	private void animaImageButton() {
		View imageButton = findViewById(R.id.imageButtonCanvasBackgroundAnimations);
		Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		translateAnimation.setDuration(1000);
		translateAnimation.setRepeatCount(Animation.INFINITE);
		translateAnimation.setRepeatMode(Animation.REVERSE);
		translateAnimation.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		imageButton.startAnimation(translateAnimation);
	}
	
	/**
	 * Es la clase que se va a dedicar de un modo asincrono a crear la nueva imagen
	 */
	private class PintaAsyncTask extends AsyncTask<ImageButton, Integer, Bitmap>{
		ImageButton imageButton;
		
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.publishProgress(0);
		}

		@Override
		protected Bitmap doInBackground(ImageButton... imageViews) {
			//1. Calculamos el ancho y el alto de la view
			this.imageButton = imageViews[0];
			int width = imageViews[0].getWidth();
			int height = imageViews[0].getHeight();
			//2. Creamos el bitmap
			Bitmap bitmap = createBitmap(width, height);
			
			//3. asignamos un lienzo "con sus brochas" al bitmap para poder pintar en el
			Canvas canvas = new Canvas(bitmap);
			
			//4. pintamos los pixeles
			pintaPixeles(width, height, canvas);
			
			//5. Publicamos que ya llevamos el 10% de la creación de nuestra "obra de arte".
			this.publishProgress(10);
			
			//6 pintamos unas cuantas líneas
			for (int i = 0; i < 8; i++) {
				pintaLineas(width, height, canvas);
				//Volvemos a publicar el porcentaje.
					this.publishProgress(20+i*10);
			}
			Painter.pintaCuadrados(width, height, canvas, MAX_LINES, paint,width/2,height/2);
			//Volvemos a publicar el porcentaje.
			this.publishProgress(100);
			return bitmap;
		}

		

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			String textToShow = values[0]+"%";
			//1. Si ya se está informando cancelamos esta información y la actualizamos
			if (actualPercentageToast != null)
			{
				//actualPercentageToast.cancel();
				actualPercentageToast.setText(textToShow);
			}else {
				actualPercentageToast = Toast.makeText(ActivityCanvasBackgroundAnimations.this, textToShow, Toast.LENGTH_SHORT);
			}
			
			//2. Informamos al usuario del porcentaje completado mediante un Toast.
			actualPercentageToast.show();
			
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			imageButton.setImageBitmap(result);
			imageButton.startAnimation(new AlphaAnimation(1, 1));
		}
	}
}
