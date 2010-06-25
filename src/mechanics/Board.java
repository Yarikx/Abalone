package mechanics;

import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable {

	public static final byte WHITE = 2;
	public static final byte BLACK = 3;
	public static final int FAILURE = 0;
	public static final int SUCCESS = 1;

	private byte[][] field;
	private int whiteCaptured = 0;
	private int blackCaptured = 0;

	public Board() {
		this(new ClassicLayout(), BLACK);
	}

	public Board(Layout l, byte downSide) {
		if (downSide == BLACK)
			field = l.getBlackStartField();
		else
			field = l.getWhiteStartField();
	}

	public static int getMinColumn(int row) {
		return row < 6 ? 1 : row - 4;
	}

	public static int getMaxColumn(int row) {
		return row < 5 ? row + 4 : 9;
	}

	public byte getState(Cell c) {
		return field[c.getRow()][c.getColumn()];
	}

	public byte getState(int row, int column) {
		return field[row][column];
	}

	private void setState(Cell c, byte state) {
		field[c.getRow()][c.getColumn()] = state;
	}
	
	public boolean isValid(Group g, byte side) {
		if (!g.onAnyLine() || g.lineLength() > 3)
			return false;
		for (Cell c : g.getCells()) {
			if (getState(c) != side)
				return false;
		}
		return true;
	}

	public MoveType getMoveType(Move m) {
		// If move is a leap
		boolean result = true;
		for (Cell c : m.getDestination().getCells()) {
			if (getState(c) != Layout.E)
				result = false;
		}
		if (result == true)
			return new MoveType(MoveType.LEAP, m.getSource());
		// If move is a pushing move...
		if (m.isPushing()) {
			Direction d = m.getDirection();
			// ...check if it is a silent pushing move
			if (getState(m.getPeak().shift(d)) == Layout.E)
				return new MoveType(MoveType.SILENTPUSH, m.getSource());
			int nCell = getState(m.getPeak().shift(d));
			if (nCell == Layout.N)
				return new MoveType(MoveType.NOMOVE);
			int nnCell = getState(m.getPeak().shift(d).shift(d));
			byte enemyMarble = oppositeSide(m.getSide());
			if (nCell == enemyMarble
					&& (nnCell == Layout.E || nnCell == Layout.N))
				return new MoveType(MoveType.ENEMYPUSH, new Group(m.getTail(),
						m.getPeak().shift(d)));
			if (nnCell == Layout.N || m.getSource().lineLength() == 2)
				return new MoveType(MoveType.NOMOVE);
			int nnnCell = getState(m.getPeak().shift(d).shift(d).shift(d));
			if (nCell == enemyMarble && nnCell == enemyMarble
					&& (nnnCell == Layout.E || nnnCell == Layout.N))
				return new MoveType(MoveType.ENEMYPUSH, new Group(m.getTail(),
						m.getPeak().shift(d).shift(d)));
		}
		return new MoveType(MoveType.NOMOVE);
	}

	public int makeMove(Move m) {
		int type = getMoveType(m).getResult();
		if (type == MoveType.NOMOVE)
			return FAILURE;
		else if (type == MoveType.LEAP || type == MoveType.SILENTPUSH) {
			for (Cell c : m.getSource().getCells())
				setState(c, Layout.E);
			for (Cell c : m.getDestination().getCells())
				setState(c, m.getSide());
		} else {
			byte src = Layout.E, dest;
			Cell c = m.getTail();

			do {
				dest = getState(c);
				if (dest == Layout.N)
					if (m.getSide() == WHITE)
						blackCaptured++;
					else
						whiteCaptured++;
				else
					setState(c, src);
				src = dest;
				c = c.shift(m.getDirection());
			} while (dest != Layout.E && dest != Layout.N);
		}
		return SUCCESS;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("       1 2 3 4 5");
		int j = 1;
		for (byte[] row : field) {
			for (int i = 0; i < Math.abs(6 - j); i++) {
				s.append(" ");
			}
			if (j != 1 && j != 11) {
				s.append((char) ((int) 'A' + j - 2) + " ");
			}
			for (int cell : row)
				if (cell == Layout.E)
					s.append("- ");
				else if (cell == Layout.W)
					s.append("○ ");
				else if (cell == Layout.B)
					s.append("● ");
			if (j != 1 && j <= 5)
				s.append(Integer.toString(j + 4));
			s.append("\n");
			j++;
		}
		return s.toString();
	}

	public int getMarblesCaptured(byte side) {
		return side == WHITE ? whiteCaptured : blackCaptured;
	}

	public List<Cell> getAllMarbles() {
		List<Cell> list = new ArrayList<Cell>();
		for (int i = 1; i <= 9; i++)
			for (int j = getMinColumn(i); j <= getMaxColumn(i); j++)
				if (getState(i,j) == WHITE || getState(i,j) == BLACK)
					list.add(new Cell(i,j));
		return list;
	}

	public List<Cell> getSideMarbles(byte side) {
		List<Cell> list = new ArrayList<Cell>();
		for (int i = 1; i <= 9; i++)
			for (int j = getMinColumn(i); j <= getMaxColumn(i); j++)
				if (getState(i,j) == side)
					list.add(new Cell(i,j));
		return list;
	}

	public Board clone() {
		Board b = new Board();
		byte[][] f = new byte[11][11];
		for (int i = 0; i < 11; i++)
			for (int j = 0; j < 11; j++)
				f[i][j] = field[i][j];
		b.field = f;
		b.whiteCaptured = whiteCaptured;
		b.blackCaptured = blackCaptured;
		return b;
	}

	public static byte oppositeSide(byte side) {
		return side == WHITE ? BLACK : WHITE;
	}
}
