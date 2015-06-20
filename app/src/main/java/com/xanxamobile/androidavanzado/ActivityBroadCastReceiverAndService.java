package com.xanxamobile.androidavanzado;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.animation.BehindPagerTransformer;
import com.xanxamobile.androidavanzado.animation.HiperspacePagerTransformer;
import com.xanxamobile.androidavanzado.animation.ZoomPagerTransformer;
import com.xanxamobile.androidavanzado.services.DownloadService;
import com.xanxamobile.androidavanzado.services.DownloadService.LocalBinder;
import com.xanxamobile.androidavanzado.services.DownloadService.OnDownloadImageListener;

/**
 * <pre>
 * En este ejemplo veremos cómo podemos hacer descargas en segundo plano mediante un service. 
 * Este service comunicará con esta Activity mediante un BroadcastReceiver alojado en la activity
 * Podemos comunicarnos con la activity de muchos modos pero este es uno de los más correctos.
 * </pre>
 * @author Manuel Sánchez Palacios
 *
 */
public class ActivityBroadCastReceiverAndService extends FragmentActivity  implements OnDownloadImageListener{
	//=================================================
	// Constants
	//=================================================
	private final ServiceDownloadConnection mServiceDownloadConnection = new ServiceDownloadConnection();
	//=================================================
	// Fields
	//=================================================
	DownloadService downloadService;
	boolean connectedToService = false;
	ScreenSlidePagerAdapterImageDownload pagerAdapter;
	BroadcastReceiverDownloaded broadcastReceiverDownloaded;
	Button bindServiceButton;
	//=================================================
	// Constructors
	//=================================================
	
	//=================================================
	// Override function
	//=================================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.communication_activity_service_with_broadcastreceiver);
		//Registramos el receiver
		registerDownloadReceiver();
		//Inicializamos el listener del button download (Se podría hacer en una clase externa)
		findViewById(R.id.ButtonDownload).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Recuperamos el contexto del botón que es la ActivityBroadCastReceiverAndService, haciendo un ActivityBroadCastReceiverAndService.this también valdría
				Context context = v.getContext();
				//Creamos el Intent para iniciar la descarga
				Intent intent = new Intent(context, DownloadService.class);
				intent.setAction(DownloadService.ACTION_DOWNLOAD_IMAGE);
				//Añadimos al Intent la información sobre la imagen
				intent.putExtra(DownloadService.EXTRA_URL_NAME, getEditText().getText().toString());
				context.startService(intent);
			}
		});
		
		//Este método hace binding del servicio para conectarnos a él y poder comunicarnos sin necesidad de broadcast
		bindServiceButton = (Button)findViewById(R.id.ButtonBindService);
		bindServiceButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActivityBroadCastReceiverAndService.this, DownloadService.class);
		        bindService(intent, mServiceDownloadConnection, Context.BIND_AUTO_CREATE);
			}
		});
		
		initViewPager ();
		
		findViewById(R.id.buttonAnimationChange).setOnClickListener(new OnClickListener() {
			private final int MAX_ANIMATIONS = 3;
			int actualAnimation = 0;
			public void onClick(View v) {
				ViewPager viewPager = (ViewPager)findViewById(R.id.viewPagerDownload);
				switch (actualAnimation) {
				case 0:
					((TextView)v).setText(R.string.animation_zoom);
					viewPager.setPageTransformer(true, new ZoomPagerTransformer());
					break;
				case 1:
					((TextView)v).setText(R.string.animation_behind);
					viewPager.setPageTransformer(true, new BehindPagerTransformer());
					break;
				case 2:
					((TextView)v).setText(R.string.animation_hiperspace);
					viewPager.setPageTransformer(true, new HiperspacePagerTransformer());
					break;
				default:
					break;
				}
				//Tenemos 3 animaciones nada más
				actualAnimation = (actualAnimation+1)%MAX_ANIMATIONS;
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		   if (connectedToService) {
	            unbindService(mServiceDownloadConnection);
	            connectedToService = false;
	        }
	}

	protected void onStop(){
		super.onStop();
		if (broadcastReceiverDownloaded != null){
			//No se nos debe de olvidar desresgistrar el receiver para que no se quede escuchando si no lo estamos usando
			getApplicationContext().unregisterReceiver(broadcastReceiverDownloaded);
			Toast.makeText(this, "broadcastReceiverDownloaded desregistrado", Toast.LENGTH_SHORT).show();
			Log.d("tag", "unRegisterDownloadReceiver "+DownloadService.ACTION_IMAGE_DOWNLOADED);
		}
		if (pagerAdapter != null){
			//Reciclamos los bitmaps (Esto se podría hacer en cada detach de los fragments pero no está hecho así por simplificar).
			pagerAdapter.recycleBitmap();
		}
	}

	public void onImageDownloaded(String url, String path) {
		if (path != null){
			Toast.makeText(this, "Imagen descargada "+path, Toast.LENGTH_SHORT).show();
			//Tened cuidado, si el bitmap es muy grande puede dar outOfMemoryException, hay formas de evitarlo mediante "Options" pero no quiero complicar el ejemplo de comunicación. 
			Options options = new Options();
			options.inSampleSize = 4; //Habría que calcularlo pero de momento nos vale
			Bitmap bitmap = BitmapFactory.decodeFile(path, options);
			Log.d("tag", "bitmap decoded "+bitmap.getWidth()+", "+bitmap.getHeight());
			insertNewImage(bitmap);
		}else {
			Toast.makeText(this, "Error downloading "+url, Toast.LENGTH_SHORT).show();
			//Aquí podríamos intentar volver a descargar la imagen o tratar el error.
		}
	}
	
	//=================================================
	// Functions
	//=================================================
	/**
	 * Registramos el Receiver para escuchar a la llegada de la imagen
	 */
	private void registerDownloadReceiver() {
	    IntentFilter filter = new IntentFilter(DownloadService.ACTION_IMAGE_DOWNLOADED);
	    broadcastReceiverDownloaded = new BroadcastReceiverDownloaded();
        getApplicationContext().registerReceiver(broadcastReceiverDownloaded, filter);
        Log.d("tag", "registerDownloadReceiver "+DownloadService.ACTION_IMAGE_DOWNLOADED);
	}
	
	private void insertNewImage(Bitmap bitmap) {
		pagerAdapter.addImage(bitmap);
	}

	/**
	 * Aquí inicializaremos el viewPager, no es parte de la comunicación pero es interesante para mostrar las imágenes
	 * Para pasar de página haced un gesto horizontal
	 */
	private void initViewPager() {
		ViewPager viewPager = (ViewPager)findViewById(R.id.viewPagerDownload);
		pagerAdapter = new ScreenSlidePagerAdapterImageDownload(getSupportFragmentManager());
		
		viewPager.setAdapter(pagerAdapter);
	}
	//=================================================
	// Getters and Setters
	//=================================================
	private EditText getEditText() {
		return (EditText)findViewById(R.id.editTextImageDownload);
	}
	//=================================================
	//Classes
	//=================================================
	/**
	 * Es la clase que se va a encargar de informar cuando estamos o no conectados a un servicio
	 * */
    public class ServiceDownloadConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            downloadService = binder.getService();
            binder.setOnDownloadImageListener(ActivityBroadCastReceiverAndService.this);
            connectedToService = true;
            Toast.makeText(ActivityBroadCastReceiverAndService.this, "Service Download Conectado (Binding)", Toast.LENGTH_SHORT).show();
            bindServiceButton.setText(R.string.service_binded);
        }

        public void onServiceDisconnected(ComponentName arg0) {
        	connectedToService = false;
        	Toast.makeText(ActivityBroadCastReceiverAndService.this, "Service Download Desconectado (UnBinding)", Toast.LENGTH_SHORT).show();
        	bindServiceButton.setText(R.string.bind_service);
        }
    }
	
    /**
     * Usaremos el Broadcast siempre y cuando no estemos conectados al servicio
     * @author Manuel Sánchez Palacios
     *
     */
    public class BroadcastReceiverDownloaded extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String url = intent.getStringExtra(DownloadService.EXTRA_URL_NAME);
			String path = intent.getStringExtra(DownloadService.EXTRA_PATH_DOWNLOADED);
			Toast.makeText(ActivityBroadCastReceiverAndService.this, "BroadcastReceiverDownloaded "+path, Toast.LENGTH_SHORT).show();
			Log.d("tag", "BroadcastReceiverDownloaded "+path);
			onImageDownloaded(url, path);	
		}
    }

    ///////////////////ESTA SERÍA LA PARTE GRÁFICA. POR SUPUESTO CADA CLASE PODRÍA IR EN ARCHIVOS DIFERENTES, PERO EN ESTE EJEMPLO SALVO EL SERVICE LO DEMÁS ESTÁ EN ESTA CLASE PARA QUE NO HAYA QUE BUSCAR POR LOS PAQUETES
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapterImageDownload extends FragmentStatePagerAdapter {
    	List<FragmentImageDownload> fragmentsList = new ArrayList<ActivityBroadCastReceiverAndService.FragmentImageDownload>();
        public ScreenSlidePagerAdapterImageDownload(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }
        
        public void addImage (Bitmap bitmap){
        	FragmentImageDownload fragmentDownload = new FragmentImageDownload();
        	fragmentDownload.setBitmap(bitmap);
        	fragmentsList.add(fragmentDownload);
        	
        	//Notificamos que los datos han cambiado
        	this.notifyDataSetChanged();
        }
        
        public void recycleBitmap (){
        	for (FragmentImageDownload fragment : fragmentsList) {
        		fragment.recycleBitmap();
			}
        }
    }
    
    public static class FragmentImageDownload extends Fragment {
    	ImageView imageView;
    	Bitmap bitmap;
    	public FragmentImageDownload(){};
    	
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
           //Lo normal es inflar de un XML pero vamos a hacerlo por código para hacerlo diferente
    		imageView = new ImageView(container.getContext());
    		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    		imageView.setImageBitmap(bitmap);
            return imageView;
        }	
    	
		public void setBitmap (Bitmap bitmap){
    		this.bitmap = bitmap;
    	}
		
		public void recycleBitmap (){
			Log.d("tag", "recycleBitmap "+bitmap);
			if (bitmap != null && !bitmap.isRecycled()){
				bitmap.recycle();
			}
		}
    }
	//=================================================
	// Test
	//=================================================
}
