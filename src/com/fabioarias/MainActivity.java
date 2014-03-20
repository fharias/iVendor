package com.fabioarias;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import mobi.pdf417.Pdf417MobiScanData;
import net.photopay.base.BaseBarcodeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fabioarias.custom.CustomActivity;
import com.fabioarias.model.Data;
import com.fabioarias.model.FacturaDTO;
import com.fabioarias.net.ApiReader;
import com.fabioarias.ui.Categories;
import com.fabioarias.ui.LeftNavAdapter;
import com.fabioarias.ui.Profile;
import com.fabioarias.ui.Search;
import com.fabioarias.ui.Showcase;
import com.fabioarias.ui.Store;
import com.fabioarias.utils.BarcodeUtil;

/**
 * The Class MainActivity is the base activity class of the application. This
 * activity is launched after the Splash and it holds all the Fragments used in
 * the app. It also creates the Navigation Drawer on left side.
 */
public class MainActivity extends CustomActivity {

	/** The drawer layout. */
	private DrawerLayout drawerLayout;
	private JSONArray cart = null;
	private String IMEI = null;
	private JSONArray lastpurchase = null;
	private static FacturaDTO factura = null;
	private static Integer store_pos = 0;
	private static String store_codigo_vendedor = null;
	private static String store_codigo_cajero = null;

	/** ListView for left side drawer. */
	private ListView drawerLeft;

	/** The drawer toggle. */
	private ActionBarDrawerToggle drawerToggle;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newsfeeder.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getUUID();
		getCart();
		setupDrawer();
		setupContainer();

	}

	public String getIMEI() {
		return IMEI;
	}

	public FacturaDTO getFactura() {
		return factura;
	}

	public String getStore_codigo_vendedor() {
		return store_codigo_vendedor;
	}

	public void setStore_codigo_vendedor(String store_codigo_) {
		store_codigo_vendedor = store_codigo_;
	}

	public String getStore_codigo_cajero() {
		return store_codigo_cajero;
	}

	public void setStore_codigo_cajero(String store_codigo_) {
		store_codigo_cajero = store_codigo_;
	}

	public Integer getStore_pos() {
		return store_pos;
	}

	public void setStore_pos(int store_pos) {
		this.store_pos = store_pos;
	}

	private void getUUID() {
		try {
			TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			IMEI = manager.getDeviceId();
			Log.e("IMEI", IMEI);
		} catch (Exception e) {
			Class<?> c;
			try {
				c = Class.forName("android.os.SystemProperties");
				Method get = c.getMethod("get", String.class, String.class);
				IMEI = (String) (get.invoke(c, "ro.serialno", "unknown"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
			}

		}
	}

	public void setCartItem(JSONObject item) {
		if (cart == null)
			cart = new JSONArray();
		cart.put(item);
	}

	public JSONArray getCart() {
		try {
			ApiReader reader = null;
			reader = new ApiReader(this.getString(R.string.host), "show/"
					+ IMEI, "carts");
			cart = reader.getItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cart;

	}

	public JSONArray getLastpurchase() {
		try {
			ApiReader reader = null;
			reader = new ApiReader(this.getString(R.string.host),
					"lastpurchases/" + IMEI, "carts");
			lastpurchase = reader.getItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastpurchase;

	}

	public int getCartSize() {

		if (cart != null) {
			return cart.length();
		}
		return 0;
	}

	/**
	 * Setup the drawer layout. This method also includes the method calls for
	 * setting up the Left side drawer.
	 */
	private void setupDrawer() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View view) {
				setActionBarTitle();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(R.string.menu1);
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
		drawerLayout.closeDrawers();

		setupLeftNavDrawer();
	}

	/**
	 * Setup the left navigation drawer/slider. You can add your logic to load
	 * the contents to be displayed on the left side drawer. You can also setup
	 * the Header and Footer contents of left drawer if you need them.
	 */
	private void setupLeftNavDrawer() {
		drawerLeft = (ListView) findViewById(R.id.left_drawer);

		drawerLeft.setAdapter(new LeftNavAdapter(this, getDummyLeftNavItems()));
		drawerLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				drawerLayout.closeDrawers();
				launchFragment(pos);
			}
		});
		drawerLayout.openDrawer(drawerLeft);
	}

	/**
	 * This method returns a list of dummy items for left navigation slider. You
	 * can write or replace this method with the actual implementation for list
	 * items.
	 * 
	 * @return the dummy items
	 */
	private ArrayList<Data> getDummyLeftNavItems() {
		ArrayList<Data> al = new ArrayList<Data>();
		al.add(new Data("Carro", null, R.drawable.ic_nav1));
		al.add(new Data("Tienda", null, R.drawable.ic_nav4));
		al.add(new Data("Busqueda", null, R.drawable.ic_nav5));
		return al;
	}

	/**
	 * This method can be used to attach Fragment on activity view for a
	 * particular tab position. You can customise this method as per your need.
	 * 
	 * @param pos
	 *            the position of tab selected.
	 */
	private void launchFragment(int pos) {
		Fragment f = null;
		String title = null;

		switch (pos) {
		case 0:
			title = "Carro ( " + this.getCartSize() + " )";
			f = new Store();

			break;
		case 1:
			title = "Tienda ";
			f = new Showcase();
			break;
		case 2:
			title = "Busqueda";
			f = new Search();
			break;
		}
		if (f != null) {
			while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
				getSupportFragmentManager().popBackStackImmediate();
			}
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, f).addToBackStack(title)
					.commit();
		}
	}

	/**
	 * This method can be used to attach Fragment on activity view for a
	 * particular tab position. You can customize this method as per your need.
	 * 
	 * @param pos
	 *            the position of tab selected.
	 */
	public void launchFragment(Fragment f, String title) {
		drawerLayout.closeDrawers();
		if (f != null) {
			while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
				getSupportFragmentManager().popBackStackImmediate();
			}
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, f).addToBackStack(title)
					.commit();
		}
	}

	/**
	 * Setup the container fragment for drawer layout. The current
	 * implementation of this method simply calls launchFragment method for tab
	 * position 0. You can customize this method as per your need to display
	 * specific content.
	 */
	private void setupContainer() {
		getSupportFragmentManager().addOnBackStackChangedListener(
				new OnBackStackChangedListener() {

					@Override
					public void onBackStackChanged() {
						setActionBarTitle();
					}
				});
		launchFragment(0);
	}

	/**
	 * Set the action bar title text.
	 */
	private void setActionBarTitle() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 0)
			return;
		String title = getSupportFragmentManager().getBackStackEntryAt(
				getSupportFragmentManager().getBackStackEntryCount() - 1)
				.getName();
		getActionBar().setTitle(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.Activity#onConfigurationChanged(android.content.res.Configuration
	 * )
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		drawerToggle.onConfigurationChanged(newConfig);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newsfeeder.custom.CustomActivity#onOptionsItemSelected(android.view
	 * .MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int,
	 * android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
				getSupportFragmentManager().popBackStackImmediate();
			} else
				finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Pdf417MobiScanData scanData = null;
		String barcodeType = null;
		String barcodeData = null;
		Log.i("Activity", "" + requestCode);
		switch (requestCode) {
		case 105536:
			if (resultCode == BaseBarcodeActivity.RESULT_OK) {
				scanData = intent
						.getParcelableExtra(BaseBarcodeActivity.EXTRAS_RESULT);
				barcodeType = scanData.getBarcodeType();
				barcodeData = scanData.getBarcodeData();
				Log.i("RESULT", barcodeType + " -- " + barcodeData);
				if (scanData.getBarcodeType().equals("PDF417")) {
					try {
						factura = BarcodeUtil.getFactura(barcodeData);
					} catch (Exception e) {

					}

				}
				this.launchFragment(new Store(), "Vitrina");
			}
			break;
		case 105538:
		case 105539:
			if (resultCode == BaseBarcodeActivity.RESULT_OK) {
				scanData = intent
						.getParcelableExtra(BaseBarcodeActivity.EXTRAS_RESULT);
				barcodeType = scanData.getBarcodeType();
				barcodeData = scanData.getBarcodeData();
				Log.i("RESULT", barcodeType + " -- " + barcodeData);
				switch (store_pos) {
				case 1:
					store_codigo_vendedor = barcodeData;
					break;
				case 2:
					store_codigo_cajero = barcodeData;
					break;
				}
			}
			this.launchFragment(new Store(), "Vitrina");
			break;
		case 65536:
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Handle successful scan
				Log.i("BARCODE", contents);
				Log.i("BARCODE", format);
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
			if (resultCode == BaseBarcodeActivity.RESULT_OK) {
				scanData = intent
						.getParcelableExtra(BaseBarcodeActivity.EXTRAS_RESULT);
				barcodeType = scanData.getBarcodeType();
				barcodeData = scanData.getBarcodeData();
				Log.i("RESULT", barcodeType + " -- " + barcodeData);
				if (barcodeData != null)
					this.launchFragment(new Search(barcodeData), "Buscar");
			}
			break;
		}
	}
}
