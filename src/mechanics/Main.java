package mechanics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class Main {

	private static void globalInit() {
		Cell.init();
	}
	
	public static void main(String[] args) throws Exception {
		globalInit();
		long i = Calendar.getInstance().getTimeInMillis();
		Ann ai = new Ann();
		Board b = new Board();
		Random r = new Random();
		int x, y;
		List<Move> l = new ArrayList<Move>();
		for (int j = 1; j < 100000; j++) {
			
//			b.clone();
//			ai.getAllGroups(b, Board.WHITE);
//			x = r.nextInt(4)+1;
//			y = r.nextInt(4)+1;
//			Move m = new Move(new Group(
//					new Cell(x,y),
//					new Cell(x+2,y+2)),Direction.NorthWest,Board.WHITE);
//			l.add(m);
//			for (int K = 1; K < 7; K++) {
//			b.getMoveType(m);
//			}
//			b.makeMove(m);
			ai.getAllPossibleMoves(b, Board.BLACK);
//			int u = 0;
//			ai.getAllGroups(b, Board.WHITE);
		}
		System.out.println(Calendar.getInstance().getTimeInMillis() - i);
//		Game game = new Game(new ClassicLayout(),Board.BLACK,new Ann(),new Ann(),null);
//		game.start();
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
//		while (true) {
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
//		}
	}

	public static Direction convDir(String s) {
		if (s.equals("NW"))
			return Direction.NorthWest;
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

	public static Cell convCell(String s) {
		return Cell.get((int) s.charAt(0) - (int) 'A' + 1, Integer
				.parseInt(Character.toString(s.charAt(1))));
	}

	public static byte convSide(String s) {
		if (s.equals("W"))
			return Board.WHITE;
		else
			return Board.BLACK;
	}
}
