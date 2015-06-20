package com.xanxamobile.androidavanzado;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.utils.Painter;

/**
 * Aparece un botón con una Shape de fondo que si pulsamos sobre ella cambiará de color automáticamente por ser un selector, si accedemos al botón mediante el el pad de direcciones este se pondrá burdeos 
 * Además de esto pintamos sobre el ImageButton Dos circulos y Dos rectángulos, girando el canvas para pintar uno de ellos y restaurandolo después para pintar el siguiente.
 * @see selector_background.xml  
 * @author Manu
 *
 */
public class GreenHouseActivity extends Activity {
	static int width = -1, height = -1;
	Bitmap bitmap;
	View backMovingView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.green_house);
		Bitmap bitmap = initBitmap();
		ImageButton imageButton = (ImageButton)findViewById(R.id.imageButtonGreenHouse);
		imageButton.setImageBitmap(bitmap);
		initAnimations(imageButton);
		initListeners(imageButton);
		backMovingView = findViewById(R.id.imageViewBackView);
	}
 
	
	private void initListeners(ImageButton imageButton) {
		OnButtonClickListener onButtonClickListener = new OnButtonClickListener();
		findViewById(R.id.ButtonDownload).setOnClickListener(onButtonClickListener);
		findViewById(R.id.button2).setOnClickListener(onButtonClickListener);
		findViewById(R.id.button3).setOnClickListener(onButtonClickListener);
		findViewById(R.id.button4).setOnClickListener(onButtonClickListener);
		imageButton.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				initAnimations(v);
				return true;
			}
		});
	}


	private Bitmap initBitmap() {
		//1. Creamos el bitmap donde vamos a pintar con el tamaño de la pantalla
		bitmap = Bitmap.createBitmap(getWidth(), getHeight()/3, Bitmap.Config.ARGB_8888);
		//2. Iniciamos el Canvas con el bitmap que vamos a pintar haciendo uso del Canvas:
		Canvas canvas = new Canvas(bitmap);
		//3. Pintamos dos Circulos rojo primero y después sobre este uno amarillo de menor tamaño
		int left = getWidth()/4;
		int top = getHeight()/8;
		int right = getWidth()-left;
		int bottom = getHeight();
		Paint paint = new Paint();
		//Le ponemos un filtros para que no se vean los bordes de sierra.
		paint.setAntiAlias(true);
		paint.setColor (Color.RED);
		canvas.drawCircle(left, top, getWidth()/9, paint);
		paint.setColor (Color.YELLOW);
		canvas.drawCircle(left, top, getWidth()/10f, paint);
		//4. Salvamos el estado del canvas para después poderlo restaurar si lo deseamos
		canvas.save();
		//5. Escalamos a la mitad, Rotamos 45 grados el canvas, y lo movemos (también podríamos pintar rotando lo que vallamos a pintar en vez del canvas)
		canvas.scale(0.5f, 0.5f, getWidth()/2, getHeight()/2);
		canvas.rotate(45, getWidth()/2, getHeight()/2);
		canvas.translate(-getWidth()/3, -(bottom-top)/1.5f);
		
		//6 pintamos de nuevo pero ahora un rectángulo verde;
		paint.setColor(Color.rgb(0, 255, 0));
		canvas.drawRect(left, top, right, bottom, paint);
		//7. Restauramos la posición del canvas a antes de la rotación y translación.
		canvas.restore();
		//8. Ponemos el color de la brocha a marrón claro:
		paint.setColor(Color.rgb(255, 128, 64));
		canvas.drawRect(left, top, right, bottom, paint);
		
		return bitmap;
	}
	
	/**
	 * //TODO por el alumno2: Animation
	 * <li>
	 * Hacer una animación de escala (ScaleAnimation) en vez de una de Translate para que el <b>botón de fondo</b> parezca que se acerca a la cámara (desde muy pequeño hasta su tamaño).
	 * </li>
	 * <li>
	 * EXTRA --> Crear una animación de translación similar en AndEngine sobre un botón como la que aparece al principio rebotando
	 * </li>
	 * <li>
	 * EXTRA --> Crear una animación de translación similar en Cocos2D sobre un botón como la que aparece al principio rebotando
	 * </li>
	 * 
	 * Este método inicializa y ejecuta las animaciones sobre la view
	 * @param view
	 */
	private void initAnimations(View view) {
		Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,  Animation.RELATIVE_TO_SELF, 0);
		anim.setAnimationListener(new AnimationListenerRotame(view));
		anim.setRepeatCount(2);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setDuration(2000);
		anim.setInterpolator(view.getContext(), android.R.anim.bounce_interpolator);
		view.startAnimation(anim);
		
		
	}


	public int getHeight() {
		if (height == -1)
			loadHeightAndWidthScreen();
		return height;
	}
	
	public int getWidth() {
		if (width == -1)
			loadHeightAndWidthScreen();
		return width;
	}
	
	private void loadHeightAndWidthScreen() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		height = dm.heightPixels;
		width = dm.widthPixels;
	}
	
	//======================================
	//OnClicks
	//======================================
	/**
	 * Inicia una animación muy simple de rotación sobre el reloj
	 * @param view
	 */
	public void onClickClock (View view){
		Animation rotateAnimation = new RotateAnimation(0, 360);
		rotateAnimation.setDuration(750);
		view.startAnimation(rotateAnimation);
	}
	public void onGreenHouseClick(View view){
		int duration = 1000;
		AnimationSet animationSet = new AnimationSet(true);
		Animation animationAlpha = new AlphaAnimation(1,0.5f);
		animationAlpha.setDuration(duration);
		animationSet.addAnimation(animationAlpha);
		Animation animationScale = new ScaleAnimation(1, 0.5f, 1, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animationScale.setDuration(duration);
		animationScale.setRepeatCount(1);
		animationScale.setInterpolator(view.getContext(), android.R.anim.accelerate_decelerate_interpolator);
		animationScale.setRepeatMode(Animation.REVERSE);
		animationSet.addAnimation(animationScale);
		
		Animation animationAlpha2 = new AlphaAnimation(0.5f, 1);
		animationAlpha2.setStartOffset(duration);
		animationAlpha2.setDuration(duration);
		
		animationSet.addAnimation(animationAlpha2);
		
		view.startAnimation(animationSet);
		/*
		Animation translateAnimation = new TranslateAnimation(150,0,150,0);
		translateAnimation.setDuration(1000);
		view.startAnimation(translateAnimation);
		*/
	}
	
	//==============================
	// Clases
	//==================================
	private class AnimationListenerRotame implements AnimationListener{
		View view;
		
		public AnimationListenerRotame(View view) {
			super();
			this.view = view;
		}
		/**
		 * Se llama cuando la animación ha terminado
		 */
		public void onAnimationEnd(Animation animation) {
			int duration = 1000;
			view.setBackgroundResource(R.drawable.selector_background);
			Animation rotateAnimation = new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
			rotateAnimation.setDuration(duration);
			rotateAnimation.setFillAfter(true);
			view.startAnimation(rotateAnimation);
			view.postDelayed(new Runnable() {
				
				public void run() {
					Toast.makeText(GreenHouseActivity.this, "Púlsame", Toast.LENGTH_SHORT).show();
				}
			}, duration);
		}
		/**
		 * Se llama cuando la animación se está repitiendo
		 */
		public void onAnimationRepeat(Animation animation) {
			view.setBackgroundColor(Painter.getRandomColor());
		}

		/**
		 * Se llama Cuando la animación se empieza
		 */
		public void onAnimationStart(Animation animation) {
			
		}
		
	}
	
	/**
	 * 1. Hemos de crear unas animaciónes de diferencia, o lo que es lo mismo. especificas cuantos píxeles te mueves.
	 * 2. "Teletransportamos" la view e iniciamos la animación.
	 * @author Manuel Sánchez Palacios.
	 *
	 */
	private class OnButtonClickListener implements OnClickListener{
		View lastView;
		public void onClick(View v) {
			if (lastView ==  null){	
				lastView = v;
//				lastView.setBackgroundColor(Color.GREEN);
				lastView.setEnabled(false);
				backToLastView(lastView);
			}else{
				//Podemos hacer los movimientos también mediante diferencias:
				int difX = v.getLeft()-lastView.getLeft();
				int difY = v.getTop()-lastView.getTop();
				
				
				int lastViewXTMP = lastView.getLeft(); 
				int lastViewYTMP = lastView.getTop();
				int lastViewRightTMP = lastView.getRight();
				int lastViewBottomTMP = lastView.getBottom();
				
				int vXTMP = v.getLeft();
				int vYTMP = v.getTop();
				int vRightTMP = v.getRight();
				int vBottomTMP = v.getBottom();
				
				Log.d("tag", "1 lastView left "+lastView.getLeft());
				lastView.layout(vXTMP, vYTMP, vRightTMP, vBottomTMP);
				v.layout(lastViewXTMP, lastViewYTMP, lastViewRightTMP, lastViewBottomTMP);
				Log.d("tag", "2 lastView left "+lastView.getLeft());
				
				Animation translateLastViewAnimation = new TranslateAnimation(-difX, 0, -difY, 0);
				translateLastViewAnimation.setDuration (500);
				
				Animation translateVAnimation = new TranslateAnimation(+difX, 0, +difY, 0);
				translateVAnimation.setDuration (500);
				
				v.startAnimation(translateVAnimation);
				lastView.startAnimation(translateLastViewAnimation);
//				lastView.setBackgroundResource(R.drawable.selector_background);
				lastView.setEnabled(true);
				lastView = null;
			}
		}
		
		
		private void backToLastView(View lastView) {
			backMovingView.setVisibility(View.VISIBLE);
			int difX = backMovingView.getLeft()-lastView.getLeft();
			int difY = backMovingView.getTop()-lastView.getTop();
			
			int add = 1;
			int lastViewXTMP = lastView.getLeft()-add; 
			int lastViewYTMP = lastView.getTop()-add;
			int lastViewRightTMP = lastView.getRight()+add;
			int lastViewBottomTMP = lastView.getBottom()+add;
			
			backMovingView.layout(lastViewXTMP, lastViewYTMP, lastViewRightTMP, lastViewBottomTMP);
			
			Animation translateVAnimation = new TranslateAnimation(+difX, 0, +difY, 0);
			translateVAnimation.setDuration (250);
			backMovingView.startAnimation(translateVAnimation);
			
			
		}
		
	}
}
