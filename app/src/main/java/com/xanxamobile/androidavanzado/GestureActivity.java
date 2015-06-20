package com.xanxamobile.androidavanzado;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author Manuel Sánchez Palacios
 *
 */
public class GestureActivity extends Activity {

	/**
	 * Es la View donde el usario pinta
	 */
	 GestureOverlayView gesture;
	 /**
	  * Es el listener que recivirá el pintado
	  */
     OnGesturePerformedListener onGesturePerformedListener;
     /**
      * Es la librería que contiene la información de los gestos precargados.
      */
     private GestureLibrary gestureLibrary;     
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
 	@Override
 	protected void onCreate(Bundle savedInstanceState) {

 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.gesture);
 		initGestureStore(this);
 		gesture = (GestureOverlayView)findViewById(R.id.gestureView);
 		onGesturePerformedListener = new OnGesturePerformedListenerMine();
 		gesture.addOnGesturePerformedListener(onGesturePerformedListener);
 		
 	}
     
	//=====================================
	// Methods
	//=====================================
     /**
 	 * 
 	 * @param context
 	 * @return Un gestureLibrary con todos los gestos cargados. De memoria
 	 *         Interna o de SD dependiendo de si el usuario lo ha inicializado.
 	 */
 	private void initGestureStore(Context context) {
 		if (gestureLibrary == null){
 			gestureLibrary = GestureLibraries.fromRawResource(context, R.raw.gestures);// fromFile(mStoreFile);
 			
 			if (!gestureLibrary.load())
 				Toast.makeText(context, "El GestureLibrary no se ha podido inicializar", Toast.LENGTH_SHORT);
 			else
 				Toast.makeText(context, "gestureLibrary init "+gestureLibrary.getGestureEntries().size(), Toast.LENGTH_SHORT).show();
 			
 		}
 	}
	//=====================================
	// Getters And Setters
	//=====================================


	//=====================================
	// Classes
	//=====================================
     public class OnGesturePerformedListenerMine implements OnGesturePerformedListener{

 		private String tag = "OnGesturePerformedListenerMine";
 		private long interval;
 		
 		public void onGesturePerformed(GestureOverlayView overlay,Gesture gesture) {
 			List<Prediction> predictions = gestureLibrary.recognize(gesture);
 			
 			Log.d(tag , "predictions --> "+predictions.size());
 			
 			if (!predictions.isEmpty())
 			{
 				Prediction prediction = predictions.get(0);
 				Log.d(tag , "predictions is empty "+prediction.score);
 				if (prediction.score > 1.0){
 					 // Mostramos el texto
 		            		Toast.makeText(overlay.getContext(), "Esto es un:"+prediction.name, Toast.LENGTH_SHORT).show();
 				}else{
 					Toast.makeText(overlay.getContext(), "No hay una predicción exacta.", Toast.LENGTH_SHORT).show();
 				}
 			}else{
 				Toast.makeText(overlay.getContext(), "No hay ningún gesto. "+gestureLibrary.getGestureEntries().size(), Toast.LENGTH_SHORT).show();
 			}
 		}
 	
 	}
}
