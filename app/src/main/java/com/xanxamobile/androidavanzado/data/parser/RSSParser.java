package com.xanxamobile.androidavanzado.data.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.net.Uri;
import android.util.Log;
import android.util.Xml;

import com.xanxamobile.androidavanzado.data.RSSArticle;

/**
 * http://www.make-rss-feeds.com/rss-tags.htm
 * @author Manuel Sánchez Palacios
 *
 */
public class RSSParser {
	
	//===========================================
	// Constantes TAGS RSS
	//===========================================
	// names of the XML tags
		static final String LANGUAGE 		= "LANGUAGE",
							//Fecha
							PUB_DATE 		= "pubDate",
							LAST_BUILD_DATE = "lastBuildDate",
							//RSS
							RSS				= "rss",
							CHANNEL 		= "channel",
							DESCRIPTION 	= "description",
							LINK 			= "link",
							TITLE 			= "title",
							ITEM 			= "item",
							CREATOR 		= "creator",
							COPYRIGHT 		= "copyright",
							AUTHOR			= "author",
							TTL				= "ttl",
							ENCLOSURE		= "enclosure",
							//Value
							URL				= "url",
							//Image
							IMAGE			= "image",
							url				= "url";
		
		
	//================================================
	// Variables
	//================================================
	private static RSSParser parser;
	public static RSSParser getInstance(){
		if (parser == null)
			parser = new RSSParser();
		return parser;
	}
	
	/**
	 * 
	 * @param feedUrls --> Las direcciones RSS a parsear.
	 * @return una lista de Articulos parseados
	 */
	public List<RSSArticle> parse (List<String> feedUrls)
	{
		List<RSSArticle> resultRSSArticles = new ArrayList<RSSArticle>();
		for (String feedUrl : feedUrls) {
			try {
				resultRSSArticles.addAll(parse(feedUrl));	
			} catch (Exception e) {
				Log.e(this.getClass().toString(), "feedUrl = "+feedUrl);
				e.printStackTrace();
			}
			
		}
		return resultRSSArticles;
	}
	
	/**
	 * @param rssURL
	 * @return Una lista con los artículos ya creados.
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	public List<RSSArticle> parse(String rssURL) throws IOException, XmlPullParserException{
		List<RSSArticle> resultRSSArticles = null;
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStreamActual = getInputStream(rssURL);
		parser.setInput(inputStreamActual,null);
		int eventType = parser.getEventType();
		RSSArticle actualArticle = null;
		boolean done = false;
		while (eventType != XmlPullParser.END_DOCUMENT && !done){
			String name = null;
			switch (eventType){
				case XmlPullParser.START_DOCUMENT:
					resultRSSArticles = new ArrayList<RSSArticle>();
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					
					//Log.d(tag, "START TAG *NAME ==> "+name);
					if (name.equalsIgnoreCase(LAST_BUILD_DATE)){
						
					}
					else if (name.equalsIgnoreCase(ITEM)){ //Iniciamos el articulo por cada item
						actualArticle = new RSSArticle();
					} else if (actualArticle != null){
						if (name.equalsIgnoreCase(LINK)){
							setLink(actualArticle, parser.nextText());
						} else if (name.equalsIgnoreCase(DESCRIPTION)){
							setDescription(actualArticle, parser.nextText());
						} else if (name.equalsIgnoreCase(ENCLOSURE)){
							setImage(actualArticle, parser);//parser.nextText());
						}
						else if (name.equalsIgnoreCase(PUB_DATE)){
							
						} else if (name.equalsIgnoreCase(TITLE)){
							setTitle (actualArticle, parser.nextText());
						}else if (name.equalsIgnoreCase(CREATOR) || name.equalsIgnoreCase(AUTHOR))
						{
							setAuthor (actualArticle, parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase(ITEM) && actualArticle != null){
						resultRSSArticles.add(actualArticle);
					} else if (name.equalsIgnoreCase(CHANNEL)){
						done = true;
					}
					break;
			}
			try {
				eventType = parser.next();	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		inputStreamActual.close();
		///////////////////////////
		return resultRSSArticles;
	}


	//=============================
	//Getters&Setters
	//=============================
	/**
	//TODO Por el alumno: Cache
	*  <li>
	*  EXTRA --> Cachear el rss en local (Ya sea en SD o en memoria interna) para ello hemos de crearnos un inputStream de un fichero que descarguemos.
	*  </li>
	*/ 
	private InputStream getInputStream(String url) throws MalformedURLException, IOException {
		InputStream stream = new URL(url).openStream();
		return stream;
	}
	
	private void setLink(RSSArticle actualArticle, String nextText) {
		actualArticle.setLink (nextText);
		
	}

	private void setAuthor(RSSArticle actualArticle, String nextText) {
		actualArticle.setAuthor(nextText);
		
	}

	private void setTitle(RSSArticle actualArticle, String nextText) {
		actualArticle.setTitle(nextText);
	}
	
	private void setImage (RSSArticle actualArticle, XmlPullParser parser) {
		if (actualArticle.getImage() == null){
			for (int i = 0; i < parser.getAttributeCount(); i++) {
				if (parser.getAttributeName(i).equals(URL)){
					actualArticle.setImage(parser.getAttributeValue(i));		
				}
					
			}
		}
	}
	/**
	 * La descripción muchas veces es un html en <![CDATA[ por lo que deberemos volver a parsearlo. para extraer la imagen.
	 * @param actualArticle
	 * @param nextText
	 */
	private void setDescription(RSSArticle actualArticle, String nextText) {
		HTMLParser htmlParser = HTMLParser.getInstance();
		List<String> urlsImages = null;
		try {
			try {
				urlsImages = htmlParser.findURLImages(nextText);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (urlsImages != null && !urlsImages.isEmpty())
			{
				actualArticle.setImage(urlsImages.get(0));
			}
		
		if (urlsImages != null && !urlsImages.isEmpty())
			nextText = htmlParser.removeImgTags (nextText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		actualArticle.setDescription(nextText);
	}
}
