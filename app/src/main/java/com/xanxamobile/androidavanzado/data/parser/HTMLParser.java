package com.xanxamobile.androidavanzado.data.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

/**
 * Se utilizara para parsear las imagenes de las descripciones por si están dentro en formato html.
 * @author Manuel Sánchez Palacios
 *
 */
public class HTMLParser extends GenericParser{

	private final static String IMG = "img",
								SRC = "src";
	
	List<String> urlImages;
	static HTMLParser htmlParser;
	public static HTMLParser getInstance(){
		if (htmlParser == null)
			htmlParser = new HTMLParser();
		return htmlParser;
	}
	
	
	public List<String> findURLImages(String textToParse) throws XmlPullParserException, IOException {
		urlImages = null;
		if (textToParse != null)
		{
			urlImages = new LinkedList<String>();
			InputStream inputStreamActual = new ByteArrayInputStream(textToParse.getBytes("UTF-8"));
			parse(inputStreamActual);
		}
		return urlImages;
	}


	@Override
	protected void startDocument(XmlPullParser parser) {
		urlImages = new LinkedList<String>();
	}


	@Override
	protected void startTag(XmlPullParser parser) {
		String name = parser.getName();
		if (name.equals(IMG))
		{
			int attributeCount = parser.getAttributeCount();
			for (int i = 0; i < attributeCount; i++) {
				String attributeName = parser.getAttributeName(i);
				if (attributeName.equals(SRC))
				{
					String urlImage = parser.getAttributeValue(i);
					Log.d("HTMLParser", "urlImage "+urlImage);
					urlImages.add(urlImage);//Obtenemos el valor de la imagen.
					break;
				}
			}
			
				
			
		}
	}

	@Override
	protected void endTag(XmlPullParser parser) {}


	@Override
	protected void endDocument() {}


	public String removeImgTags(String nextText) {
		return nextText.replaceAll("<img*+/>", "");
	}
	
	
	
}
