package com.xanxamobile.androidavanzado;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.PriorityQueue;

/**
 * Esto es una interfaz multiresolución.
 * @author XanXa
 *
 */
public class CalculatorActivity extends Activity {

	PriorityQueue < Double >  pollResults;
	Double  memo;

	//=================================================
	// Constants
	//=================================================
	
	//=================================================
	// Fields
	//=================================================
	EditText editText;
	//=================================================
	// Constructors
	//=================================================
	
	//=================================================
	// override function
	//=================================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interfaz_multiresolucion);

		pollResults = new PriorityQueue < Double > ();
		memo = 0.0;
		//Funcionalidad botón +
		View buttonPlus = findViewById(R.id.buttonPlus);
		buttonPlus.setOnClickListener(new OnClickListenerPlus());
		View buttonC = findViewById(R.id.buttonC);
		buttonC.setOnClickListener(new OnClickListenerC());
		View buttonMM = findViewById(R.id.buttonMM);
		buttonMM.setOnClickListener(new OnClickListenerMM());
		View buttonMP = findViewById(R.id.buttonMP);
		buttonMP.setOnClickListener(new OnClickListenerMP());
		View buttonMul = findViewById(R.id.buttonMul);
		buttonMul.setOnClickListener(new OnClickListenerMul());
		View buttonDiv = findViewById(R.id.ButtonDiv);
		buttonDiv.setOnClickListener(new OnClickListenerDiv());
		View buttonMinus = findViewById(R.id.ButtonMinus);
		buttonMinus.setOnClickListener(new OnClickListenerMinus());
		View buttonEq = findViewById(R.id.ButtonEqual);
		buttonEq.setOnClickListener(new OnClickListenerEq());
		View buttonSq = findViewById(R.id.imageButSq);
		buttonSq.setOnClickListener(new OnClickListenerSq());
		View buttonPot = findViewById(R.id.imageButPot);
		buttonPot.setOnClickListener(new OnClickListenerPot());
		View buttonPoint = findViewById(R.id.ButtonPoint);
		buttonPoint.setOnClickListener(new OnClickListenerPoint());
		View buttonBack = findViewById(R.id.imageButtonBack);
		buttonBack.setOnClickListener(new OnClickListenerBack());

	}
	//=================================================
	// Functions
	//=================================================
	/**
	 * Es la fución que se llamará desde el xml para todos los números, insertará un número en el edit text de arriba
	 * Al ser una fución proveniente de un XML hemos ponerle una View como parámetro y ponerla como pública.
	 * @param v
	 */
	public void insertNumber (View v){
		String numberString = ((Button)v).getText().toString();
//		int number = Integer.parseInt(numberString);
		addText(numberString);
	}
	
	/**
	 * Concatenamos una string a lo que ya haya en el editText
	 * @param stringToConcatenate
	 */
	private void addText(String stringToConcatenate) {
		EditText editTextCalculate = getEditText();
		editTextCalculate.setText(editTextCalculate.getText().toString()+stringToConcatenate);
	}
	
	
	public class OnClickListenerPlus implements OnClickListener{

		public void onClick(View v) {
			getResult();
			addText("+");
		}
	}

	public class OnClickListenerMinus implements OnClickListener{

		public void onClick(View v) {
			getResult();
			addText("−");
		}
	}

	public class OnClickListenerMul implements OnClickListener{

		public void onClick(View v) {
			getResult();
			addText("X");
		}
	}

	public class OnClickListenerDiv implements OnClickListener{

		public void onClick(View v) {
			getResult();
			addText("/");
		}
	}

	public class OnClickListenerEq implements OnClickListener{

		public void onClick(View v) {
			getResult();
		}
	}

	public class OnClickListenerC implements OnClickListener{

		public void onClick(View v) {
			EditText editText = getEditText();
			editText.setText("");
		}
	}

	public class OnClickListenerMM implements OnClickListener{

		public void onClick(View v) {
			EditText editText = getEditText();
				editText.setText(editText.getText().toString()+memo.toString());
		}
	}

	public class OnClickListenerMP implements OnClickListener{

		public void onClick(View v) {
			getResult();
			EditText editText = getEditText();
			try {
				memo = Double.valueOf(editText.getText().toString());
			}catch (Exception e){

			}
		}
	}
	public class OnClickListenerSq implements OnClickListener{

		public void onClick(View v) {
			getResult();
			EditText editText = getEditText();
			Double sq=Math.sqrt(Double.valueOf(editText.getText().toString()));
			editText.setText(sq.toString());
		}
	}
	public class OnClickListenerPot implements OnClickListener{

		public void onClick(View v) {
			getResult();
			addText("^");
		}
	}
	public class OnClickListenerPoint implements OnClickListener{

		public void onClick(View v) {
			addText(".");
		}
	}
	public class OnClickListenerBack implements OnClickListener{

		public void onClick(View v) {
			if(!pollResults.isEmpty()){
				EditText editText = getEditText();
				editText.setText(pollResults.poll().toString());
			}

		}
	}

	//=================================================
	// Getters and Setters
	//=================================================
	/**
	 * Cacheamos el edit text de calculos para no tener que buscarlo varias veces con findViewById ya que esto es muy lento
	 * @return
	 */
	public EditText getEditText() {
		if (editText == null){
			editText = (EditText)findViewById(R.id.editTextCalculate);
		}
		return editText;
	}

	public boolean getResult(){
		EditText editText = getEditText();
		String text = editText.getText().toString();
		boolean contains=false;
		if(text.contains("+")){
			String splitted[] = text.split("\\+");
			if(splitted.length == 2) {
				Double first = Double.valueOf(splitted[0]);
				Double second = Double.valueOf(splitted[1]);
				Double result = first + second;
				editText.setText(String.valueOf(result));
			}else if(splitted.length==1){
				editText.setText(String.valueOf(splitted[0]));
			}
			contains=true;
		}else if(text.contains("−")){
			String splitted[] = text.split("−");
			if(splitted.length == 2) {
				Double first = Double.valueOf(splitted[0]);
				Double second = Double.valueOf(splitted[1]);
				Double result = first - second;
				editText.setText(String.valueOf(result));
			}else if(splitted.length==1){
				editText.setText(String.valueOf(splitted[0]));
			}
			contains=true;
		}else if(text.contains("X")){
			String splitted[] = text.split("X");
			if(splitted.length == 2) {
				Double first = Double.valueOf(splitted[0]);
				Double second = Double.valueOf(splitted[1]);
				Double result = first * second;
				editText.setText(String.valueOf(result));
			}else if(splitted.length==1){
				editText.setText(String.valueOf(splitted[0]));
			}
			contains=true;
		}else if(text.contains("/")){
			String splitted[] = text.split("/");
			if(splitted.length == 2) {
				Double first = Double.valueOf(splitted[0]);
				Double second = Double.valueOf(splitted[1]);
				Double result = first / second;
				editText.setText(String.valueOf(result));
			}else if(splitted.length==1){
				editText.setText(String.valueOf(splitted[0]));
			}
			contains=true;
		}else if(text.contains("^")){
			String splitted[] = text.split("\\^");
			if(splitted.length == 2) {
				Double first = Double.valueOf(splitted[0]);
				Double second = Double.valueOf(splitted[1]);
				Double result = Math.pow(first,second);
				editText.setText(String.valueOf(result));
			}else if(splitted.length==1){
				editText.setText(String.valueOf(splitted[0]));
			}
			contains=true;
		}
		if(contains){
			pollResults.add(Double.valueOf(editText.getText().toString()));
		}
		return contains;
	}
	//=================================================
	// Test
	//=================================================
}
