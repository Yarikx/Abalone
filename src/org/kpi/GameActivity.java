package org.kpi;

import mechanics.Ann;
import mechanics.Board;
import mechanics.ClassicLayout;
import mechanics.Game;
import mechanics.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends Activity {
	private BoardView bw;
	private Game game;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		
		setContentView(R.layout.main);
		bw = (BoardView) findViewById(R.id.boardView);
		
		
		if(intent.getAction().equals("org.kpi.abalone.GAME")){
			
			String sp = intent.getExtras().getString("vs");
			
			Player secondPlayer = sp.equals("cpu")?(new Ann()):bw;
			game = new Game(new ClassicLayout(), Board.BLACK, bw,
					secondPlayer, bw);
			bw.setGame(game);
			//bw.drawBoard(game.getBoard());
			(new Thread(new Runnable() {

				@Override
				public void run() {
					
					game.start();
				}
			},"Game Thread")).start();
		}
		
		

	}
	
	
	
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		bw.screenChanged();
		return super.onRetainNonConfigurationInstance();
	}
}
