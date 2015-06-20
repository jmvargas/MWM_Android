package com.xanxamobile.androidavanzado;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * http://developer.android.com/reference/android/speech/tts/TextToSpeech.html
 * Es la clase de Text To Speech. Leerá todo lo que tú le escribas.
 * @author Manuel Sánchez Palacios
 *
 */
public class TTSActivity extends Activity{

	//=====================================
	// Constants
	//=====================================

	//=====================================
	// Fields
	//=====================================
	TextToSpeech tts;
	OnInitListener onInitListener;

	//=====================================
	// Constructors
	//=====================================

	//=====================================
	// Override Methods
	//=====================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tts);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initTTS();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		stopTTS();
	}	
	//=====================================
	// Methods
	//=====================================
	
	/**
	 * Acción que se hace cuando se pulsa el botón de lectura.
	 * @param v
	 */
	public void onClickLeeme (View v){
		speak(getActualText());
	}
	
	private void initTTS(){
		tts = new TextToSpeech(this, new OnInitListenerMine());
	}
	
	

	private void speak(String textToSpeak){
		updateSpeechAndPitch();
		tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
		
	}
	

	
	private void updateSpeechAndPitch() {
		String rateString = ((TextView)findViewById(R.id.editTextSpeech)).getText().toString();
		String pitchString = ((TextView)findViewById(R.id.editTextPitch)).getText().toString();
		
		float rate = 1;
		float pitch = 1;
		if (!rateString.equals(""))
			rate = Float.parseFloat(rateString);
		setSpeechRate(rate);
		if (!pitchString.equals(""))
			pitch = Float.parseFloat(pitchString);
		setSpeechPitch(pitch);
	}

	private void stopTTS(){
		if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
	}
	//=====================================
	// Getters And Setters
	//=====================================
	private String getActualText() {
		TextView editText = (TextView)findViewById(R.id.editTextTextToRead);
		return editText.getText().toString();
	}
	
	private void setSpeechRate(float speechRate){
		tts.setSpeechRate(speechRate);
	}
	
	private void setSpeechPitch(float speechPitch){
		tts.setPitch(speechPitch);
	}
	//=====================================
	// Classes
	//=====================================
	private class OnInitListenerMine implements OnInitListener{

		public void onInit(int status) {
			
			//Vemos si hemos tenido exito en la decodificación
			if (status == TextToSpeech.SUCCESS) {
				 int result = tts.setLanguage(Locale.getDefault());
				 if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
			               // Lanuage data is missing or the language is not supported.
			                Toast.makeText(TTSActivity.this, "No está soportado el "+Locale.getDefault(), Toast.LENGTH_SHORT).show();
			            } else {
			                speak("!Hola! ¡Ahora puedes hacer que lea!");//getActualText());
			            }
			}
		}

		
		
	}
}
