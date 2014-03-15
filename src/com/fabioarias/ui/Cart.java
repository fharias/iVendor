 package com.fabioarias.ui;

import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fabioarias.MainActivity;
import com.fabioarias.R;
import com.fabioarias.custom.CustomFragment;
import com.fabioarias.net.ApiReader;
import com.fabioarias.net.ApiReader.DownloadImageTask;


@SuppressLint("ValidFragment")
public class Cart extends CustomFragment {
	
	private JSONObject item = null;
	private EditText codigoVendedor = null;
	private EditText numeroBoleta = null;
	private TextView codigo = null;
	private TextView codigo_lbl = null;
	private String IMEI = null;
	
	public Cart(){
		
	}
	
	public Cart(JSONObject item){
		this.item = item;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.vitrina_info, null);
		ImageView img = (ImageView) v.findViewById(R.id.productoImage);
		TextView price = (TextView)v.findViewById(R.id.price);
		TextView description = (TextView)v.findViewById(R.id.description);
		try {
			new ApiReader(getActivity().getString(R.string.host),"image","servers").new DownloadImageTask(img).execute(item.getJSONObject("Item").getString("Code"), "400", "400");
			price.setText("Stock "+item.getJSONObject("Item").getString("OnHand"));
			description.setText(item.getJSONObject("Item").getString("Description1"));
			ImageButton buyButton = (ImageButton)v.findViewById(R.id.buy);
			buyButton.setClickable(true);
			buyButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					((MainActivity)getActivity()).setCartItem(item);
					ApiReader reader;
					try{
						reader = new ApiReader(getActivity().getString(R.string.host),"add/"+((MainActivity)getActivity()).getIMEI()+"/"+item.getJSONObject("Item").getString("Code"),"carts");
						JSONObject response = reader.getItem();
						if(response != null){
							if(response.getString("state").equals("OK")){
								Toast.makeText(getActivity().getApplicationContext(), "AGREGADO AL CARRO", 1).show();
							}else{
								Toast.makeText(getActivity().getApplicationContext(), response.getString("message"), 1).show();
							}
						}else{
							Toast.makeText(getActivity().getApplicationContext(), "NO SE RECIBIO RESPUESTA DEL SERVIDOR", 1).show();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					((MainActivity)getActivity()).launchFragment(new Store(), "Carro ");
				}
				
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}
}
