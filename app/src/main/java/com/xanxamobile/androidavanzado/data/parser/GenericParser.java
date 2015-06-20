package com.xanxamobile.androidavanzado.data.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.xanxamobile.androidavanzado.data.RSSArticle;

public abstract class GenericParser {

	boolean done = false;
	public void parse (InputStream inputStreamActual) throws XmlPullParserException, IOException
	{
		done = false;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStreamActual,null);
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT && !done){
			String name = null;
			switch (eventType){
				case XmlPullParser.START_DOCUMENT:
					startDocument(parser);
					break;
				case XmlPullParser.START_TAG:
					startTag(parser);
					break;
				case XmlPullParser.END_TAG:
					endTag(parser);
					break;
			}
			try {
				eventType = parser.next();	
			} catch (Exception e) {
				Log.e("GenericParser", "Error parser.next");
				e.printStackTrace();
				break;
			}
		}
		endDocument();
		inputStreamActual.close();
	}
	
	
	abstract protected void startDocument(XmlPullParser parser);
	abstract protected void startTag(XmlPullParser parser);
	abstract protected void endTag(XmlPullParser parser);
	abstract protected void endDocument();
}
