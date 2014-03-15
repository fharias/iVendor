package com.fabioarias.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fabioarias.MainActivity;
import com.fabioarias.R;
import com.fabioarias.custom.CustomFragment;
import com.fabioarias.custom.ImageClick;
import com.fabioarias.net.ApiReader;

/**
 * The Class Showcase is the Fragment class that is launched when the user
 * clicks on Showcase button in Left navigation drawer.
 */
public class Showcase extends CustomFragment
{
	
	private JSONArray categories = new JSONArray();
	private MainActivity activity = null;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.showcase, null);
		activity = (MainActivity)this.getActivity();
		ApiReader reader = new ApiReader(getActivity().getString(R.string.host),"categories","servers");
		try{
			categories = reader.getItems();
			Log.e("CATEGORIAS", "LEYENDO CATEGORIAS");
			if(categories != null){
				Log.e("CATEGORIAS", "OK");
			}else{
				Log.e("CATEGORIAS", "NOK ");
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.e("CATEGORIAS", e.getMessage());
		}
		GridView grid = (GridView) v.findViewById(R.id.grid);
		grid.setAdapter(new GridAdapter());
		return v;
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		
	}

	/**
	 * The Class GridAdapter is the adpater for displaying Products in GridView.
	 * The current implementation simply display dummy product images. You need
	 * to change it as per your needs.
	 */
	private class GridAdapter extends BaseAdapter
	{
		
		
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			if(categories != null){
				try {
					return categories.length();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return 0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int index)
		{
			try{
				return null;
			}catch(Exception e){
				
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2)
		{
			if (v == null)
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.profile_item, null);
			ImageView img = (ImageView) v.findViewById(R.id.img);
			TextView text = (TextView)v.findViewById(R.id.lbl);
			TextView text2 = (TextView)v.findViewById(R.id.lbl2);
			JSONObject item;
			try {
				item = categories.getJSONObject(pos);
				text.setText(item.getJSONObject("Item").getString("Description1"));
				text2.setText("Stock "+item.getJSONObject("Item").getString("OnHand"));
				new ApiReader(getActivity().getString(R.string.host),"image","servers").new DownloadImageTask(img).execute(item.getJSONObject("Item").getString("Code"));
				img.setOnClickListener(new ImageClick(item, activity));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			return v;
		}
	}
}
