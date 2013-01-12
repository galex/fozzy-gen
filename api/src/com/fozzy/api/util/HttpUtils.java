package com.fozzy.api.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

public final class HttpUtils {

	private static String sRequestContentCharSet = HTTP.DEFAULT_CONTENT_CHARSET;

	public static final int DEFAULT_TIMEOUT = 10000;

	private static final String CONTENT_TYPE_HEADER_PARAM_NAME = "Content-Type";
	private static final String CONTENT_TYPE_HEADER_PARAM_VALUE = "application/x-www-form-urlencoded";

	public enum Method {
		GET, POST, DELETE, PUT
	};

	/**
	 * Allows the change the request content charset to the specified one : such
	 * as HTTP.UTF-8 The default charset is ISO-8859-1 as defined in the HTTP
	 * standard.
	 * 
	 * @param contentCharSet a static final String member of HTTP
	 */
	public static void setRequestContentCharset(String contentCharSet) {
		sRequestContentCharSet = contentCharSet;
	}

	/**
	 * Used to make an httpPost
	 * 
	 * @param urlString
	 * @param parameters the parameters should look like this :
	 *            name=value&name2=value2 ....
	 * @return
	 * @throws IOException
	 */

	public static InputStream post(String urlString, String parameters) throws IOException {
		return makeRequest(urlString, parameters, Method.POST, null, null, DEFAULT_TIMEOUT);
	}

	public static InputStream get(String urlString) throws IOException {

		return makeRequest(urlString, null, Method.GET, null, null, DEFAULT_TIMEOUT);
	}

	public static InputStream get(String urlString, String username, String passsord) throws IOException {

		return makeRequest(urlString, null, Method.GET, username, passsord, DEFAULT_TIMEOUT);
	}

	public static InputStream put(String urlString) throws IOException {

		return makeRequest(urlString, null, Method.PUT, null, null, DEFAULT_TIMEOUT);
	}

	public static InputStream delete(String urlString) throws IOException {

		return makeRequest(urlString, null, Method.DELETE, null, null, DEFAULT_TIMEOUT);
	}

	public static InputStream post(String urlString, List<NameValuePair> parameters) throws IOException {
		return post(urlString, parameters, null, null, null, DEFAULT_TIMEOUT, true);
	}

	public static InputStream post(String urlString, List<NameValuePair> parameters, boolean isPortRangeLimited) throws IOException {
		return post(urlString, parameters, null, null, null, DEFAULT_TIMEOUT, isPortRangeLimited);
	}

	public static InputStream post(String urlString, List<NameValuePair> parameters, List<Header> headers, String username, String password, int timeout, boolean isPortRangeLimited) throws HttpException, IOException {

		InputStream stream = null;
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(params, timeout);
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		DefaultHttpClient httpclient = new DefaultHttpClient(params);

		if (username != null && password != null) {
			httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new UsernamePasswordCredentials(username, password));
		}

		HttpPost post = new HttpPost(urlString);
		post.setHeader(new BasicHeader(CONTENT_TYPE_HEADER_PARAM_NAME, CONTENT_TYPE_HEADER_PARAM_VALUE));

		if (parameters != null)
			post.setEntity(new UrlEncodedFormEntity(parameters, sRequestContentCharSet));

		HttpResponse response = httpclient.execute(post);

		if (isPortRangeLimited) {
			
			if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
				stream = response.getEntity().getContent();
			} else {
				int statusCode = response.getStatusLine().getStatusCode();

				//throw new IOException("Server error " + response.getStatusLine().getStatusCode() + " Message :" + response.getStatusLine().getReasonPhrase());
				throw new HttpException(statusCode,"Server error " + response.getStatusLine().getStatusCode() + " Message :" + response.getStatusLine().getReasonPhrase());
			}
		} else stream = response.getEntity().getContent();

		return stream;
	}

	public static InputStream makeRequest(String urlString, String parameters, Method method, String username, String password, int timeout) throws HttpException,IOException {

		InputStream stream = null;
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(params, timeout);
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		DefaultHttpClient httpclient = new DefaultHttpClient(params);

		if (username != null && password != null) {
			httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new UsernamePasswordCredentials(username, password));
		}

		HttpUriRequest request = null;

		switch (method) {
			case GET:
				request = new HttpGet(merge(urlString, parameters));
				break;
			case POST:
				HttpPost post = new HttpPost(urlString);
				post.setHeader(new BasicHeader(CONTENT_TYPE_HEADER_PARAM_NAME, CONTENT_TYPE_HEADER_PARAM_VALUE));

				if (parameters != null) {
					List<NameValuePair> nameValuePairs = extractNameValuePairs(parameters);
					post.setEntity(new UrlEncodedFormEntity(nameValuePairs, sRequestContentCharSet));
				}
				request = post;
				break;
			case DELETE:
				request = new HttpDelete(merge(urlString, parameters));
				break;
			case PUT:
				request = new HttpPut(merge(urlString, parameters));
				break;
		}

		HttpResponse response = httpclient.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode >= 200 && statusCode < 300) {
			stream = response.getEntity().getContent();
		} else {
			throw new HttpException(statusCode,"Server error " + response.getStatusLine().getStatusCode() + " Message : " + response.getStatusLine().getReasonPhrase());
		}

		return stream;
	}

	private static String merge(String url, String parameters) {
		String res = url;
		if (parameters != null) {
			char delimiter = '?';
			if (url.contains("?")) {
				delimiter = '&';
			}
			res = String.format("%s%c%s", url, delimiter, parameters);
		}
		return res;
	}

	private static List<NameValuePair> extractNameValuePairs(String parameters) {
		String paramsStr[] = parameters.split("&");
		List<NameValuePair> parametersPairs = new ArrayList<NameValuePair>(paramsStr.length);
		for (int i = 0; i < paramsStr.length; i++) {
			String stringSplitted[] = paramsStr[i].split("=");
			String name = stringSplitted[0];
			String value = stringSplitted[1];
			parametersPairs.add(new BasicNameValuePair(name, value));
		}
		return parametersPairs;
	}

	public static InputStream uploadFile(String urlString, String fileName, String fieldName, String mimeType) throws IOException {
		FileInputStream input = new FileInputStream(fileName);
		InputStream result = uploadFile(urlString, input, fileName, fieldName, mimeType, null);
		input.close();
		return result;
	}

	public static InputStream uploadFile(String urlString, String fileName, String fieldName, String mimeType, List<NameValuePair> params) throws IOException {
		FileInputStream input = new FileInputStream(fileName);
		InputStream result = uploadFile(urlString, input, fileName, fieldName, mimeType, params, true);
		input.close();
		return result;
	}

	public static InputStream uploadFile(String urlString, String fileName, String fieldName, String mimeType, List<NameValuePair> params, boolean chuncked) throws IOException {
		FileInputStream input = new FileInputStream(fileName);
		InputStream result = uploadFile(urlString, input, fileName, fieldName, mimeType, params, chuncked);
		input.close();
		return result;
	}

	public static InputStream uploadFile(String urlString, InputStream input, String fileName, String fieldName, String mimeType) throws IOException {
		return uploadFile(urlString, input, fileName, fieldName, mimeType, null);
	}

	public static InputStream uploadFile(String urlString, InputStream input, String fileName, String fieldName, String mimeType, List<NameValuePair> params) throws IOException {
		return uploadFile(urlString, input, fileName, fieldName, mimeType, params, true);
	}

	public static InputStream uploadFile(String urlString, InputStream input, String fileName, String fieldName, String mimeType, List<NameValuePair> params, boolean chuncked) throws IOException {

		String escapedFileName = fileName.replaceAll("\"", "\\\\\"");

		String boundary = "---------------------------239413274531762";
		String lineEnd = "\r\n";
		String twoHyphens = "--";

		URL connectURL = new URL(urlString);

		HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
		if (chuncked)
			conn.setChunkedStreamingMode(0);

		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		conn.setRequestProperty("Content-Length", "" + new File(fileName).length());

		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

		if (params != null) {
			for (NameValuePair param : params) {
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"" + param.getName() + "\"");
				dos.writeBytes(lineEnd);
				dos.writeBytes(lineEnd);
				dos.writeBytes(param.getValue());
				dos.writeBytes(lineEnd);
			}
		}

		dos.writeBytes(twoHyphens + boundary + lineEnd);
		dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + escapedFileName + "\"");
		dos.writeBytes(lineEnd);
		dos.writeBytes("Content-Type: " + mimeType);
		dos.writeBytes(lineEnd);
		dos.writeBytes(lineEnd);

		// create a buffer of maximum size
		int bytesAvailable = input.available();
		int maxBufferSize = 8192;
		int bufferSize = Math.min(bytesAvailable, maxBufferSize);
		byte[] buffer = new byte[bufferSize];

		// read file and write it into form...
		int bytesRead = input.read(buffer, 0, bufferSize);

		while (bytesRead > 0) {
			dos.write(buffer, 0, bufferSize);
			bytesAvailable = input.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			bytesRead = input.read(buffer, 0, bufferSize);
			dos.flush();
		}

		// send multipart form data necesssary after file data...
		dos.writeBytes(lineEnd);
		dos.writeBytes(twoHyphens + boundary + lineEnd);
		dos.writeBytes(lineEnd);

		// close streams
		dos.flush();
		dos.close();

		InputStream is = null;
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			is = conn.getInputStream();

		return is;
	}

	public static int getStatusCode(final String url) {
		int statusCode = 404;
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(params, DEFAULT_TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(params, DEFAULT_TIMEOUT);
		DefaultHttpClient httpclient = new DefaultHttpClient(params);

		HttpUriRequest request = new HttpGet(url);

		HttpResponse response;
		try {
			response = httpclient.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}

		return statusCode;
	}
	
	public static class HttpException extends IOException {
		
		private static final long serialVersionUID = -57201917012622483L;
		private int statusCode;
		
		public HttpException(int statusCode,String message) {
			super(message);
			this.statusCode = statusCode;
		}
		
		public int getStatusCode() {
			return statusCode;
		}
	}
}
