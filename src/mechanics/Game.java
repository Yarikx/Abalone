package mechanics;

public class Game {

	private static int PLAYER = 1;
	private static int AI = 2;

	private Board board;
	private Player blacksPlayer;
	private Player whitesPlayer;
	private byte currentSide = Board.BLACK;

	public Game(Layout l, byte playerSide, Player blacks, Player whites) {
		board = new Board(l, playerSide);
		blacksPlayer = blacks;
		whitesPlayer = whites;
	}

	public void start() {
		System.out.println(board);
		while (board.getMarblesCaptured(Board.WHITE) < 6
				&& board.getMarblesCaptured(Board.BLACK) < 6) {
			if (currentSide == Board.BLACK) {
				board.makeMove(blacksPlayer.requestMove(this));
			} else {
				board.makeMove(whitesPlayer.requestMove(this));
			}
			currentSide = Board.oppositeSide(currentSide);
			System.out.println(board);
		}
	}

	public Board getBoard() {
		return board;
	}

	public byte getSide() {
		return currentSide;
	}
}
