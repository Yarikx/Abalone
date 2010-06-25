package org.kpi;

import mechanics.Ann;
import mechanics.Board;
import mechanics.ClassicLayout;
import mechanics.Game;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
	private BoardView bw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bw = (BoardView) findViewById(R.id.boardView);
		// bw.drawBoard(board);
		final Game game = new Game(new ClassicLayout(), Board.BLACK, bw,
				new Ann(), bw);
		bw.setGame(game);
		//bw.drawBoard(game.getBoard());
		(new Thread(new Runnable() {

			@Override
			public void run() {
				
				game.start();
			}
		},"Game Thread")).start();
		// game.start();

	}
	
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		bw.screenChanged();
		return super.onRetainNonConfigurationInstance();
	}
}
