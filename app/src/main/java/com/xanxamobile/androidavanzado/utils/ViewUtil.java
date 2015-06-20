package com.xanxamobile.androidavanzado.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewUtil {

	/**
	 * 
	 * @param context
	 * @param resource
	 * @param viewFather
	 * @param add True si hemos de añadir al padre el resource pasado.
	 * @return
	 */
	public static View inflateView (Context context, int resource, ViewGroup viewFather, boolean add)
	{
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewInflated = (View)vi.inflate(resource, viewFather, false);
		if (add)
			try {
				viewFather.addView(viewInflated);	
			} catch (Exception e) {
				//e.printStackTrace();
			}
	    return viewInflated;   
	}
	
	/**
	 * Transforma un texto con algo de html y le pone los links
	 * @param textView
	 * @param description
	 */
	public static void setLinks(TextView textView, String description) {
		setLinks(textView, description,false);
	}

	/**
	 * Activa los links de una descripción pudiendo ir a ellos si se les hace click
	 * @param textView
	 * @param description
	 * @param textViewWithMoving
	 */
	public static void setLinks(TextView textView, String description, boolean textViewWithMoving) {
		textView.setLinksClickable(true);
		try {
			Spanned textSpan = android.text.Html.fromHtml(description);
			SpannableString ss = new SpannableString(textSpan);
			textView.setText(ss);
			if(textViewWithMoving){
				textView.setMovementMethod(LinkMovementMethod.getInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
