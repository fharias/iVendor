package com.fabioarias.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.fabioarias.R;
import com.fabioarias.custom.CustomFragment;

// TODO: Auto-generated Javadoc
/**
 * The Class Profile is the Fragment class that is launched when the user clicks
 * on Profile button in Left navigation drawer.
 */
public class Profile extends CustomFragment
{

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.profile, null);

		setTouchNClick(v.findViewById(R.id.p1));
		setTouchNClick(v.findViewById(R.id.p2));
		setTouchNClick(v.findViewById(R.id.p3));

		GridView grid = (GridView) v.findViewById(R.id.grid);
		grid.setAdapter(new GridAdapter());
		return v;
	}

	/* (non-Javadoc)
	 * @see com.taptag.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.p3)
			getFragmentManager().beginTransaction()
					.replace(R.id.content_frame, new MainFragment())
					.addToBackStack("Elements").commit();
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
		public View getView(int arg0, View v, ViewGroup arg2)
		{
			if (v == null)
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.profile_item, null);

			return v;
		}

	}
}
