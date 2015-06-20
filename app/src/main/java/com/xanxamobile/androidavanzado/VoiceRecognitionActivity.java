package com.xanxamobile.androidavanzado;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceRecognitionActivity extends Activity {

	// =====================================
	// Constants
	// =====================================
	/**
	 * Es el id que recogemos en el OnResult
	 */
	public final static int VOICE_RECOGNITION_REQUEST_CODE = 69;

	// =====================================
	// Fields
	// =====================================

	// =====================================
	// Constructors
	// =====================================

	// =====================================
	// Override Methods
	// =====================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_recognition);
	}

	// 5. Una vez iniciada recogemos el resultado en el onActivityResult de la
	// Activity
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
			//Array list de las posibles coincidencias.
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			
			//Cogemos la primera coincidencia que es la que más se acercará al reconocimiento.
			if (matches != null && !matches.isEmpty()){
				setHeardText(matches.get(0));
				setHeardText(matches);
			}else 
				Toast.makeText(this, "matches "+matches, Toast.LENGTH_SHORT).show();
		}else{
			if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
				Toast.makeText(this, "Error reconociendo la voz "+resultCode, Toast.LENGTH_SHORT).show();
		}
	}

	
	

	// =====================================
	// Methods
	// =====================================
	/**
	 * Al pulsar el botón de voice recognition:
	 * @param v
	 */
	public void onClickVoiceRecognition(View v){
		initVoiceRecognition();
	}
	
	/**
	 * Llamamos al un servicio de reconocimiento de voz.
	 */
	private void initVoiceRecognition() {
		// 2. Tras ver que hay al menos una activity, volvemos a usar el intent.
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		// 3. Ponemos el idioma (por defecto por ejemplo).
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		// 4. Finalmente iniciamos la Activity de recognition
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	// =====================================
	// Getters And Setters
	// =====================================
	private void setHeardText(String textToAdd) {
		TextView editText = (TextView) findViewById(R.id.editTextTextHeard);
		editText.setText(textToAdd);
	}
	
	private void setHeardText(ArrayList<String> matches) {
		TextView editText = (TextView) findViewById(R.id.editTextTextHeardMatches);
		editText.setText("");
		int i = 1;
		for (String string : matches) {
			editText.setText(editText.getText() +""+i+".- "+string+"\n");
			i++;
		}
		
	}
	// =====================================
	// Classes
	// =====================================
}
