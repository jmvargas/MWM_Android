package com.xanxamobile.androidavanzado;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Ej2_1Activity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Map<String, String>> items = new ArrayList<Map<String, String>>();
        Map<String, String> m = new HashMap<String, String>();
        m.put("titulo", "Primer elemento");
        m.put("descripción", "Descripción del primer elemento");
        Integer d = new Integer(R.drawable.papel);
        m.put("imagen", d.toString());
        d = new Integer(R.drawable.piedra);
        m.put("imagen2", d.toString());
        items.add(m);
        m = new HashMap<String, String>();
        m.put("titulo", "Segundo elemento");
        m.put("descripción", "Descripción del segundo elemento");
        d = new Integer(R.drawable.ic_launcher);
        m.put("imagen", d.toString());
        d = new Integer(R.drawable.pot);
        m.put("imagen2", d.toString());
        items.add(m);
        m = new HashMap<String, String>();
        m.put("titulo", "Tercer elemento");
        m.put("descripción", "Descripción del tercer elemento");
        d = new Integer(R.drawable.piedra);
        m.put("imagen", d.toString());
        d = new Integer(R.drawable.papel);
        m.put("imagen2", d.toString());
        items.add(m);
        m = new HashMap<String, String>();
        m.put("titulo", "Cuarto elemento");
        m.put("descripción", "Descripción del cuarto elemento");
        d = new Integer(R.drawable.square_root);
        m.put("imagen", d.toString());
        d = new Integer(R.drawable.tijera);
        m.put("imagen2", d.toString());
        items.add(m);
        m = new HashMap<String, String>();
        m.put("titulo", "Quinto elemento");
        m.put("descripción", "Descripción del quinto elemento");
        d = new Integer(R.drawable.tijera);
        m.put("imagen", d.toString());
        d = new Integer(R.drawable.ic_launcher);
        m.put("imagen2", d.toString());
        items.add(m);
        setContentView(R.layout.activity_ej2_1);
        ListView list1 = (ListView) findViewById(R.id.laListView);
        list1.setAdapter(new Ej2_1CustomListAdapter(
                this, items));
    }
}