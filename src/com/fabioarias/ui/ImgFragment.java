package com.fabioarias.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fabioarias.R;

/**
 * The Class ImgFragment is simple Fragment that holds a Single ImageView and it
 * is used as an Item fragment in Fragment pager adapter.
 */
public class ImgFragment extends Fragment
{

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		ImageView v = (ImageView) inflater.inflate(R.layout.imageview, null);
		v.setImageResource(getArguments().getInt("img"));
		return v;
	}
}
