package com.xanxamobile.androidavanzado.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author Manuel Sánchez Palacios
 *
 */
public class DownloadService extends IntentService {
	
	
	//=================================================
	// Constants
	//=================================================
	public final static String ACTION_DOWNLOAD_IMAGE = "com.xanxamobile.androidavanzado.action_download_image";
	public final static String ACTION_IMAGE_DOWNLOADED = "com.xanxamobile.androidavanzado.action_image_donwloaded";
	
	
	public final static String EXTRA_URL_NAME = "com.xanxamobile.androidavanzado.extra_url_image";
	public final static String EXTRA_PATH_DOWNLOADED = "com.xanxamobile.androidavanzado.extra_path_downloaded_image";
	
	// Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    /**
     * Lo usamos para poder llamar a onDownloadImageListener en el hilo principal, que es donde se han de llamar todos los listeners
     * Controlamos si estamos conectados a la Activity (binding) mediante el handler. Si es null no estamos conectados
     */
    private Handler handlerDownloadedBinding;
    private Handler handlerTmp;
	//=================================================
	// Fields
	//=================================================
    OnDownloadImageListener onDownloadImageListener;
	//=================================================
	// Constructors
	//=================================================
    public DownloadService() {
		super("Download Service");
	}

	//=================================================
	// override function
	//=================================================
	@Override
	public IBinder onBind(Intent intent) {
		//Creamos el handler para poder llamar al onDownloadImageListener
		handlerDownloadedBinding = new Handler();
		//Devolvemos el binder que hará que podamos comunicarnos con la activity.
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		handlerDownloadedBinding = null;
		return super.onUnbind(intent);
	}

	
	
	@Override
	public void onStart(Intent intent, int startId) {
		handlerTmp = new Handler();
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		handlerTmp = new Handler();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//No se llama en el main thread por lo que se pueden hacer conexiones
		final String imageUrl = intent.getStringExtra(EXTRA_URL_NAME);
		final String filePath = downloadImage(imageUrl);
		Log.d("tag", "onHandleIntent start "+imageUrl);
		if (handlerDownloadedBinding == null || onDownloadImageListener == null){
			//Informamos al broadcast que está en la activity de que se ha descargado la imagen y de cual es su path (puede ser null si ha habido algún problema)
			Intent intentToSend = new Intent(ACTION_IMAGE_DOWNLOADED);
			intentToSend.putExtra(EXTRA_URL_NAME, imageUrl);
			intentToSend.putExtra(EXTRA_PATH_DOWNLOADED, filePath);
			this.sendBroadcast(intentToSend);
			Log.d("tag", "sendBroadcast "+filePath);
		}else {
			//Si estamos conectados a la Activity entonces informamos mediante el listener en vez de mediante un broadcast
			handlerDownloadedBinding.post(new Runnable() {
				public void run() {
					onDownloadImageListener.onImageDownloaded(imageUrl, filePath);
					Log.d("tag", "onDownloadImageListener.onImageDownloaded "+filePath);
				}
			});
		}
		Log.d("tag", "onHandleIntent finished "+filePath);
	}
	private String downloadImage(String imageUrl) {
		String fileCompletePath = null;
		File fileLocal = null;
		if (imageUrl != null){
			String split[] = imageUrl.split(File.separator);
			if (split.length != 0){
				String archiveName = split[split.length-1];
				fileLocal = instanciatePathFile(this, archiveName);
				if (fileLocal != null){
					try {
						fileLocal = downloadImage (imageUrl, fileLocal);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if (fileLocal != null){
			fileCompletePath = fileLocal.getAbsolutePath();
		}
		return fileCompletePath;
	}

	/**
	 * 
	 * @param imageUrl
	 * @param fileLocal
	 * @return el fichero descargado
	 * @throws IOException 
	 */
	private File downloadImage(String imageUrl, File fileLocal) throws IOException {
		Log.d("tag", "downloadImage "+imageUrl);
		if (thereIsConnection(this)) {
			if (fileLocal.exists() && fileLocal.length() > 200) {
				//Como existe no tenemos que hacer nada
			} else {
				removeFile(fileLocal);//Por si existe lo borramos
				if (createFileLocal(fileLocal)) {
					//Nos lo descargamos
					try {
						URL URL = new URL(imageUrl);
						HttpURLConnection con = (HttpURLConnection) URL
								.openConnection();
						con.setConnectTimeout(4500);
						InputStream iStream = con.getInputStream();
						fileLocal = saveFile(fileLocal, iStream);
					} catch (IOException e) {
						removeFile(fileLocal);
						fileLocal = null;
						throw e;
					}
				}
			}
		}else {
			fileLocal = null;
			Log.e("tag", "ERROR THERE IS NO CONNECTION "+imageUrl);
		}
		
		return fileLocal;
	}
	
	private static File saveFile(File fileLocal, InputStream iStream)
			throws IOException {
		FileOutputStream fileOutputStreamFile = new FileOutputStream(fileLocal);
		byte buffer[] = new byte[1024];
		FileOutputStream fileOutputStream = fileOutputStreamFile;
		int readBytes = iStream.read(buffer);
		
		if (readBytes == 0) {
			removeFile(fileLocal);
			throw (new IOException("No bits en url"));
		} else {
			while (readBytes > 0) {
				Log.d("tag", "bytes leidos "+readBytes);
				fileOutputStream.write(buffer, 0, readBytes);
				readBytes = iStream.read(buffer);
			}
			fileOutputStream.close();
			iStream.close();
			return fileLocal;
		}
	}
	
	public static boolean removeFile(File fileToRemove) {
		boolean isRemoved = false;
		if (fileToRemove.exists()){
			isRemoved = fileToRemove.delete();
		}
		return isRemoved;
	}
	
	private static boolean createFileLocal(File fileLocal) {
		boolean createFileLocal = true;
		try {
			if (!fileLocal.createNewFile()){
				Log.w("tag", "ERROR AL CREAR EL FICHERO "+fileLocal.getName());
				createFileLocal = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			createFileLocal = false;
		}
		return createFileLocal;
	}

	private static File instanciatePathFile(Context context, String pathLocal) {
		File fileCacheParent = context.getCacheDir();
		File fileLocal = new File(fileCacheParent.getAbsolutePath()+addBars(pathLocal));
		return fileLocal;
	}
	
	//=================================================
	// Functions
	//=================================================
	public static String getInternalStorageDirectory(Context context, String pathName) {
		String result = context.getFilesDir().getAbsolutePath() + addBars(pathName);
		File file =  new File(result);
		if (!file.exists()){
			file.mkdirs();
		}
		return result;
	}
	private static String addBars(String pathName){
		
		if(!pathName.startsWith(File.separator)){
			pathName = File.separator + pathName;
		}
		
		if(!pathName.endsWith(File.separator)){
			pathName += File.separator;
		}
		
		return pathName;
	}
	
	public static boolean thereIsConnection(Context context) {
		boolean result = true;
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context.getSystemService(
				Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			result = false;
		}
		return result;
	}
	//=================================================
	// Getters and Setters
	//=================================================

	//=================================================
	//Classes
	//=================================================
	/**
	 * Esta es la clase para el client Binder, hemos de tener claro que los clientes van a estar en el mismo
	 * proceso que el service para no tener que realizar la conexión mediante IPC (Android Remote Service)
	 * @author Manuel Sánchez Palacios
	 *
	 */
	public class LocalBinder extends Binder {
		/**
		 * @return El servicio actual para que el cliente pueda acceder a sus funciones.
		 */
        public DownloadService getService() {
            return DownloadService.this;
        }
        
        public void setOnDownloadImageListener (OnDownloadImageListener onDownloadImageListener){
        	DownloadService.this.onDownloadImageListener = onDownloadImageListener;
        }
    }
	
	/**
	 * Es un listener que te indica cuando se ha o no descargado una imagen
	 * Lo usaremos cuando hagamos binding
	 * @author Manuel Sánchez Palacios
	 *
	 */
	public interface OnDownloadImageListener {
		public void onImageDownloaded (String url, String path);
	}
	//=================================================
	// Test
	//=================================================

	
}
