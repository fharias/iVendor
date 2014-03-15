package com.fabioarias.ui;

import java.text.DecimalFormat;
import java.util.List;

import mobi.pdf417.Pdf417MobiSettings;
import mobi.pdf417.activity.Pdf417ScanActivity;
import net.photopay.base.BaseBarcodeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fabioarias.CustomScanActivity;
import com.fabioarias.MainActivity;
import com.fabioarias.R;
import com.fabioarias.custom.CustomFragment;
import com.fabioarias.net.ApiReader;
import com.fabioarias.utils.BarcodeUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * The Class Store is the Fragment class that is launched when the user clicks
 * on Store button in Left navigation drawer.
 */
public class Store extends CustomFragment {

	private Double total = 0d;
	protected ApiReader reader;
	protected EditText codigoVendedor = null;
	protected EditText codigoCajero = null;
	protected EditText numeroBoleta = null;
	protected TextView codigoJob = null;
	protected ImageView imgJob = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		if (((MainActivity) getActivity()).getCartSize() > 0) {
			v = inflater.inflate(R.layout.store, null);
			setupViewComponents(v);
		} else {
			if (((MainActivity) getActivity()).getLastpurchase().length() == 0) {
				v = inflater.inflate(R.layout.cart_empty, null);
			} else {
				v = inflater.inflate(R.layout.job, null);
				ListView grid = (ListView) v.findViewById(R.id.list);
				imgJob = (ImageView) v.findViewById(R.id.qrJobCode);

				grid.setAdapter(new JobAdapter(((MainActivity) getActivity())
						.getLastpurchase()));
			}
		}

		return v;
	}

	/**
	 * This method is called to initiate the view components and to apply
	 * listeners to view components.
	 * 
	 * @param v
	 *            the container view
	 */
	private void setupViewComponents(View v) {
		ListView grid = (ListView) v.findViewById(R.id.list);
		Button checkout = (Button) v.findViewById(R.id.checkout);
		Button cancelar = (Button) v.findViewById(R.id.cancelar);
		ImageButton scanInvoice = (ImageButton) v
				.findViewById(R.id.scanButton_1);
		ImageButton scanVendor = (ImageButton) v
				.findViewById(R.id.scanButton_2);
		scanVendor.setVisibility(View.INVISIBLE);
		ImageButton scanCashier = (ImageButton) v
				.findViewById(R.id.scanButton_3);
		scanCashier.setVisibility(View.INVISIBLE);
		codigoVendedor = (EditText) v.findViewById(R.id.codigoVendedor);
		codigoVendedor.setVisibility(View.INVISIBLE);
		codigoCajero = (EditText) v.findViewById(R.id.codigoCajero);
		codigoCajero.setVisibility(View.INVISIBLE);
		numeroBoleta = (EditText) v.findViewById(R.id.numeroBoleta);
		if(((MainActivity)getActivity()).getFactura()!=null){
			numeroBoleta.setText(((MainActivity)getActivity()).getFactura().getNumero());
		}
		if(((MainActivity)getActivity()).getStore_codigo_vendedor() != null){
			codigoVendedor.setText(((MainActivity)getActivity()).getStore_codigo_vendedor());
			
		}
		if(((MainActivity)getActivity()).getStore_codigo_cajero() != null){
			codigoCajero.setText(((MainActivity)getActivity()).getStore_codigo_cajero());
		}
		setTouchNClick(cancelar);
		setTouchNClick(checkout);
		setTouchNClick(scanInvoice);
		setTouchNClick(scanVendor);
		setTouchNClick(scanCashier);
		grid.setAdapter(new StoreAdapter(((MainActivity) getActivity())
				.getCart()));
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.scanButton_1:
			Log.i("scan_invoice", "SCAN_INVOICE");
			// SCANING BARCODE
			try {
				Pdf417MobiSettings sett = new Pdf417MobiSettings();
				//sett.setNullQuietZoneAllowed(true);
				//sett.setAll1DBarcodesEnabled(true);
				//sett.setAll2DBarcodesEnabled(true);
				sett.setDontShowDialog(true);
				Log.i("SEARCH", ((MainActivity) getActivity()).getCart()
						.toString());
				Bundle b = new Bundle();

				// sett.setRemoveOverlayEnabled(true);
				Intent intent = new Intent(getActivity(),
						Pdf417ScanActivity.class);
				intent.putExtra(BaseBarcodeActivity.EXTRAS_SETTINGS, sett);
				// intent.putExtra(BaseBarcodeActivity.EXTRAS_LICENSE_KEY,
				// "1c61089106f282473fbe6a5238ec585f8ca0c29512b2dea3b7c17b8030c9813dc965ca8e70c8557347177515349e6e");
				// Start Activity
				startActivityForResult(intent, 40000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this.getActivity().getApplicationContext(),
						"ERROR:" + e, 1).show();

			}
			break;
		case R.id.scanButton_2:
			Log.i("scan_vendor", "SCAN_VENDOR");
			// SCANING BARCODE
			try {
				Pdf417MobiSettings sett = new Pdf417MobiSettings();
				//sett.setNullQuietZoneAllowed(true);
				//sett.setAll1DBarcodesEnabled(true);
				//sett.setAll2DBarcodesEnabled(true);
				sett.setDontShowDialog(true);
				Log.i("SEARCH", ((MainActivity) getActivity()).getCart()
						.toString());
				Bundle b = new Bundle();

				// sett.setRemoveOverlayEnabled(true);
				Intent intent = new Intent(getActivity(),
						Pdf417ScanActivity.class);
				intent.putExtra(BaseBarcodeActivity.EXTRAS_SETTINGS, sett);
				// intent.putExtra(BaseBarcodeActivity.EXTRAS_LICENSE_KEY,
				// "1c61089106f282473fbe6a5238ec585f8ca0c29512b2dea3b7c17b8030c9813dc965ca8e70c8557347177515349e6e");
				// Start Activity
				((MainActivity)getActivity()).setStore_pos(1);
				startActivityForResult(intent, 40002);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this.getActivity().getApplicationContext(),
						"ERROR:" + e, 1).show();

			}
			break;
		case R.id.scanButton_3:
			Log.i("scan_cashier", "SCAN_CASHIER");
			// SCANING BARCODE
			try {
				Pdf417MobiSettings sett = new Pdf417MobiSettings();
				//sett.setNullQuietZoneAllowed(true);
				//sett.setAll1DBarcodesEnabled(true);
				//sett.setAll2DBarcodesEnabled(true);
				sett.setDontShowDialog(true);
				Log.i("SEARCH", ((MainActivity) getActivity()).getCart()
						.toString());
				Bundle b = new Bundle();

				// sett.setRemoveOverlayEnabled(true);
				Intent intent = new Intent(getActivity(),
						Pdf417ScanActivity.class);
				intent.putExtra(BaseBarcodeActivity.EXTRAS_SETTINGS, sett);
				// intent.putExtra(BaseBarcodeActivity.EXTRAS_LICENSE_KEY,
				// "1c61089106f282473fbe6a5238ec585f8ca0c29512b2dea3b7c17b8030c9813dc965ca8e70c8557347177515349e6e");
				// Start Activity
				((MainActivity)getActivity()).setStore_pos(2);
				startActivityForResult(intent, 40003);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this.getActivity().getApplicationContext(),
						"ERROR:" + e, 1).show();

			}
			break;
		case R.id.checkout:
			try {
				if (codigoVendedor.getText().toString().length() > 3) {
					if (codigoCajero.getText().toString().length() > 2) {
						if (numeroBoleta.getText().toString().length() > 5) {
							new AlertDialog.Builder(this.getActivity())
									.setIcon(android.R.drawable.ic_dialog_info)
									.setTitle("Realizar Compra")
									.setMessage(
											"Esta seguro de realizar la compra?")
									.setPositiveButton(
											"Si",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													reader = new ApiReader(
															getActivity().getString(R.string.host),
															"checkout/"
																	+ ((MainActivity) getActivity())
																			.getIMEI()
																	+ "/"
																	+ codigoVendedor
																			.getText()
																			.toString()
																	+ "/"
																	+ numeroBoleta
																			.getText()
																			.toString()
																	+ "/"
																	+ codigoCajero
																			.getText()
																			.toString(),
															"carts");
													JSONObject response = reader
															.getItem();
													if (response != null) {
														try {
															if (response
																	.getString(
																			"state")
																	.equals("OK")) {
																Toast.makeText(
																		getActivity()
																				.getApplicationContext(),
																		"TRANSACCION EXITOSA SU CODIGO DE VENDING ES "
																				+ response
																						.getString("codigo"),
																		1)
																		.show();

															} else {
																Toast.makeText(
																		getActivity()
																				.getApplicationContext(),
																		response.getString("message"),
																		1)
																		.show();
															}
														} catch (JSONException e) {
															// TODO
															// Auto-generated
															// catch block
															e.printStackTrace();
														}
													} else {
														Toast.makeText(
																getActivity()
																		.getApplicationContext(),
																"NO SE RECIBIO RESPUESTA DEL SERVIDOR",
																1).show();
													}
													((MainActivity) getActivity())
															.recreate();
												}

											}).setNegativeButton("No", null)
									.show();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"DEBE INGRESAR UN NUMERO DE BOLETA VALIDO, LONGITUD MINIMA 6 DIGITOS",
									1).show();
						}
					} else {
						Toast.makeText(
								getActivity().getApplicationContext(),
								"DEBE INGRESAR UN NUMERO DE CAJERO VALIDO, LONGITUD MINIMA 3 DIGITOS",
								1).show();
					}
				} else {
					Toast.makeText(
							getActivity().getApplicationContext(),
							"DEBE INGRESAR UN NUMERO DE VENDEDOR VALIDO, LONGITUD MINIMA 4 DIGITOS",
							1).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.cancelar:
			try {
				new AlertDialog.Builder(this.getActivity())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("Cancelar Compra")
						.setMessage("Esta seguro de cancelar la compra?")
						.setPositiveButton("Si",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										reader = new ApiReader(
												getActivity().getString(R.string.host),
												"cancel/"
														+ ((MainActivity) getActivity())
																.getIMEI(),
												"carts");
										JSONObject cc = reader.getItem();
										((MainActivity) getActivity())
												.recreate();
									}

								}).setNegativeButton("No", null).show();
			} catch (Exception e) {

			}
			break;
		}
	}

	/**
	 * The Class StoreAdapter is the adapter for displaying Products in
	 * GridView. The current implementation simply display dummy product images.
	 * You need to change it as per your needs.
	 */
	private class StoreAdapter extends BaseAdapter {
		private JSONArray cart = null;
		private TextView totalView = null;
		private double total = 0d;

		public StoreAdapter(JSONArray cart) {
			this.total = 0;
			this.cart = cart;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			if (cart != null)
				return cart.length();
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int arg0) {
			try {
				return cart.get(arg0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2) {
			if (v == null)
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.store_item, null);

			TextView lbl = (TextView) v.findViewById(R.id.lbl1);
			try {
				lbl.setText(cart.getJSONObject(pos).getJSONObject("Item")
						.getString("Description1"));
				lbl = (TextView) v.findViewById(R.id.lbl2);
				lbl.setText("Cantidad "
						+ cart.getJSONObject(pos).getJSONObject("Item")
								.getString("Cantidad"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return v;
		}

	}

	/**
	 * The Class StoreAdapter is the adapter for displaying Products in
	 * GridView. The current implementation simply display dummy product images.
	 * You need to change it as per your needs.
	 */
	private class JobAdapter extends BaseAdapter {
		private JSONArray cart = null;
		private TextView totalView = null;
		private double total = 0d;

		public JobAdapter(JSONArray cart) {
			this.total = 0;
			this.cart = cart;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			if (cart != null)
				return cart.length();
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int arg0) {
			try {
				return cart.get(arg0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2) {
			if (v == null)
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.store_item, null);

			TextView lbl = (TextView) v.findViewById(R.id.lbl1);
			try {
				if (pos == 0) {
					imgJob.setImageBitmap(BarcodeUtil.encodeBarcode(cart
							.getJSONObject(pos).getJSONObject("Item")
							.getString("Job"), BarcodeFormat.PDF_417, 200, 350));
				}
				lbl.setText(cart.getJSONObject(pos).getJSONObject("Item")
						.getString("Description1"));
				lbl = (TextView) v.findViewById(R.id.lbl2);
				lbl.setText("Cantidad "
						+ cart.getJSONObject(pos).getJSONObject("Item")
								.getString("Cantidad"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return v;
		}

	}

	/**
	 * The Class ImgAdapter is the Adapter class for ViewPager to provide image
	 * sliding feature.
	 */
	private class ImgAdapter extends FragmentStatePagerAdapter {

		/**
		 * Instantiates a new img adapter.
		 * 
		 * @param fm
		 *            the fm
		 */
		public ImgAdapter(FragmentManager fm) {
			super(fm);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int pos) {
			Bundle b = new Bundle();
			if (pos == 0)
				b.putInt("img", R.drawable.store_img1);
			else if (pos == 1)
				b.putInt("img", R.drawable.store_img2);
			else if (pos == 2)
				b.putInt("img", R.drawable.store_img3);
			ImgFragment i = new ImgFragment();
			i.setArguments(b);
			return i;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			return 3;
		}

	}

}
