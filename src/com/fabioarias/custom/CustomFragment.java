package com.fabioarias.custom;

import com.fabioarias.R;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * The Class CustomFragment is the base Fragment class. You can extend your
 * Fragment classes with this class in case you want to apply common set of
 * rules for those Fragments.
 */
public class CustomFragment extends Fragment implements OnClickListener
{

	/**
	 * Set the touch and click listener for a View
	 * 
	 * @param v
	 *            the view
	 * @return the same view
	 */
	public View setTouchNClick(View v)
	{

		v.setOnClickListener(this);
		v.setOnTouchListener(CustomActivity.TOUCH);
		return v;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		
		switch(v.getId()){
		case R.id.p1:
			Log.e("ONCLICK", "por codigo");
			break;
		case R.id.p2:
			Log.e("ONCLICK", "POR SCAN");
			break;
		}
	}

}
