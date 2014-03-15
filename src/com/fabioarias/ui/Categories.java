package com.fabioarias.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabioarias.R;
import com.fabioarias.custom.CustomFragment;
import com.fabioarias.model.Data;

/**
 * The Class Categories is the Fragment class that is launched when the user
 * clicks on Category button in Left navigation drawer.
 */
public class Categories extends CustomFragment
{

	/** The category list. */
	private ArrayList<Data> catList;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.categories, null);

		loadCategories();
		GridView grid = (GridView) v;
		grid.setAdapter(new GridAdapter());
		return v;
	}

	/**
	 * This method currently loads a dummy list of categories. You can write the
	 * actual implementation of loading categories.
	 */
	private void loadCategories()
	{
		catList = new ArrayList<Data>();
		catList.add(new Data("Women", null, R.drawable.cat1));
		catList.add(new Data("Man", null, R.drawable.cat2));
		catList.add(new Data("Baby", null, R.drawable.cat3));
		catList.add(new Data("Travel", null, R.drawable.cat4));
		catList.add(new Data("Tech", null, R.drawable.cat5));
		catList.add(new Data("Food&Drink", null, R.drawable.cat6));
		catList.add(new Data("Cat 7", null, R.drawable.cat1));
		catList.add(new Data("Cat 8", null, R.drawable.cat2));
		catList.add(new Data("Cat 9", null, R.drawable.cat3));
		catList.add(new Data("Cat 10", null, R.drawable.cat4));
		
		
	}

	/**
	 * The Class GridAdapter is the adapter class for Category GridView. The
	 * currently implementation of this adapter simply display static dummy
	 * images. You need to write the code for loading actual images.
	 */
	private class GridAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return catList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Data getItem(int arg0)
		{
			return catList.get(arg0);
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
						R.layout.cat_item, null);

			if (pos % 8 == 0)
				v.setBackgroundResource(R.drawable.cat_block1);
			else if ((pos - 1) % 8 == 0)
				v.setBackgroundResource(R.drawable.cat_block2);
			else if ((pos - 2) % 8 == 0)
				v.setBackgroundResource(R.drawable.cat_block3);
			else if ((pos - 3) % 8 == 0)
				v.setBackgroundResource(R.drawable.cat_block4);
			else if ((pos - 4) % 8 == 0)
				v.setBackgroundResource(R.drawable.cat_block5);
			else if ((pos - 5) % 8 == 0)
				v.setBackgroundResource(R.drawable.cat_block6);
			else if ((pos - 6) % 8 == 0)
				v.setBackgroundResource(R.drawable.cat_block7);
			else if ((pos - 7) % 8 == 0)
				v.setBackgroundResource(R.drawable.cat_block8);

			TextView lbl = (TextView) v.findViewById(R.id.lbl);
			lbl.setText(getItem(pos).getTitle());

			ImageView img = (ImageView) v.findViewById(R.id.img);
			img.setImageResource(getItem(pos).getImage());
			return v;
		}

	}
}
