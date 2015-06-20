package com.xanxamobile.androidavanzado;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.xanxamobile.androidavanzado.utils.Painter;

/**
 * Tenemos tres columnas con animaciones, la primera son las animaciones estandars, la segunda son animaciones mediante animators compatibles sólo con Honeycomb en adelante, la tercera utilizan animation compatibles con todos los dispositivos android y la última crea su propio LayoutTransition
 * @author XanXa
 *
 */
@SuppressLint("NewApi")
public class ActivityLayoutTransition extends Activity {
	//=================================================
	// Constants
	//=================================================
	/**
	 * Son las animaciones transition por defecto
	 */
	public final static int ANIMATION_LAYOUT_TRANSITION_STANDARD = 0;
	/**
	 * Son animaciones creadas mediante animator
	 */
	public final static int ANIMATION_ANIMATOR = 1;
	/**
	 * Son animaciones creadas compatibles con todos los terminales android existentes
	 */
	public final static int ANIMATION_OLD_ANIMATION = 2;
	/**
	 * Animaciones 
	 */
	public final static int ANIMATION_MY_LAYOUT_TRANSITION = 3;
	//=================================================
	// Fields
	//=================================================
	LinearLayout linearLayoutDefaultlayoutTransition;
	LinearLayout animator;
	LinearLayout animation;
	LinearLayout myLinearLayoutLayoutTransition;
	
	private LayoutTransition mLayoutTransition;
	
	List<LinearLayout> linearLayouts = new ArrayList<LinearLayout>();
	//=================================================
	// Constructors
	//=================================================
	
	//=================================================
	// override function
	//=================================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_transition);
		getActionBar().setTitle("Pulsa + o los rectángulos creados");	
		
		linearLayoutDefaultlayoutTransition = (LinearLayout)findViewById(R.id.LinearLayoutTransition);
		animator = (LinearLayout)findViewById(R.id.LinearLayoutAnimator);
		animation = (LinearLayout)findViewById(R.id.LinearLayoutAnimation);
		myLinearLayoutLayoutTransition = (LinearLayout)findViewById(R.id.LinearLayoutOwnLayoutTransition);
		linearLayouts.add(linearLayoutDefaultlayoutTransition);
		linearLayouts.add(animator);
		linearLayouts.add(animation);
		linearLayouts.add(myLinearLayoutLayoutTransition);
		
		initLayoutTransition (myLinearLayoutLayoutTransition);
		initUpButtons ();
	}
	
	

	private void initUpButtons() {
		findViewById(R.id.ButtonAnimator).setOnClickListener(new OnClickListeneAnimator());
	}



	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_action_bar, menu);
	    menu.findItem(R.id.menu_delete).setVisible(false);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_add:
			addItem();
			break;
		case R.id.menu_delete:
			
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
	
	private void initLayoutTransition(LinearLayout linearLayoutLayoutTransition) {
		 mLayoutTransition = new LayoutTransition();
		 mLayoutTransition.setStagger(LayoutTransition.CHANGE_APPEARING, 500);
         mLayoutTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 500);
         
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
        	 //A partir de android 4.1
	         mLayoutTransition.setStagger(LayoutTransition.CHANGING, 500);
	         mLayoutTransition.enableTransitionType(LayoutTransition.CHANGING);
	         //Podemos editar con unanimator
//	         PropertyValuesHolder bottomProperty =
//	                 PropertyValuesHolder.ofInt("bottom", 0, 1);
//	         final ObjectAnimator change = ObjectAnimator.ofPropertyValuesHolder(
//	        		 linearLayoutLayoutTransition, bottomProperty).
//	                 setDuration(mLayoutTransition.getDuration(LayoutTransition.CHANGING));
//	         mLayoutTransition.setAnimator(LayoutTransition.CHANGING, change);
         }
         //Asignamos la transicion
		 linearLayoutLayoutTransition.setLayoutTransition(mLayoutTransition);
	}
	
	/**
	 * Añade un item a cada scroll view
	 */
	private void addItem() {
		int i = 0;
		for (LinearLayout linearLayout : linearLayouts) {
			View view = new View(this);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 100));
			view.setBackgroundColor(Painter.getRandomColor());
			view.setOnClickListener(getOnClickListener(i));
			linearLayout.addView(view);
			postCreated(view, i);
			i++;
		}
	}
	
	private void postCreated(View view, int animationType) {
		switch (animationType) {
		case ANIMATION_OLD_ANIMATION:
			initOldAnimationCreated(view);
			break;
		case ANIMATION_ANIMATOR:
			initAnimatorCreated(view);
		break;
		default:
			break;
		}
		
	}

	private void postResize(View view, int animationType) {
		switch (animationType) {
		case ANIMATION_ANIMATOR:
			postResizedAnimator(view);
		break;
		case ANIMATION_OLD_ANIMATION:
			postResizedOldAnimated(view);
			break;
		case ANIMATION_MY_LAYOUT_TRANSITION:
			view.requestLayout();
			break;
		default:
			break;
		}
	}

	private boolean preRemoveAnimation(View view, int animationType) {
		boolean hasToRemove = true;
		switch (animationType) {
		case ANIMATION_ANIMATOR:
			preRemoveAnimator(view);
			hasToRemove = false;
		break;
		case ANIMATION_OLD_ANIMATION:
			preRemoveOldAnimated(view);
			break;

		default:
			break;
		}
		return hasToRemove;
	}
	/////////////////////////////////////////////////////////////////
	//Animator
	//////////////////////////////////////////////////////////////////
	
	private void initAnimatorCreated(View view){
		view.setAlpha(0);
		view.animate()
			.setDuration(500)
			.alpha(1);
			;
	}
	
	/**
	 * Min android 3.1 (API LEVEL 12)
	 * @param view
	 */
	private void postResizedAnimator(View view) {
		view.setPivotY(0);//Ponemos que pivote sobre el 0 en y para que no se escale desde el centro de la imagen sino que esta animación se inicie desde arriba (y = 0)
		view.setScaleY(0.5f);
		view.animate()
			.scaleY(1)
			.setDuration(500)
			.withLayer()
			;
		//Además de escalar la view actual tenemos que mover la siguiente hacia delante
		//1. Cogemos el padre
		ViewGroup viewGroup = (ViewGroup)view.getParent();
		
		//2. buscamos el hermano siguiente
		int nextViewIndex = calculateViewIndex (view, viewGroup)+1;
		while (viewGroup.getChildCount() > nextViewIndex){
			View nextView = viewGroup.getChildAt(nextViewIndex);
			//una vez encontrada la movemos hacia delante
			nextView.animate()
				.yBy(view.getHeight())
				.setDuration(500);
				;
			nextViewIndex++;
		}
	}
	
	
	/**
	 * Min android 3.1 (API LEVEL 12)
	 * @param view
	 */
	private void preRemoveAnimator(final View view) {
		final ViewGroup viewGroup = ((ViewGroup)view.getParent());
		view.animate()
			.scaleY(0)
			.setDuration(500)
			.withEndAction(new Runnable() {
				public void run() {
					viewGroup.removeView(view);
				}
			});
		
		//El resto de views también han de animarse
		int nextViewIndex = calculateViewIndex (view, viewGroup)+1;
		while (viewGroup.getChildCount() > nextViewIndex){
			final View nextView = viewGroup.getChildAt(nextViewIndex);
			//una vez encontrada la movemos hacia arriba
			ObjectAnimator animationTranslate = ObjectAnimator.ofFloat(nextView, "y", nextView.getTop()-view.getHeight());
			animationTranslate.setDuration(500);
			
			animationTranslate.start();
			
			animationTranslate.addListener(new AnimatorListener() {
				
				public void onAnimationStart(Animator animation) {
				}
				
				public void onAnimationRepeat(Animator animation) {
					
				}
				
				public void onAnimationEnd(Animator animation) {
					//Tenemos que devolverlo a su posición ya que al eliminar la view anterior subimos el resto.
					nextView.setY(nextView.getY()+view.getHeight());
				}
				
				public void onAnimationCancel(Animator animation) {
					
				}
			});
//			nextView.animate()
//				.setDuration(500)
////			.translationYBy(-view.getHeight())
////			.yBy(-view.getHeight())
//				;
			nextViewIndex++;
		}
	}



	////////////////////////////////////////////////////////////////
	//old animation
	///////////////////////////////////////////////////////////////
	/**
	 * Crea la animación de aparcición para la columna de antigüas
	 * @param view
	 */
	private void initOldAnimationCreated(View view) {
		Animation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setInterpolator(this, android.R.anim.accelerate_interpolator);
		alphaAnimation.setDuration(500);
		view.startAnimation(alphaAnimation);
	}
	
	/**
	 * Animación de resize que pone al doble de su tamaño la casilla
	 * @param view
	 */
	private void postResizedOldAnimated (View view){
		Animation scaleAnimation = new ScaleAnimation(1, 1, 0.5f, 1, 0, 0);
		scaleAnimation.setDuration(500);
		scaleAnimation.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		view.startAnimation(scaleAnimation);
		
		//Además de escalar la view actual tenemos que mover la siguiente hacia delante
		//1. Cogemos el padre
		ViewGroup viewGroup = (ViewGroup)view.getParent();
		
		//2. buscamos el hermano siguiente
		int nextViewIndex = calculateViewIndex (view, viewGroup)+1;
		while (viewGroup.getChildCount() > nextViewIndex){
			View nextView = viewGroup.getChildAt(nextViewIndex);
			//una vez encontrada la movemos hacia delante
			Animation translateAnimation = new TranslateAnimation(0, 0, -view.getHeight(), 0);
			scaleAnimation.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
			translateAnimation.setDuration(500);
			nextView.startAnimation(translateAnimation);
			nextViewIndex++;
		}
	}
	
	private void preRemoveOldAnimated(View view) {
		Animation scaleAnimation = new ScaleAnimation(1, 1, 1, 0, 0, 0);
		scaleAnimation.setDuration(500);
		scaleAnimation.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		view.startAnimation(scaleAnimation);
		
		//Además de escalar la view actual tenemos que mover la siguiente hacia delante
		//1. Cogemos el padre
		ViewGroup viewGroup = (ViewGroup)view.getParent();
		
		//2. buscamos el hermano siguiente
		int nextViewIndex = calculateViewIndex (view, viewGroup)+1;
		while (viewGroup.getChildCount() > nextViewIndex){
			View nextView = viewGroup.getChildAt(nextViewIndex);
			//una vez encontrada la movemos hacia arriba
			Animation translateAnimation = new TranslateAnimation(0, 0, view.getHeight(), 0);
			scaleAnimation.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
			translateAnimation.setDuration(500);
			nextView.startAnimation(translateAnimation);
			nextViewIndex++;
		}
	}

	//=================================================
	// Getters and Setters
	//=================================================
	/**
	 * Buscamos el index para una view dentro de un viewGroup
	 * @param view
	 * @param viewGroup
	 * @return la posición donde se encuentra la view, -1 en caso de error
	 */
	private int calculateViewIndex(View view, ViewGroup viewGroup) {
		int result = -1;
		int count = viewGroup.getChildCount();
		for (int i = 0; i < count; i++) {
			View viewTmp = viewGroup.getChildAt(i);
			if (viewTmp.equals(view)){
				//Si es la view entonces hemos encontrado el index, terminamos la búsqueda.
				result = i;
				break;
			}
		}
		
		return result;
	}



	private OnClickListener getOnClickListener(int i) {
		OnClickListener listener = new BiggerAndDisappearListener(i); 
		return listener;
	}

	//=================================================
	//Classes
	//=================================================
	public class BiggerAndDisappearListener implements OnClickListener {
		int state = 0;
		int animationType;
		
		public BiggerAndDisappearListener(int animationType) {
			super();
			this.animationType = animationType;
		}



		public void onClick(View v) {
			if (state == 0){
				//La primera vez que lo tocamos lo hacemos más grande
				LayoutParams layoutParams = v.getLayoutParams();
				layoutParams.height += layoutParams.height;
				v.setLayoutParams(layoutParams);
				postResize (v, animationType);
				state++;
			}else {
				//La segunda vez que lo tocamos lo hacemos desaparecer
				if (preRemoveAnimation(v, animationType)){
					ViewGroup parent = (ViewGroup)v.getParent();
					parent.removeView(v);
				}	
			}
		}



	
	}
	
	//////// Animaciones para los buttons de arriba
	public class OnClickListeneAnimator implements OnClickListener {
		final int MAX_ANIMATIONS = 3;
		int actualAnimation = 0;
		
		public void onClick(View v) {
			ViewPropertyAnimator viewPropertyAnimator = null;
			switch (actualAnimation) {
			case 0:
				viewPropertyAnimator = v.animate().alpha(0.5f);
				break;
			case 1:
				viewPropertyAnimator = v.animate().alpha(1f);
			break;
			case 2:
				animatorSet(v);
				
				break;
			default:
				break;
			}
			
			if (viewPropertyAnimator != null){
				viewPropertyAnimator.setDuration(500);
			}
			actualAnimation = (actualAnimation+1)%MAX_ANIMATIONS;
		}

		private void animatorSet(View v) {
			AnimatorSet animatorSet = new AnimatorSet();
			//1. Creamos varias ObjectAnimator
			ValueAnimator animatorFadeOut = ObjectAnimator.ofFloat(v, "alpha", 1f, 0.5f);
			animatorFadeOut.setInterpolator(new BounceInterpolator());
			animatorFadeOut.setDuration(500);
			Animator animatorFadeIn = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
			animatorFadeIn.setDuration(500);
			ValueAnimator animatorChangeBack = ObjectAnimator.ofInt(v, "backgroundColor", Color.BLACK, Color.RED, Color.WHITE, Color.parseColor("#00CC00"));
			animatorChangeBack.setEvaluator(new ArgbEvaluator()); //Es de tipo hexadecimal
			animatorChangeBack.setDuration(5000);
			Animator animatorMoveY =  ObjectAnimator.ofFloat(v, "y", v.getTop(), v.getTop()-v.getHeight(), v.getTop());
			animatorMoveY.setInterpolator(new BounceInterpolator());
			animatorMoveY.setDuration(2500);
			
			ValueAnimator textColor = ObjectAnimator.ofInt(v, "textColor", Color.WHITE, Color.BLUE, Color.BLACK, Color.GRAY);
			textColor.setEvaluator(new ArgbEvaluator()); //Es de tipo hexadecimal
			textColor.setDuration(5000);
			
			animatorSet.play(animatorChangeBack).with(animatorFadeOut).with(textColor);
			animatorSet.play(animatorFadeIn).with(animatorMoveY);
			/**
			 *  colorAnim.setRepeatCount(ValueAnimator.INFINITE);
            	colorAnim.setRepeatMode(ValueAnimator.REVERSE);
			 */
//			animatorSet.start();
			Animator animatorSize = ObjectAnimator.ofInt(v, "height", v.getHeight(), 0, v.getHeight());
			
			AnimatorSet animatorSetFinal = new AnimatorSet();
			animatorSetFinal.play(animatorSet).before(animatorSize);

            // Start the animation
			animatorSetFinal.start();
		}
		
	}
	
	//=================================================
	// Test
	//=================================================
}
