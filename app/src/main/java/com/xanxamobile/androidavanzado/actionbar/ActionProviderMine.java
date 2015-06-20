package com.xanxamobile.androidavanzado.actionbar;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Api level 14
 * @author XanXa
 *
 */
@SuppressLint("NewApi")
public class ActionProviderMine extends ActionProvider implements OnMenuItemClickListener{


	
	
	
	//=================================================
	// Constants
	//=================================================

	static final int ITEMS = 5;

	

	//=================================================
	// Fields
	//=================================================
	Context context;
	//=================================================
	// Constructors
	//=================================================
	public ActionProviderMine(Context context) {
		super(context);
		this.context = context;
	}

	//=================================================
	// override function
	//=================================================
	@Override
	public View onCreateActionView() {
		Log.d(this.getClass().getSimpleName(), "onCreateActionView");
 
		TextView textView = new TextView(context);
		textView.setText("Photo");
 
		return null;
	}
 
	@Override
	public boolean onPerformDefaultAction() {
		Log.d(this.getClass().getSimpleName(), "onPerformDefaultAction");
 
		return super.onPerformDefaultAction();
	}
 
	@Override
	public boolean hasSubMenu() {
		Log.d(this.getClass().getSimpleName(), "hasSubMenu");
 
		return true;
	}
 
	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		Log.d(this.getClass().getSimpleName(), "onPrepareSubMenu");
		//Limpiamos el submenú.
		subMenu.clear();
 
		PackageManager manager = context.getPackageManager();
		List<ApplicationInfo> applicationList = manager.getInstalledApplications(PackageManager.GET_ACTIVITIES);
		for (int i = 0; i < Math.min(ITEMS, applicationList.size()); i++) {
			ApplicationInfo appInfo = applicationList.get(i);
			subMenu.add(0, i, i, manager.getApplicationLabel(appInfo))
					.setIcon(appInfo.loadIcon(manager))
					.setOnMenuItemClickListener(this);
		}
 
		if (ITEMS < applicationList.size()) {
			subMenu = subMenu.addSubMenu(Menu.NONE, ITEMS, ITEMS, "More Activities");
			for (int i = 0; i < applicationList.size(); i++) {
				ApplicationInfo appInfo = applicationList.get(i);
				subMenu.add(0, i, i, manager.getApplicationLabel(appInfo))
				.setIcon(appInfo.loadIcon(manager))
				.setOnMenuItemClickListener(this);
			}
		}
	}

	public boolean onMenuItemClick(MenuItem item) {
		//Mostramos qué aplicación es.
		Toast.makeText(context, "Action Provider Mine --> App: "+item.getTitle(), Toast.LENGTH_SHORT).show();
		return true;
	}
	//=================================================
	// Functions
	//=================================================

	//=================================================
	// Getters and Setters
	//=================================================

	//=================================================
	//Classes
	//=================================================

	//=================================================
	// Test
	//=================================================
}
