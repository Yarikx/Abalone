package org.kpi;

import mechanics.Board;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Board board = new Board();
		BoardView bw = (BoardView) findViewById(R.id.boardView);
		bw.drawBoard(board);
	}
}
