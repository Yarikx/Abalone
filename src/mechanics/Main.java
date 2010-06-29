package mechanics;


public class Main {

	public static void main(String[] args) throws Exception {
		Game game = new Game(new ClassicLayout(),Board.BLACK,new Ann(),new Ann(),null);
		game.start();
//		Board board = new Board(new TestLayout(),Board.BLACK);
//		System.out.println(board);
//		Ann ai = new Ann();
		
//		board.makeMove(ai.findNextMove(board, Board.BLACK, 3));
//		System.out.println(board);
//		System.out.println(board);
		//board.makeMove(new Move(new Group(new Cell())))
//		System.out.println(ai.findNextMove(board, Board.BLACK, 3));
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String[] s;
		while (true) {
//			br.readLine();
//			board.makeMove(ai.findNextMove(board, Board.BLACK, 4));
//			System.out.println(board);
//			System.out.println(board.getMarblesCaptured(Board.WHITE)+":"+board.getMarblesCaptured(Board.BLACK));
//			br.readLine();
//			board.makeMove(ai.findNextMove(board, Board.WHITE, 1));
//			System.out.println(board);
//			System.out.println(board.getMarblesCaptured(Board.WHITE)+":"+board.getMarblesCaptured(Board.BLACK));
//			s = br.readLine().split(" ");
//			if (s.length == 3)
//				board.makeMove(new Move(new Group(convCell(s[0])),
//						convDir(s[1]), convSide(s[2])));
//			else
//				board.makeMove(new Move(new Group(convCell(s[0]),
//						convCell(s[1])), convDir(s[2]), convSide(s[3])));
//			System.out.println(board);
//			board.makeMove(ai.findNextMove(board, Board.BLACK, 3));
//			System.out.println(board);
//			System.out.println(board.getMarblesCaptured(Board.WHITE)+":"+board.getMarblesCaptured(Board.BLACK));
		}
	}

	public static Direction convDir(String s) {
		if (s.equals("NW"))
			return Direction.NorthWest;//	public static Cell convCell(String s) {
//		return new Cell((int) s.charAt(0) - (int) 'A' + 1, Integer
//		.parseInt(Character.toString(s.charAt(1))));
//}
		else if (s.equals("N"))
			return Direction.North;
		else if (s.equals("E"))
			return Direction.East;
		else if (s.equals("SE"))
			return Direction.SouthEast;
		else if (s.equals("S"))
			return Direction.South;
		else
			return Direction.West;
	}

//	public static Cell convCell(String s) {
//		return new Cell((int) s.charAt(0) - (int) 'A' + 1, Integer
//				.parseInt(Character.toString(s.charAt(1))));
//	}

	public static byte convSide(String s) {
		if (s.equals("W"))
			return Board.WHITE;
		else
			return Board.BLACK;
	}
}
