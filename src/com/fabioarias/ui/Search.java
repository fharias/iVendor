package com.fabioarias.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabioarias.R;
import com.fabioarias.custom.CustomFragment;
import com.fabioarias.custom.ImageClick;
import com.fabioarias.net.ApiReader;
import com.fabioarias.net.ApiReader.DownloadImageTask;

/**
 * The Class Search is the Fragment class that is launched when the user
 * clicks on Search button in Left navigation drawer.
 */
public class Search extends CustomFragment
{
	private JSONArray categories = new JSONArray();
	private Activity activity = null;
	private View v = null;
	private EditText searchField = null;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.search, null);
		View scanView = (View)v.findViewById(R.id.p2);
		searchField = (EditText)v.findViewById(R.id.editText1);
		setTouchNClick(v.findViewById(R.id.p1));
		setTouchNClick(scanView);
		this.v = v;
		return v;
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		switch(v.getId()){
		case R.id.p1:
			// BUSQUEDA POR CRITERIA
			ApiReader reader = new ApiReader("http://winbugs.cloudapp.net/vendormachine","searchByCriteria/"+searchField.getText(),"servers");
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
			GridView grid = (GridView) this.v.findViewById(R.id.grid_search);
			grid.setVisibility(View.VISIBLE);
			grid.setAdapter(new GridAdapter());
			break;
		case R.id.p2:
			// SCANING BARCODE
			
			break;
		}
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
				text2.setText("CLP$ "+item.getJSONObject("Item").getString("Cost"));
				new ApiReader("http://winbugs.cloudapp.net/vendormachine","image","servers").new DownloadImageTask(img).execute(item.getJSONObject("Item").getString("Code"));
				img.setOnClickListener(new ImageClick(item, activity));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			return v;
		}
	}
}
