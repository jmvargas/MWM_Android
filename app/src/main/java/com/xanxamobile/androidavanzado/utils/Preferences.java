package com.xanxamobile.androidavanzado.utils;

import java.util.Arrays;
import java.util.List;

import com.xanxamobile.androidavanzado.R;

/**
 * Son las preferencias que ha elegido el usuario.
 * 
 * @author Manuel Sánchez Palacios
 * 
 */
public class Preferences {
	// ======================================
	// Constantes
	// ======================================
	public static final int COMPOSITE_LISTVIEW = 0, COMPOSITE_GRIDVIEW = 1,
			COMPOSITE_GALLERY = 2, COMPOSITE_STACKVIEW = 3;
	/**
	 * Separador por defecto entre varios feeds. El salvado de feeds debería de
	 * hacerse en una base de datos pero en este ejemplo nos centraremos en el
	 * widget y el parseo.
	 */
	public static final String defaultSeparator = "<--_,_-->";

	// =============================================
	// Variables
	// =============================================
	// ============================================
	// Singleton
	// ============================================
	public static Preferences preferences;

	public static Preferences getInstance() {
		if (preferences == null) {
			preferences = new Preferences();
		}
		return preferences;
	}

	/**
	 * 
	 * @param isWidget
	 * @return El xml que eligió el usuario para ser mostrado
	 */
	public int getActualComponentMainView(boolean isWidget) {
		int actualView = getPreferencesActualComponetMainView();
		int resultView = -1;
		switch (actualView) {
		case COMPOSITE_LISTVIEW:
			resultView = R.layout.main_composite_list_view;
			break;
		case COMPOSITE_GRIDVIEW:
			resultView = R.layout.main_composite_grid_view;
			break;
		case COMPOSITE_GALLERY:
			resultView = R.layout.main_composite_gallery;
			break;
		case COMPOSITE_STACKVIEW:
			resultView = R.layout.main_composite_stack;
			break;
		default:
			break;
		}
		// Un widget no puede tener un grid view
		if (isWidget && (actualView == COMPOSITE_GRIDVIEW))
			resultView = R.layout.main_composite_list_view;
		return resultView;
	}

	/**
	 * 
	 * @param isWidget
	 * @return El entero del xml que eligió el usuario para ser mostrado
	 */
	public int getActualItemMainView(boolean isWidget) {
		int actualView = getPreferencesActualComponetMainView();
		int resultView = -1;
		switch (actualView) {
		case COMPOSITE_LISTVIEW:
			resultView = R.layout.main_item_list_view;
			break;
		case COMPOSITE_GRIDVIEW:
			resultView = R.layout.main_item_grid_view;
			break;
		case COMPOSITE_GALLERY:
			resultView = R.layout.main_item_gallery;
			break;
		case COMPOSITE_STACKVIEW:
			resultView = R.layout.main_item_gallery;
			break;
		default:
			break;
		}
		// Un widget no puede tener un grid view
		if (resultView == R.layout.main_composite_grid_view && isWidget)
			resultView = R.layout.main_composite_list_view;
		return resultView;
	}

	// =======================================================
	// Preferences getters
	// =======================================================
	/**
	 * //TODO por el alumno: Preferences <li>
	 * Extra --> La preference actual se obtendrá de preferences. (Ha de
	 * introducirse en la preference Activity que creeis para vuestro proyecto).
	 * </li>
	 * 
	 * @return El entero que representa el xml/item actual a cargar.
	 */
	private int getPreferencesActualComponetMainView() {
		return COMPOSITE_LISTVIEW;

	}

	/**
	 * //TODO por el alumno: PreferenceActivity <li>
	 * EXTRA --> Hacer que se pueda configurar el path por defecto y si este es
	 * visible o no por el browser de fotos. (En la PreferenceActivity que has
	 * de crear)</li> <li>
	 * EXTRA --> Hacer que si no hay SD se descargue los archivos temporales en
	 * el disco interno.</li> Devuelve la dirección dónde se encuentran los
	 * archivos temporales dentro de la SD
	 */
	public String getDefaultPath() {
		return ".rssReader";
	}

	/**
	 * Este método deberá parsear una preference referente a las urls que el
	 * usuario tiene actualmente elegidas en preferencias y transformarlas en
	 * una lista de Strings TODO por el alumno: PreferenceActivity <li>
	 * Extra --> Ahora mismo los feedsRSS no son dinámicos. Crear una
	 * PreferenceActivity para que el usuario pueda seleccionar o deseeleccionar
	 * los feeds que se le ofrezcan.</li>
	 * 
	 * @return Una lista con las URLs de los feeds actuales.
	 */
	public List<String> getRSSURLs() {
		// hacerlo mediante preferences guardamos en preferences la lista de
		// feedsUrls separadas por el defaultSeparator.
		String preferences = 
				"http://www.informatica.us.es/index.php/es/noticias-etsii?format=feed&type=rss"
				+ defaultSeparator+
				"http://www.elpais.com/rss/feed.html?feedId=1022"
				+ defaultSeparator
				+ "http://ep00.epimg.net/rss/tags/noticias_mas_vistas.xml"
				+ defaultSeparator
				+ "http://ep00.epimg.net/rss/tags/ultimas_noticias.xml"
				+ defaultSeparator
				+ "http://ep00.epimg.net/rss/elpais/portada.xml"
				;
		String preferencesSplitted[] = preferences.split(defaultSeparator);
		return Arrays.asList(preferencesSplitted);

	}

}
