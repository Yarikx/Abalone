package com.bytopia.abalone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mechanics.Ann;
import mechanics.Board;
import mechanics.ClassicLayout;
import mechanics.Game;
import mechanics.Player;
import mechanics.Side;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

public class GameActivity extends Activity {
	private BoardView bw;
	private Game game;
	private static final String FILE_NAME = "gamedump.bin";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Intent intent = getIntent();

		setContentView(R.layout.main);
		bw = (BoardView) findViewById(R.id.boardView);
		bw.setParent(this);

		if (intent.getAction().equals("com.bytopia.abalone.GAME")) {

			String sp = intent.getExtras().getString("vs");
			
			Player secondPlayer = sp.equals("cpu")?(new Ann()):bw;
			game = new Game(new ClassicLayout(), Side.BLACK, bw,
					secondPlayer, bw, sp.equals("cpu") ? Game.CPU : Game.HUMAN);
			startGame();
		} else if (intent.getAction().equals("com.bytopia.abalone.RESUMEGAME")) {
			Log.d("state", "resumeing");
			try {
				FileInputStream fis = openFileInput(FILE_NAME);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Board board = (Board) ois.readObject();
				byte side = ois.readByte();
				byte vsType = ois.readByte();
				Player secondPlayer = (vsType == Game.HUMAN) ? bw : (new Ann());
				game = new Game(board, side, bw, secondPlayer, bw,vsType);
				byte n = ois.readByte();
				//bw.ballSizeRecalc();
				game.getBoard().setBlackCaptured(n);
				
				for(int i =1;i<=n;i++){
					ballCaptured(Side.BLACK);
				}
				n = ois.readByte();
				game.getBoard().setWhiteCaptured(n);
				for(int i =1;i<=n;i++){
					ballCaptured(Side.WHITE);
				}
				
				startGame();
				

			} catch (Exception e) {
				Log.d("state", "Error");
				finish();
			}
		}

		super.onCreate(savedInstanceState);

	}

	private void startGame() {
		bw.setGame(game);
		// bw.drawBoard(game.getBoard());
		(new Thread(new Runnable() {

			@Override
			public void run() {

				game.start();
			}
		}, "Game Thread")).start();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		bw.screenChanged();
		return super.onRetainNonConfigurationInstance();
	}

	@Override
	protected void onPause() {
		Log.d("state", "paused");

		// Create a new output file stream that’s private to this application.
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME,
					Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(game.getBoard());
			oos.writeByte(game.getCurrentSide());
			oos.writeByte(game.getVsType());
			oos.writeByte(game.getBoard().getMarblesCaptured(Board.BLACK));
			oos.writeByte(game.getBoard().getMarblesCaptured(Board.WHITE));
			oos.close();

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		super.onPause();
	}

	public void ballCaptured(final byte side) {
		Runnable runnable = new Runnable() {

			private static final int DEFBALLSIZE=34;

			@Override
			public void run() {
				LinearLayout ll1 = (LinearLayout) findViewById(R.id.top_balls);
				LinearLayout ll2 = (LinearLayout) findViewById(R.id.bottom_balls);
				ImageView iw = new ImageView(bw.getContext());
				iw.setImageResource((side == Board.BLACK) ? R.drawable.black_ball
						: R.drawable.white_ball);
				iw.setAdjustViewBounds(true);
				iw.setMaxHeight(DEFBALLSIZE);
				iw.setMaxWidth(DEFBALLSIZE);
				iw.setScaleType(ScaleType.CENTER_INSIDE);
				if (side == Board.BLACK) {
					ll1.addView(iw);
				} else {
					ll2.addView(iw);
				}

			}
		};

		runOnUiThread(runnable);

	}

}
