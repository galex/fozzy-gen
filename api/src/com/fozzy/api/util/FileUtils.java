package com.fozzy.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 
 * @author Christophe Beyls
 */
public class FileUtils {

	private static final String DEFAULT_ENCODING = "UTF-8";

	public static void copyStream(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[8192];
		int readBytes;
		while ((readBytes = is.read(buffer)) != -1) {
			os.write(buffer, 0, readBytes);
		}
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		return convertStreamToString(is, DEFAULT_ENCODING);
	}

	public static String convertStreamToString(InputStream is, String encoding) throws IOException {
		InputStreamReader reader = new InputStreamReader(is, encoding);
		StringBuilder out = new StringBuilder();
		char[] buffer = new char[4096];
		int readChars;
		while ((readChars = reader.read(buffer)) != -1) {
			out.append(buffer, 0, readChars);
		}
		return out.toString();
	}

	public static String convertStreamToString(InputStream is, OutputStream os) throws IOException {
		return convertStreamToString(is, DEFAULT_ENCODING, os);
	}

	/**
	 * Builds a String from an InputStream while simultaneously copying it to an OutputStream, using the specified encoding.
	 */
	public static String convertStreamToString(InputStream is, String encoding, OutputStream os) throws IOException {
		InputStreamReader reader = new InputStreamReader(is, encoding);
		StringBuilder out = new StringBuilder();
		Writer outWriter = new OutputStreamWriter(os, encoding);
		char[] buffer = new char[4096];
		int readChars;
		while ((readChars = reader.read(buffer)) != -1) {
			out.append(buffer, 0, readChars);
			outWriter.write(buffer, 0, readChars);
		}
		outWriter.flush();
		return out.toString();
	}
}
