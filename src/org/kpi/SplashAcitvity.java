package org.kpi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class SplashAcitvity extends Activity{
	long m_dwSplashTime = 3000;
	boolean m_bPaused = false;

	boolean m_bSplashActive = true;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// Draw the splash screen
		setContentView(R.layout.splash);
		// Very simple timer thread
		Thread splashTimer = new Thread() {
			public void run() {
				try {
					// Wait loop
					long ms = 0;
					while (m_bSplashActive && ms < m_dwSplashTime) {
						sleep(100);
						// Advance the timer only if we're running.
						if (!m_bPaused)
							ms += 100;
					}
					// Advance to the next screen.
					startActivity(new Intent(
							"org.kpi.abalone.MAINMENU"));

				} catch (Exception e) {
					Log.e("Splash", e.toString());
				} finally {
					finish();
				}
			}
		};
		splashTimer.start();
	}

	protected void onPause() {
		super.onPause();
		m_bPaused = true;
	}
	
	protected void onResume()
	{
	    super.onResume();
	    m_bPaused = false;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    //if we get any key, clear the splash screen
	    super.onKeyDown(keyCode, event);
	    m_bSplashActive = false;
	    return true;
	}
}
