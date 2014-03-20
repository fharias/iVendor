package com.fabioarias.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.util.Log;

public class ApiController extends AsyncTask<String, Void, Void> {
	private final HttpClient client = new DefaultHttpClient();
	private String content;
	private String error = "NO ERROR";
	private JSONArray datajson = null;
	String data = "";
	private boolean ready = false;

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	int sizeData = 0;

	public JSONArray getDatajson() {
		return datajson;
	}

	public void setDatajson(JSONArray datajson) {
		this.datajson = datajson;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	protected Void doInBackground(String... urls) {
		/************ Make Post Call To Web Server ***********/
		BufferedReader reader = null;

		// Send data
		try {

			datajson = receive(urls[0]);
		} catch (Exception ex) {
			ex.printStackTrace();
			error = ex.getMessage();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception ex) {
			}
		}
		return null;
	}

	private JSONArray receive(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		// Execute the request
		HttpResponse response;

		JSONArray arr = null;
		try {
			response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null
					&& response.getStatusLine().getStatusCode() == 200) {
				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				Log.e("JSON>>>", result);
				arr = (JSONArray) new JSONTokener(result).nextValue();
				;
				instream.close();

			}
			ready = true;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block

			Log.e("ERROR>>>>", e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("ERROR>>>>", e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.getStackTraceString(e);
			Log.e("ERROR>>>>", e.toString());
		}

		return arr;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			Log.e("ERROR>>>>" + "ERROR", e.toString());

		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("ERROR>>>>" + "ERRO", e.toString());
			}
		}
		return sb.toString();
	}

}
