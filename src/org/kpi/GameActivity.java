package org.kpi;

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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
	private BoardView bw;
	private Game game;
	private static final String FILE_NAME = "gamedump.bin";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		Intent intent = getIntent();
		
		setContentView(R.layout.main);
		bw = (BoardView) findViewById(R.id.boardView);
		
		
		if(intent.getAction().equals("org.kpi.abalone.GAME")){
			
			String sp = intent.getExtras().getString("vs");
			
			Player secondPlayer = sp.equals("cpu")?(new Ann()):bw;
			game = new Game(new ClassicLayout(), Board.BLACK, bw,
					secondPlayer, bw);
			game.setVsType(sp.equals("cpu")?Game.CPU:Game.HUMAN);
			startGame();
		}else if(intent.getAction().equals("org.kpi.abalone.RESUMEGAME")){
			Log.d("state","resumeing");
			try {
				FileInputStream fis = openFileInput(FILE_NAME);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Board board = (Board) ois.readObject();
				byte side = ois.readByte();
				byte vsType = ois.readByte();
				Player secondPlayer = (vsType==Game.HUMAN)?bw:(new Ann());
				game = new Game(board, side, bw, secondPlayer, bw);
				startGame();
				
			} catch (FileNotFoundException e) {
				Log.d("state", "FileNotFound");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("state", "Error resumeing state");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		super.onCreate(savedInstanceState);
		

	}




	private void startGame() {
		bw.setGame(game);
		//bw.drawBoard(game.getBoard());
		(new Thread(new Runnable() {

			@Override
			public void run() {
				
				game.start();
			}
		},"Game Thread")).start();
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
			FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(game.getBoard());
			oos.writeByte(game.getCurrentSide());
			oos.writeByte(game.getVsType());
			
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		super.onPause();
	}
	
	
	
	
}
