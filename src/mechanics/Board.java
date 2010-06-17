package mechanics;

import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable {

	public static final byte WHITE = 2;
	public static final byte BLACK = 3;
	public static final int NOMOVE = 0;
	public static final int LEAP = 1;
	public static final int SILENTPUSH = 2;
	public static final int ENEMYPUSH = 3;
	public static final int FAILURE = 0;
	public static final int SUCCESS = 1;

	private byte[][] field;
	private int whiteCaptured = 0;
	private int blackCaptured = 0;

	public Board() {
		field = new ClassicLayout().getStartField();
	}

	public byte getState(Cell c) {
		return field[c.getRow()][c.getColumn()];
	}

	private void setState(Cell c, byte state) {
		field[c.getRow()][c.getColumn()] = state;
	}

	public int getMoveType(Move m) {
		// If move is a leap
		boolean result = true;
		for (Cell c : m.getDestination().getCells()) {
			if (getState(c) != Layout.E)
				result = false;
		}
		if (result == true)
			return LEAP;
		// If move is a pushing move...
		if (m.isPushing()) {
			Direction d = m.getDirection();
			// ...check if it is a silent pushing move
			if (getState(m.getPeak().shift(d)) == Layout.E)
				return SILENTPUSH;
			int nCell = getState(m.getPeak().shift(d));
			if (nCell == Layout.N)
				return NOMOVE;
			int nnCell = getState(m.getPeak().shift(d).shift(d));
			int enemyMarble = Layout.getOpposite(m.getSide());
			if (nCell == enemyMarble
					&& (nnCell == Layout.E || nnCell == Layout.N))
				return ENEMYPUSH;
			if (nnCell == Layout.N || m.getSource().lineLength() == 2)
				return NOMOVE;
			int nnnCell = getState(m.getPeak().shift(d).shift(d).shift(d));
			if (nCell == enemyMarble && nCell == enemyMarble
					&& (nnnCell == Layout.E || nnnCell == Layout.N))
				return ENEMYPUSH;
		}
		return NOMOVE;
	}

	public int makeMove(Move m) {
		int type = getMoveType(m);
		if (type == NOMOVE)
			return FAILURE;
		else if (type == LEAP || type == SILENTPUSH) {
			for (Cell c : m.getSource().getCells())
				setState(c, Layout.E);
			for (Cell c : m.getDestination().getCells())
				setState(c, m.getSide());
		} else {
			// int buffer = Layout.E;
			byte src = Layout.E, dest;
			Cell c = m.getTail();
			// Cell nc = c.shift(m.getDirection());

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
		Cell c = Cell.first();
		List<Cell> list = new ArrayList<Cell>();
		while (!c.outOfBounds()) {
			if (getState(c) == WHITE || getState(c) == BLACK)
				list.add(c);
			c = c.next();
		}
		return list;
	}

	public List<Cell> getSideMarbles(int side) {
		Cell c = Cell.first();
		List<Cell> list = new ArrayList<Cell>();
		while (!c.outOfBounds()) {
			if (getState(c) == side)
				list.add(c);
			c = c.next();
		}
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
	
	public static byte oppositeSide(int side) {
		return side == WHITE ? BLACK : WHITE;
	}
}
