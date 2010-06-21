package mechanics;

public class Game {

	private static int PLAYER = 1;
	private static int AI = 2;

	private Board board;
	private Watcher watcher;
	private Player blacksPlayer;
	private Player whitesPlayer;
	private byte currentSide = Board.BLACK;

	public Game(Layout l, byte playerSide, Player blacks, Player whites, Watcher watcher) {
		board = new Board(l, playerSide);
		blacksPlayer = blacks;
		whitesPlayer = whites;
		this.watcher = watcher;
	}

	public void start() {
		int i = 0;
		System.out.println(board);
		Move move = null;
		while (board.getMarblesCaptured(Board.WHITE) < 6
				&& board.getMarblesCaptured(Board.BLACK) < 6) {
			if (currentSide == Board.BLACK) {
				move = blacksPlayer.requestMove(this);
			} else {
				move = whitesPlayer.requestMove(this);
			}
			if(watcher!=null){
				watcher.doAnimation(board.getMoveType(move));
			}
			board.makeMove(move);
			if (watcher != null){
				watcher.updateView();
			}
			currentSide = Board.oppositeSide(currentSide);
			System.out.println(board);
			System.out.println(i++ + ". "+board.getMarblesCaptured(Board.WHITE)+":"+board.getMarblesCaptured(Board.BLACK));
		}
	}

	public Board getBoard() {
		return board;
	}

	public byte getSide() {
		return currentSide;
	}
}
