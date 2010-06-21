package org.kpi;

import mechanics.Ann;
import mechanics.Board;
import mechanics.ClassicLayout;
import mechanics.Game;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Board board = new Board();
		final BoardView bw = (BoardView) findViewById(R.id.boardView);
		// bw.drawBoard(board);
		final Game game = new Game(new ClassicLayout(), Board.BLACK, bw,
				new Ann(), bw);
		bw.drawBoard(game.getBoard());
		(new Thread(new Runnable() {

			@Override
			public void run() {
				
				game.start();
			}
		})).start();
		// game.start();

	}
}
