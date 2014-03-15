package com.fabioarias.ui;

import mobi.pdf417.Pdf417MobiScanData;
import mobi.pdf417.Pdf417MobiSettings;
import mobi.pdf417.activity.Pdf417ScanActivity;
import net.photopay.base.BaseBarcodeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fabioarias.CustomScanActivity;
import com.fabioarias.MainActivity;
import com.fabioarias.R;
import com.fabioarias.custom.CustomFragment;
import com.fabioarias.custom.ImageClick;
import com.fabioarias.net.ApiReader;
import com.fabioarias.net.ApiReader.DownloadImageTask;

/**
 * The Class Search is the Fragment class that is launched when the user
 * clicks on Search button in Left navigation drawer.
 */
@SuppressLint("ValidFragment")
public class Search extends CustomFragment
{
	private JSONArray categories = new JSONArray();
	private MainActivity activity = null;
	private View v = null;
	private EditText searchField = null;
	private String scan = null;
	
	public Search(){
		
	}
	
	public Search(String scan){
		this.scan = scan;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.search, null);
		View scanView = (View)view.findViewById(R.id.p2);
		searchField = (EditText)view.findViewById(R.id.editText1);
		setTouchNClick(view.findViewById(R.id.p1));
		setTouchNClick(scanView);
		searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView vv, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		        	// BUSQUEDA POR CRITERIA
					search(searchField.getText().toString());
		            return true;
		        }
		        return false;
		    }
		});
		
		this.v = view;
		if(scan != null){
			search(scan);
		}
		return view;
	}
	
	protected void search(String criteria){
		ApiReader reader = new ApiReader(getActivity().getString(R.string.host),"searchByCriteria/"+criteria,"servers");
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
		GridView grid = (GridView) v.findViewById(R.id.grid_search);
		grid.setVisibility(View.VISIBLE);
		grid.setAdapter(new GridAdapter());
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
			ApiReader reader = new ApiReader(getActivity().getString(R.string.host),"searchByCriteria/"+searchField.getText(),"servers");
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
			try {
				//Pdf417MobiSettings sett = new Pdf417MobiSettings();
		        // Set this to true to scan even barcode not compliant with standards
		        // For example, malformed PDF417 barcodes which were incorrectly encoded
		        // Use only if necessary because it slows down the recognition process
		        //		sett.setUncertainScanning(true);
		        // Set this to true to scan barcodes which don't have quiet zone (white area) around it
		        // Use only if necessary because it drastically slows down the recognition process 
		        //sett.setNullQuietZoneAllowed(true);
		        // set this to true to enable QR code scanning
		        //sett.setQrCodeEnabled(true);
		        // set this to true to prevent showing dialog after successful scan
		        //sett.setDontShowDialog(false);
		        //sett.setAll1DBarcodesEnabled(true);
		        //sett.setCode128Enabled(true);
		        //sett.setCode39Enabled(true);
		        //sett.setEan13Enabled(true);
		        //sett.setEan8Enabled(true);
		        // if license permits this, remove Pdf417.mobi logo overlay on scan activity
		        // if license forbids this, this option has no effect
		        Log.i("SEARCH", ((MainActivity)getActivity()).getCart().toString());
		        Bundle b = new Bundle();
		        Intent intent = new Intent("com.fabioarias.SCAN");
		        
		        //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		        startActivityForResult(intent, 0);
		        //sett.setRemoveOverlayEnabled(true);
				//Intent intent = new Intent((MainActivity)getActivity(), Pdf417ScanActivity.class);
				//intent.putExtra(BaseBarcodeActivity.EXTRAS_SETTINGS, sett);
				//intent.putExtra("CART", ((MainActivity)getActivity()).getCart().toString());
				//
				//intent.putExtra(BaseBarcodeActivity.EXTRAS_LICENSE_KEY, "1c61089106f282473fbe6a5238ec585f8ca0c29512b2dea3b7c17b8030c9813dc965ca8e70c8557347177515349e6e");
				// Start Activity
				//startActivityForResult(intent, 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this.getActivity().getApplicationContext(), "ERROR:" + e, 1).show();

			}
			break;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.i("SEARCH FRAG", requestCode+"-----------------");
		if (requestCode == 0 && resultCode==BaseBarcodeActivity.RESULT_OK) {
			Pdf417MobiScanData scanData = intent.getParcelableExtra(BaseBarcodeActivity.EXTRAS_RESULT);
			String barcodeType = scanData.getBarcodeType();
			String barcodeData = scanData.getBarcodeData();
			/*if (resultCode == Intent.RESULT_OK) {
				tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
				tvResult.setText(intent.getStringExtra("SCAN_RESULT"));
			} else if (resultCode == RESULT_CANCELED) {
				tvStatus.setText("Press a button to start a scan.");
				tvResult.setText("Scan cancelled.");
			}*/
			Log.i("RESULT", resultCode+" -- "+barcodeData);
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
				text2.setText("Cantidad "+item.getJSONObject("Item").getString("OnHand"));
				new ApiReader(getActivity().getString(R.string.host),"image","servers").new DownloadImageTask(img).execute(item.getJSONObject("Item").getString("Code"));
				img.setOnClickListener(new ImageClick(item, (MainActivity)getActivity()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return v;
		}
	}
}
