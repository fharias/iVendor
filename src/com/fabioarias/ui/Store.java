package com.fabioarias.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fabioarias.R;
import com.fabioarias.custom.CustomFragment;

/**
 * The Class Store is the Fragment class that is launched when the user
 * clicks on Store button in Left navigation drawer.
 */
public class Store extends CustomFragment
{

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.store, null);

		setupViewComponents(v);
		return v;
	}

	/**
	 * This method is called to initiate the view components and to apply listeners to view components.
	 * 
	 * @param v
	 *            the container view
	 */
	private void setupViewComponents(View v)
	{
		ListView grid = (ListView) v.findViewById(R.id.list);
		grid.setAdapter(new StoreAdapter());

		ViewPager pager = (ViewPager) v.findViewById(R.id.pager);
		pager.setAdapter(new ImgAdapter(getFragmentManager()));

		final ImageView dot1 = (ImageView) v.findViewById(R.id.dot1);
		final ImageView dot2 = (ImageView) v.findViewById(R.id.dot2);
		final ImageView dot3 = (ImageView) v.findViewById(R.id.dot3);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pos)
			{
				dot1.setImageResource(R.drawable.dot_unselected);
				dot2.setImageResource(R.drawable.dot_unselected);
				dot3.setImageResource(R.drawable.dot_unselected);
				if (pos == 0)
					dot1.setImageResource(R.drawable.dot_selected);
				else if (pos == 1)
					dot2.setImageResource(R.drawable.dot_selected);
				else if (pos == 2)
					dot3.setImageResource(R.drawable.dot_selected);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});
	}

	/**
	 * The Class StoreAdapter is the adapter for displaying Products in GridView.
	 * The current implementation simply display dummy product images. You need
	 * to change it as per your needs.
	 */
	private class StoreAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return 20;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int arg0)
		{
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
						R.layout.store_item, null);

			TextView lbl = (TextView) v.findViewById(R.id.lbl1);
			lbl.setText("Store " + (pos + 1));

			lbl = (TextView) v.findViewById(R.id.lbl2);
			lbl.setText((pos + 1) * 100 + " likes, " + (pos + 1) * 10
					+ " products");

			return v;
		}

	}

	/**
	 * The Class ImgAdapter is the Adapter class for ViewPager to provide image sliding feature.
	 */
	private class ImgAdapter extends FragmentStatePagerAdapter
	{

		/**
		 * Instantiates a new img adapter.
		 * 
		 * @param fm
		 *            the fm
		 */
		public ImgAdapter(FragmentManager fm)
		{
			super(fm);
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int pos)
		{
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

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return 3;
		}

	}

}
