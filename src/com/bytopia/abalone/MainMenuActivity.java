package com.bytopia.abalone;

import com.bytopia.abalone.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

				Intent intent = new Intent("com.bytopia.abalone.GAME");
				intent.putExtra("vs", "human");
				intent.putExtra("type", "new");
				startActivity(intent);

			}
		});

		Button newGameButtonCpu = (Button) findViewById(R.id.n_game_cpu);
		newGameButtonCpu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent("com.bytopia.abalone.GAME");
				// intent.getExtras().putString("side", "BLACK");
				intent.putExtra("vs", "cpu");
				intent.putExtra("type", "new");
				startActivity(intent);

			}
		});

		Button resumeButton = (Button) findViewById(R.id.resume_game);
		resumeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent("com.bytopia.abalone.RESUMEGAME");
				intent.putExtra("type", "resume");
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.rules_menu:
			Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.rules);
			dialog.setTitle(getString(R.string.rules_title));
			dialog.show();
			break;
		}

		return true;
	}

	static final int RULESDIALOG = 1;

}
