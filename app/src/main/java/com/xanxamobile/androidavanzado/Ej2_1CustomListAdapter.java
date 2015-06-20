package com.xanxamobile.androidavanzado;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

/**
 * Created by jesusm91 on 4/6/15.
 */
public class Ej2_1CustomListAdapter extends ArrayAdapter<Map<String, String>> {

    private final Activity context;
    private List<Map<String, String>> items;

    public Ej2_1CustomListAdapter(Activity context, List<Map<String, String>> items) {
        super(context, R.layout.activity_ej2_1_item, items);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.items=items;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_ej2_1_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        ImageView imageView2 = (ImageView) rowView.findViewById(R.id.icon2);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(items.get(position).get("titulo"));
        imageView.setImageResource(Integer.parseInt(items.get(position).get("imagen")));
        imageView2.setImageResource(Integer.parseInt(items.get(position).get("imagen2")));
        extratxt.setText(items.get(position).get("descripción"));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = (TextView) v.findViewById(R.id.item);
                Toast.makeText(context, "Pulsado el " + title.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;

    };
}
