package com.xanxamobile.androidavanzado;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


public class Ej4Activity extends FragmentActivity implements Ej4ActivityFragment.OnFragmentInteractionListener, Ej4ActivityFragment2.OnFragmentInteractionListener, Ej4ActivityFragment3.OnFragmentInteractionListener {

    private int fragmentActual = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej4);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            Ej4ActivityFragment firstFragment = new Ej4ActivityFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if(fragmentActual==1){
                        // Create fragment and give it an argument specifying the article it should show
                        Ej4ActivityFragment2 newFragment = new Ej4ActivityFragment2();
                        Bundle args = new Bundle();
                        newFragment.setArguments(args);

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.fragment_container, newFragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        fragmentActual = 2;
                    }else if(fragmentActual==2){
                        // Create fragment and give it an argument specifying the article it should show
                        Ej4ActivityFragment3 newFragment = new Ej4ActivityFragment3();
                        Bundle args = new Bundle();
                        newFragment.setArguments(args);

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.fragment_container, newFragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        fragmentActual = 3;
                    }else{
                        // Create fragment and give it an argument specifying the article it should show
                        Ej4ActivityFragment newFragment = new Ej4ActivityFragment();
                        Bundle args = new Bundle();
                        newFragment.setArguments(args);

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.fragment_container, newFragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        fragmentActual = 1;
                    }
                }
            });

        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
