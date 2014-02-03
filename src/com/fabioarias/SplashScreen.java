package com.fabioarias;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SplashScreen will launched at the start of the application. It will
 * be displayed for 3 seconds and than finished automatically and it will also
 * start the next activity of app.
 */
public class SplashScreen extends Activity
{

	/** Check if the app is running. */
	private boolean isRunning;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		isRunning = true;

		startSplash();

	}

	/**
	 * Starts the count down timer for 3-seconds. It simply sleeps the thread
	 * for 3-seconds.
	 */
	private void startSplash()
	{

		new Thread(new Runnable() {
			@Override
			public void run()
			{

				try
				{

					Thread.sleep(3000);

				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					runOnUiThread(new Runnable() {
						@Override
						public void run()
						{
							doFinish();
						}
					});
				}
			}
		}).start();
	}

	/**
	 * If the app is still running than this method will start the MainActivity
	 * and finish the Splash.
	 */
	private synchronized void doFinish()
	{

		if (isRunning)
		{
			isRunning = false;
			Intent i = new Intent(SplashScreen.this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			isRunning = false;
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}