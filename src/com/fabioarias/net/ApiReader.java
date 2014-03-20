package com.fabioarias.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ApiReader {
	private String url = "";
	private String action = "";
	private String controller = "";
	private static String TAG = "APIREADER";

	public ApiReader(String url, String action, String controller) {
		this.url = url;
		this.action = action;
		this.controller = controller;
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
	}

	public JSONArray getItems() {
		JSONArray data = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			Log.e(TAG, "CONEXION ");
			// Prepare a request object
			HttpGet httpget = new HttpGet(url + "/" + controller + "/" + action
					+ ".json");
			Log.e(TAG, "CONEXION ");
			// Execute the request
			Log.e(TAG, url + "/" + controller + "/" + action + ".json");
			HttpResponse response = null;
			response = httpclient.execute(httpget);
			Log.e(TAG, "CONEXION ");
			HttpEntity entity = response.getEntity();
			Log.e(TAG, "FINALIZA LA CONEXION ");
			if (entity != null
					&& response.getStatusLine().getStatusCode() == 200) {
				Log.e(TAG, "CONEXION EXITOSA");
				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				Log.e("JSON>>>", result);
				data = new JSONArray(result);
				instream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return data;
	}

	public JSONObject getItem() {
		JSONObject data = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			Log.e(TAG, "CONEXION ");
			// Prepare a request object
			HttpGet httpget = new HttpGet(url + "/" + controller + "/" + action
					+ ".json");
			Log.e(TAG, "CONEXION ");
			// Execute the request
			Log.e(TAG, url + "/" + controller + "/" + action + ".json");
			HttpResponse response = null;
			response = httpclient.execute(httpget);
			Log.e(TAG, "CONEXION ");
			HttpEntity entity = response.getEntity();
			Log.e(TAG, "FINALIZA LA CONEXION ");
			if (entity != null
					&& response.getStatusLine().getStatusCode() == 200) {
				Log.e(TAG, "CONEXION EXITOSA");
				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				Log.e("JSON>>>", result);
				data = new JSONObject(result);
				instream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return data;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
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

	public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;
		int height = 150;
		int width = 150;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = url + "/" + controller + "/" + action + "/"
					+ urls[0] + ".img";

			Bitmap mIcon11 = null;
			try {
				if (urls.length > 1) {
					height = Integer.parseInt(urls[1]);
					if (urls.length > 2) {
						width = Integer.parseInt(urls[2]);
					}
				}
				InputStream in = new java.net.URL(urldisplay).openStream();
				String result = convertStreamToString(in);
				byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
				mIcon11 = BitmapFactory.decodeByteArray(decodedString, 0,
						decodedString.length);
			} catch (Exception e) {
				Log.e("Error", urls[0] + " " + e.getMessage());
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				result = Bitmap.createScaledBitmap(result, height, width, true);
				bmImage.setImageBitmap(result);

				bmImage.setScaleType(ScaleType.CENTER_CROP);
			}
		}
	}
}
