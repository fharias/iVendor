package com.fabioarias;

import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
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
public class VitrinaCompra extends CustomActivity {

	private JSONObject item = null;
	private EditText codigoVendedor = null;
	private EditText numeroBoleta = null;
	private TextView codigo = null;
	private TextView codigo_lbl = null;
	private String IMEI = null;

	public VitrinaCompra(JSONObject item) {
		this.item = item;
	}

	public VitrinaCompra() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vitrina_info);
		Bundle b = getIntent().getExtras();
		try {
			item = new JSONObject(b.getString("item"));
			ImageView img = (ImageView) this.findViewById(R.id.productoImage);
			TextView price = (TextView) this.findViewById(R.id.price);
			TextView description = (TextView) this
					.findViewById(R.id.description);
			new ApiReader("http://winbugs.cloudapp.net/vendormachine", "image",
					"servers").new DownloadImageTask(img).execute(item
					.getJSONObject("Item").getString("Code"), "400", "400");
			price.setText("CLP$ "
					+ item.getJSONObject("Item").getString("Cost"));
			description.setText(item.getJSONObject("Item").getString(
					"Description1"));
			ImageButton buyButton = (ImageButton) this.findViewById(R.id.buy);

			buyButton.setClickable(true);
			buyButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (codigoVendedor.getText().toString().trim().length() > 0) {
						if (numeroBoleta.getText().toString().trim().length() > 3) {
							try {
								TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
								IMEI = manager.getDeviceId();
								Log.e("IMEI", IMEI);
							} catch (Exception e) {
								Class<?> c;
								try {
									c = Class
											.forName("android.os.SystemProperties");
									Method get = c.getMethod("get",
											String.class, String.class);
									IMEI = (String) (get.invoke(c,
											"ro.serialno", "unknown"));
								} catch (Exception e1) {
									// TODO Auto-generated catch block

								}

							}
							ApiReader reader;
							try {
								reader = new ApiReader(
										"http://winbugs.cloudapp.net/vendormachine",
										"job/"
												+ item.getJSONObject("Item")
														.getString("Code")
												+ "/"
												+ codigoVendedor.getText()
														.toString()
												+ "/"
												+ numeroBoleta.getText()
														.toString() + "/"
												+ IMEI, "servers");
								JSONObject response = reader.getItem();
								if (response != null) {
									if (response.getString("state")
											.equals("OK")) {
										Toast.makeText(
												getApplicationContext(),
												"TRANSACCION EXITOSA SU CODIGO DE VENDING ES "
														+ response
																.getString("codigo"),
												1).show();
										codigo_lbl.setVisibility(View.VISIBLE);
										codigo.setVisibility(View.VISIBLE);
										codigo.setText(response
												.getString("codigo"));
									} else {
										Toast.makeText(getApplicationContext(),
												response.getString("message"),
												1).show();
									}
								} else {
									Toast.makeText(
											getApplicationContext(),
											"NO SE RECIBIO RESPUESTA DEL SERVIDOR",
											1).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							Toast.makeText(getApplicationContext(),
									"DEBE INGRESAR UN NUMERO DE BOLETA VALIDO",
									1).show();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"DEBE INGRESAR UN CODIGO DE VENDEDOR VALIDO", 1)
								.show();
					}

				}

			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Set the action bar title text.
	 */
	@SuppressWarnings("unused")
	private void setActionBarTitle() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 0)
			return;
		String title = getSupportFragmentManager().getBackStackEntryAt(
				getSupportFragmentManager().getBackStackEntryCount() - 1)
				.getName();
		getActionBar().setTitle(title);
	}
}
