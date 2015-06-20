package com.xanxamobile.androidavanzado;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.data.RSSArticle;
import com.xanxamobile.androidavanzado.data.parser.RSSParser;
import com.xanxamobile.androidavanzado.utils.Preferences;
import com.xanxamobile.androidavanzado.utils.ViewUtil;

import static android.widget.Toast.*;

/**
 * Esta sección es para hacerla en casa, ahora mismo no es enficiente ni funcional.
 * @author Manuel Sanchez Palacios.
 *
 */
public class WidgetRSSReaderActivity extends Activity {
    /** Called when the activity is first created. <br><br>
     *	TODO por el alumno: AsyncTask, SearchAdapter
     *	<li>
     *	Parsear en segundo plano (Background) mediante un <b>AsyncTask</b> en vez de un Thread.
     * 	</li>
     *  <li>
     *  Crear un Adapter para el buscador de la interfaz gráfica actual. El "R.id.tvSearchArticle" que busque por titulares.
     *  <li>
     * 	<li>
     *	EXTRA --> Ordenar los artículos por fecha
     * 	</li>
     * <li>
     *	Crear un botón y si le pulsas lea todo el contenido mediante TTS
     * </li>
     **/

	TextToSpeech tts;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Preferences.getInstance().getActualComponentMainView(false));
        makeText(this, "Esta Sección es para hacerla en casa", LENGTH_SHORT).show();
        final AdapterView<MainViewAdapter> view = (AdapterView)findViewById(R.id.mainCompositeView);
        initTTS();
        final Handler handler = new Handler();
        //El parseo ha de hacerse en segundo plano mediante un Thread o mejor aún mediante un AsyncTask
        new Thread(){
        	public void run (){
	        	 RSSParser parser = RSSParser.getInstance();
	             final List<RSSArticle> articles = parser.parse(Preferences.getInstance().getRSSURLs());
	             handler.post(new Runnable() {
					
					public void run() {
						view.setAdapter(new MainViewAdapter(WidgetRSSReaderActivity.this, articles));
					}
				});
	            
        	}
        }.start();
    }

	private void initTTS(){
		tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {

			}
		});
	}



	private void speak(String textToSpeak){
		updateSpeechAndPitch();
		tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);

	}

	private void updateSpeechAndPitch() {
		String rateString = "";
		String pitchString = "";

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

	private void setSpeechRate(float speechRate){
		tts.setSpeechRate(speechRate);
	}

	private void setSpeechPitch(float speechPitch){
		tts.setPitch(speechPitch);
	}

    /**
     * Este es el adapter que genera las distintas Views.
     * 
     * 
     * 
     * TODO por el alumno: Adapter RSS eficiente
     * <li>
     * Reutilizar la convertView si esta está inicializada.
     * </li>
     * <li>
     * EXTRA --> Usando el Broadcast de SD que te dirá si tú SD está o no accesible (Writable), cachear las imágenes en SD cuando sea posible.
     * y no descargarlas siempre (Ya que esto hace que sea muy muy lento).
     * </li>
     * <li>
     * EXTRA --> Además de cachearlas en SD hemos de crear un mapa para que se guarden los bitmaps de la SD para poder acceder a ellos fácilmente directamente de la RAM.
     * Cuidado con imágenes grandes (Hemos de reducir su tamaño).
     * </li>
     * <li>
     * Crear un Wrapper que guarde en una variable temporal cada elemento de la interfaz para no tener que volver a hacer findViewById una vez cacheado
     * </li>
     * <li>
     * Controlar los posibles nulos que puedan venir al rellenar el article.
     * </li>
     * @author Manuel Sánchez Palacios
     *	 
     */
    public class MainViewAdapter extends ArrayAdapter<RSSArticle>
    {

		public MainViewAdapter(Context context, List<RSSArticle> objects) {
			super(context, -1, objects);	
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewGroup v = (ViewGroup) ViewUtil.inflateView(parent.getContext(), Preferences.getInstance().getActualItemMainView(false), parent,false);
			
			final RSSArticle article = getItem(position);
			((TextView)v.findViewById(R.id.tvTitularNoticia)).setText(article.getTitle());
			ViewUtil.setLinks(((TextView) v.findViewById(R.id.tvCuerpo)), article.getDescription(), true);

			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					speak(article.toString());
				}
			});
			
			String imageUrl = article.getImage();
			if (imageUrl != null)
			{
				ImageView iv = ((ImageView)v.findViewById(R.id.ivNoticia));
				setImageBitmap(imageUrl, iv);//setImageURI(Uri.parse(imageUrl));
			}
			return v;
		}
    }
    
    /**
     *  //TODO por el alumno: Thread to AsyncTask
     *  <li>
     *  Transformar el Thread en AsyncTask
     *  </li>
     *  <li>
     *  EXTRA --> Realizar un recycle en el los bitmaps utilizados cuando no se vayan a volver a utilizar para que no se vaya de memoria la aplicación. 
     *  </li>
     * @param url
     * @param iv
     * @return
     */
    private void setImageBitmap(final String url, final ImageView iv) { 
    	final Handler handler = new Handler();
    	new Thread(){
    		public void run()
    		{
    			 
    	        try { 
    	            URL aURL = new URL(url); 
    	            URLConnection conn = aURL.openConnection(); 
    	            conn.connect(); 
    	            InputStream is = conn.getInputStream(); 
    	            BufferedInputStream bis = new BufferedInputStream(is); 
    	            final Bitmap bm = BitmapFactory.decodeStream(bis); 
    	            bis.close(); 
    	            is.close(); 
    	            handler.post(new Runnable() {
						
						public void run() {
							iv.setImageBitmap(bm);
						}
					});
    	       } catch (IOException e) { 
    	           Log.e("WidgetRSSReaderActivity", "Error getting bitmap"); 
    	       } 
    		}
    	}.start();
    } 

}