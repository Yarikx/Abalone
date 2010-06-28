package mechanics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

	private static int PLAYER = 1;
	private static int AI = 2;

	private Board board;

	public void setBoard(Board board) {
		this.board = board;
	}

	private Watcher watcher;
	private Player blacksPlayer;
	private Player whitesPlayer;
	private byte currentSide = Board.BLACK;

	public byte getCurrentSide() {
		return currentSide;
	}

	public void setCurrentSide(byte currentSide) {
		this.currentSide = currentSide;
	}

	private byte vsType;
	public static final byte HUMAN = 1, CPU = 2;

	public Game(Layout l, byte playerSide, Player blacks, Player whites,
			Watcher watcher) {
		board = new Board(l, playerSide);
		blacksPlayer = blacks;
		whitesPlayer = whites;
		this.watcher = watcher;
	}

	public Game(Board board, byte currentSide, Player blacks, Player whites,
			Watcher watcher) {
		this.board=board;
		blacksPlayer = blacks;
		whitesPlayer = whites;
		this.watcher = watcher;
		this.currentSide = currentSide;
	}

	public void start() {
		int i = 0;
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		System.out.println(board);
		Move move = null;
		while (board.getMarblesCaptured(Board.WHITE) < 6
				&& board.getMarblesCaptured(Board.BLACK) < 6) {
			if (currentSide == Board.BLACK) {
				move = blacksPlayer.requestMove(this);
			} else {
				move = whitesPlayer.requestMove(this);
			}
			if (watcher != null) {
				watcher.doAnimation(board.getMoveType(move),
						move.getDirection());
			}

			board.makeMove(move);
			if (watcher != null) {
				watcher.updateView();
				// try {
				// br.readLine();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

			}
			currentSide = Board.oppositeSide(currentSide);
			System.out.println(board);
			System.out.println(i++ + ". "
					+ board.getMarblesCaptured(Board.WHITE) + ":"
					+ board.getMarblesCaptured(Board.BLACK));
		}
		if(board.getMarblesCaptured(Board.WHITE) >= 6){
			watcher.win(Board.BLACK);
		}else{
			watcher.win(Board.WHITE);
		}
	}

	public Board getBoard() {
		return board;
	}

	public byte getSide() {
		return currentSide;
	}

	public void setVsType(byte vsType) {
		this.vsType = vsType;
	}

	public byte getVsType() {
		return vsType;
	}
}
