package com.xanxamobile.androidavanzado;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.xanxamobile.androidavanzado.fragments.ContentFragment;
import com.xanxamobile.androidavanzado.fragments.ListMenuFragment;

public class FragmentActivityExample extends FragmentActivity {
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_main);	
		findViewById(R.id.imageViewSepparator).setOnClickListener(new OnClickListenerDismiss());
		getActionBar().setTitle("Fragment Example");
	}

	

	

	/**
	 * hace que desaparezca el fragment de menú 
	 */
	public void dismissMenu() {
		ListMenuFragment listMenuFragment = (ListMenuFragment)getSupportFragmentManager().findFragmentById(R.id.menuFragment);
		if (listMenuFragment != null && listMenuFragment.isVisible()){
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		//	ft.setCustomAnimations(android.R.anim.fade_in, 0);
			ft.hide(listMenuFragment);
			ft.commit();
			
		}else {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			//ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
			ft.show(listMenuFragment);
			ft.commit();
		}
	}
	
	
	public class OnClickListenerDismiss implements OnClickListener{

		public void onClick(View v) {
			dismissMenu();
		}		
	}
}
