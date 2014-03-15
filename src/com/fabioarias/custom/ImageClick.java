package com.fabioarias.custom;

import org.json.JSONException;
import org.json.JSONObject;

import com.fabioarias.MainActivity;
import com.fabioarias.R;
import com.fabioarias.VitrinaCompra;
import com.fabioarias.ui.Cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ImageClick implements OnClickListener{
	private JSONObject item = null;
	private MainActivity c= null;
	public ImageClick(JSONObject item, MainActivity c){
		this.c = c;
		this.item = item;
	}
	@Override
	public void onClick(View v) {
		try {
			
			c.launchFragment(new Cart(item), "Carrito");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
