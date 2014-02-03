package com.fabioarias;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fabioarias.R;
import com.fabioarias.custom.CustomActivity;
import com.fabioarias.custom.CustomFragment;
import com.fabioarias.net.ApiReader;
import com.fabioarias.net.ApiReader.DownloadImageTask;

@SuppressLint("ValidFragment")
public class VitrinaCompra extends CustomActivity{
	
	private JSONObject item = null;
	
	public VitrinaCompra(JSONObject item){
		this.item = item;
	}
	
	public VitrinaCompra(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vitrina_info);
		Bundle b = getIntent().getExtras();
		try {
			item = new JSONObject(b.getString("item"));
			ImageView img = (ImageView) this.findViewById(R.id.productoImage);
			TextView price = (TextView)this.findViewById(R.id.price);
			TextView description = (TextView)this.findViewById(R.id.description);
			new ApiReader("http://winbugs.cloudapp.net/vendormachine","image","servers").new DownloadImageTask(img).execute(item.getJSONObject("Item").getString("Code"), "400", "400");
			price.setText(item.getJSONObject("Item").getString("Cost"));
			description.setText(item.getJSONObject("Item").getString("Description1"));
			ImageButton buyButton = (ImageButton)this.findViewById(R.id.buy);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
