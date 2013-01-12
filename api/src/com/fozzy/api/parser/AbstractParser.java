package com.fozzy.api.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public abstract class AbstractParser<T> extends DefaultHandler implements IParser<T> {

	StringBuilder content = new StringBuilder();
	StringBuilder currentPath = new StringBuilder();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		content.delete(0, content.length());
		currentPath.append("/").append(localName);
		OnStartElem(localName, currentPath.toString(), attributes);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		content.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		OnEndElem(localName, currentPath.toString(), content.toString());
		currentPath.delete(currentPath.length() - localName.length() - 1, currentPath.length());
		content.delete(0, content.length());
	}

	@Override
	public void parse(InputStream is) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(is, this);
		} catch (ParserConfigurationException e) {
			Log.e("parser", "ParserConfigurationException: " + e.getMessage());
		} catch (SAXException e) {
			Log.e("parser", "SAXException: " + e.getMessage());
		} catch (IOException e) {
			Log.e("parser", "IOException: " + e.getMessage());
		}
	}

	public abstract void OnStartElem(String name, String path, Attributes attributes);

	public abstract void OnEndElem(String name, String path, String content);

}
