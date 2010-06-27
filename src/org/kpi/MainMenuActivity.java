package org.kpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu);
		Button newGameButtonHuman = (Button) findViewById(R.id.n_game_human);
		newGameButtonHuman.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent("org.kpi.abalone.GAME");
				//intent.getExtras().putString("side", "BLACK");
				intent.putExtra("vs", "human");
				startActivity(intent);

			}
		});
		
		Button newGameButtonCpu = (Button) findViewById(R.id.n_game_cpu);
		newGameButtonCpu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent("org.kpi.abalone.GAME");
				//intent.getExtras().putString("side", "BLACK");
				intent.putExtra("vs", "cpu");
				startActivity(intent);

			}
		});
		
		
	}
}
