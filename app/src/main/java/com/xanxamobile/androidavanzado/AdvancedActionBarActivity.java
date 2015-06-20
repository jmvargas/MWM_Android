package com.xanxamobile.androidavanzado;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.xanxamobile.androidavanzado.fragments.AdvancedActionBarFragmentAddAction;
import com.xanxamobile.androidavanzado.fragments.ContentFragment;
import com.xanxamobile.androidavanzado.fragments.ContentFragmentV11;
import com.xanxamobile.androidavanzado.utils.Painter;

@SuppressLint("NewApi")
public class AdvancedActionBarActivity extends FragmentActivity {

	//=================================================
	// Constants
	//=================================================
	public final static String ADD_ACTION = "add action";
	//=================================================
	// Fields
	//=================================================

	//=================================================
	// Constructors
	//=================================================
	
	//=================================================
	// override function
	//=================================================
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.advanced_action_bar);
		findViewById(R.id.buttonAddActionToMenu).setOnClickListener(new OnClickListenerAddFragment());
		
		findViewById(R.id.ButtonAddTabs).setOnClickListener(new OnClickListenerAddTabs());
		
		findViewById(R.id.ButtonCreateDropDown).setOnClickListener(new OnClickListenerCreateDropDown());
		
		findViewById(R.id.ButtonCustomizeActionBar).setOnClickListener(new OnClicklistenerCustomizeActionBar());
		
		findViewById(R.id.buttonContextMenu).setOnClickListener(new OnClickListenerShowPopUp());
		//Ahora con un long click podemos acceder al menú
		registerForContextMenu(findViewById(R.id.buttonContextMenu));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_action_bar, menu);
	    //MenuItem menuItem = menu.findItem(R.id.menu_search);
	    //menuItem.setVisible(false);
	    return true;
	}

	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.fragment_add_menu_to_action_bar, menu);
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		boolean selected = false;
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_add:
			Toast.makeText(this, "Activity Adding a Clock Action View", Toast.LENGTH_SHORT).show();
			//Con el botón add metemos una action view.
		    item.setActionView(R.layout.action_view_clock);
		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		    	item.expandActionView();
		    }
			selected = true;
			break;
		case R.id.menu_delete:
			Toast.makeText(this, "Activity Delete", Toast.LENGTH_SHORT).show();
			selected = true;
			break;
		/*case R.id.menu_search:
			Toast.makeText(this, "Activity Search", Toast.LENGTH_SHORT).show();
			selected = true;
			break;*/
		case android.R.id.home:
			Toast.makeText(this, "Activity Home", Toast.LENGTH_SHORT).show();
			finish();//Terminamos la activity y volvemos a la lista principal.
			selected = true;
			break;
		default:
			break;
		}
		//Informamos de si hemos o no terminado con el menú option
		return selected;
	}
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case R.id.menu_agenda:
        	Toast.makeText(AdvancedActionBarActivity.this, "Activity menu_agenda center Context menu", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.menu_camera:
        	Toast.makeText(AdvancedActionBarActivity.this, "Activity menu_camera center Context menu", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.menu_call:
        	Toast.makeText(AdvancedActionBarActivity.this, "Activity menu_call center Context menu", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return false;
		}
	}

	//=================================================
	// Functions
	//=================================================
	public void showPopup(View v) {
	    PopupMenu popup = new PopupMenu(this, v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.fragment_add_menu_to_action_bar, popup.getMenu());
	    popup.setOnMenuItemClickListener(new OnMenuItemClickListenerPopup());
	    popup.show();
	}
	//=================================================
	// Getters and Setters
	//=================================================

	//=================================================
	//Classes
	//=================================================
	@SuppressLint("ValidFragment")
	public class OnClickListenerAddFragment implements OnClickListener {
		boolean added = false;
		public void onClick(View v) {
			if (!added){
				//Añadimos el fragment
				getSupportFragmentManager().beginTransaction().
				replace(R.id.LinearLayoutFragmentAddAction, new AdvancedActionBarFragmentAddAction(), ADD_ACTION)
				.addToBackStack(null)
				.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
				.commit();
			
			}
//			added = true;
		}	
	}
	
	public class OnClickListenerShowPopUp implements OnClickListener {
		public void onClick(View v) {
			showPopup(v);	
		}	
	}
	
	public class OnClickListenerAddTabs implements OnClickListener{
		TabListenerMine tabListenerMine = new TabListenerMine();
		public void onClick(View v) {
			tabListenerMine.init();
		}
		
	}
	
	
	public class OnClickListenerCreateDropDown implements OnClickListener {
		OnNavigationListenerDropDown onNavigationListenerDropDown = new OnNavigationListenerDropDown();
		public void onClick(View v) {
			onNavigationListenerDropDown.init();
		}
		
	}

	public class OnClicklistenerCustomizeActionBar implements OnClickListener{

		public void onClick(View v) {
			getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			getActionBar().setCustomView(R.layout.action_bar_custom_view);
			//Ahora vamos a ponerle un listener al botón izquierdo.
			View view = getActionBar().getCustomView();
			view.findViewById(R.id.imageViewStone).setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Toast.makeText(AdvancedActionBarActivity.this, "Action Bar Custom View Stone Click", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		
	}
	
	/**
	 * Listener para el menú que aparece a la izquieda sobre el botón de abajo al hacer click
	 * @author XanXa
	 *
	 */
	public class OnMenuItemClickListenerPopup implements OnMenuItemClickListener {

		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()) {
	        case R.id.menu_agenda:
	        	Toast.makeText(AdvancedActionBarActivity.this, "Activity menu_agenda left pop up", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.menu_camera:
	        	Toast.makeText(AdvancedActionBarActivity.this, "Activity menu_camera left pop up", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.menu_call:
	        	Toast.makeText(AdvancedActionBarActivity.this, "Activity menu_call left pop up", Toast.LENGTH_SHORT).show();
	            return true;
	        default:
	            return false;
			}
			
		}
		
	}
	
	
	/**
	 * Es la clase qeu va a hacer los tabs 
	 */
	
	
	public class TabListenerMine implements TabListener{
		android.app.Fragment mFragment;

		/**
		 * Para la serialización del estado de los tabs.
		 */
		private static final String STATE_SELECTED_ITEM = "selectedItem";

		 public void init (){

		    // Establecemos que el modo de navegación va a ser mediante tabs.
		    final ActionBar actionBar = getActionBar();
		    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		    // Apadimos 10 tabs..
		    actionBar.addTab(actionBar.newTab().setText("Tab 0")
		        .setTabListener(this));
		    actionBar.addTab(actionBar.newTab().setText("Tab 1")
		        .setTabListener(this));
		    actionBar.addTab(actionBar.newTab().setText("Tab 2")
		        .setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText("Tab 3")
			    .setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText("Tab 4")
			    .setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText("Tab 5")
			    .setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText("Tab 6")
			    .setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText("Tab 7")
			    .setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText("Tab 8")
			.setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText("Tab 9")
				    .setTabListener(this));
			actionBar.addTab(actionBar.newTab().setText("Tab 10")
			.setTabListener(this));
		  }

		 
		  public void onRestoreInstanceState(Bundle savedInstanceState) {
		   // Restauramos la última posición mediante el bundle.
		    if (savedInstanceState.containsKey(STATE_SELECTED_ITEM)) {
		      getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_ITEM));
		    }
		  }

		  public void onSaveInstanceState(Bundle outState) {
		    outState.putInt(STATE_SELECTED_ITEM, getActionBar()
		        .getSelectedNavigationIndex());
		  }

		  
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		   
		}


		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			  	Bundle bundle = new Bundle();
			    bundle.putString(ContentFragment.EXTRA_TEXT, tab.getText().toString());
				bundle.putInt(ContentFragment.EXTRA_COLOR, Painter.getRandomColor());
			    
				if (mFragment == null) {
		            // Si no se ha instanciado lo instanciamos aquí. ES EL MODO NORMAL DE INSTANCIAR LOS FRAGMENTS.
		            mFragment = android.app.Fragment.instantiate(AdvancedActionBarActivity.this, ContentFragmentV11.class.getName());
		            mFragment.setArguments(bundle);
		            ft.add(android.R.id.content, mFragment, null);
		        } else {
		            // Si existía lo añadimos.
		        	 mFragment.setArguments(bundle);
		            ft.attach(mFragment);
		        }
		}


		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
	        if (mFragment != null) {
	            // quitamos el fragment para que se pueda poner otro.
	            ft.detach(mFragment);
	            mFragment = null;
	        }
		}

		  
	}
	
	/**
	 * Es la que va a tener toda la lógica del DropDown
	 * @author XanXa
	 *
	 */
	public class OnNavigationListenerDropDown implements OnNavigationListener{		
		  private static final String STATE_SELECTED_ITEM = "selectedItem";
		
		public void init (){
		    final ActionBar actionBar = getActionBar();
		    actionBar.setDisplayShowTitleEnabled(false);
		    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	
		    final String[] dropdownString = new String[] {
		    	"Item 0",
		    	"Item 1",
		    	"Item 2",
		    	"Item 3"
		    };

		    // getActionBar().getThemedContext() para que el fondo sea el mismo que el de la Action Bar
		    android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(
		    		(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) ? actionBar.getThemedContext() : AdvancedActionBarActivity.this,
		        android.R.layout.simple_spinner_item, android.R.id.text1,
		        dropdownString);
	
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
		    // Inicializamos el DropDown con el listener que va a ser esta misma clase.
		    actionBar.setListNavigationCallbacks(adapter, this);
	

	  }

	  public void onRestoreInstanceState(Bundle savedInstanceState) {
	    // Restauramos la última posición mediante el bundle.
	    if (savedInstanceState.containsKey(STATE_SELECTED_ITEM)) {
	      getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_ITEM));
	    }
	  }


	  public void onSaveInstanceState(Bundle outState) {
		//Guardamos la última posición para que en el restore podamos recuperarla.
	    outState.putInt(STATE_SELECTED_ITEM, getActionBar().getSelectedNavigationIndex());
	  }

	  
	  public boolean onNavigationItemSelected(int position, long id) {
	    // When the given dropdown item is selected, show its contents in the
	    // container view.
	    Fragment fragment = new ContentFragment();
	    Bundle bundle = new Bundle();
	    bundle.putString(ContentFragment.EXTRA_TEXT, "Position "+position+", id = "+id);
		bundle.putInt(ContentFragment.EXTRA_COLOR, Painter.getRandomColor());
	    fragment.setArguments(bundle);
	    
	    getSupportFragmentManager().beginTransaction()
	    	.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
	    	.addToBackStack(null)
	    	.replace(R.id.LinearLayoutCreateDropDown, fragment)
	    	.commit();
	    return true;
	  }
	}
	
	//=================================================
	// Test
	//=================================================
}
