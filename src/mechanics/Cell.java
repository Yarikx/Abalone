package mechanics;

public class Cell {

	private int row;
	private int column;

	public Cell(int newRow, int newColumn) {
		row = newRow;
		column = newColumn;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Cell shift(Direction d) {
		int newRow = row;
		int newColumn = column;
		switch (d) {
		case NorthWest:
			newRow--;
			newColumn--;
			break;
		case North:
			newRow--;
			break;
		case East:
			newColumn++;
			break;
		case SouthEast:
			newRow++;
			newColumn++;
			break;
		case South:
			newRow++;
			break;
		case West:
			newColumn--;
			break;
		default:
		}
		return new Cell(newRow, newColumn);
	}

	public boolean onAnyLine(Cell c) {
		for (Direction d : Direction.getAll()) {
			if (onLine(c, d))
				return true;
		}
		return false;
	}

	public boolean onLine(Cell c, Direction d) {
		if (d == Direction.NorthWest || d == Direction.SouthEast) {
			return (Math.abs(row - c.getRow()) == 1 && Math.abs(column
					- c.getColumn()) == 1)
					|| (Math.abs(row - c.getRow()) == 2 && Math.abs(column
							- c.getColumn()) == 2);
		} else if (d == Direction.North || d == Direction.South) {
			return (Math.abs(row - c.getRow()) == 1 && Math.abs(column
					- c.getColumn()) == 0)
					|| (Math.abs(row - c.getRow()) == 2 && Math.abs(column
							- c.getColumn()) == 0);
		} else
			return (Math.abs(row - c.getRow()) == 0 && Math.abs(column
					- c.getColumn()) == 1)
					|| (Math.abs(row - c.getRow()) == 0 && Math.abs(column
							- c.getColumn()) == 2);
	}

	public Cell next() {
		if ((column + 1) > (((row - 5) < 0) ? (4 + row) : 9)) {
			return new Cell(row + 1, 1 + (row - 5) > 0 ? row - 5 : 0);
		} else {
			return new Cell(row, column + 1);
		}
	}

	public boolean hasNext() {
		return !(row == 9 && column == 9);
	}

	public boolean outOfBounds() {
		return (row >= 10);
	}

	public static Cell first() {
		return new Cell(1, 1);
	}

	public static Cell last() {
		return new Cell(9, 9);
	}

	public int findDistance(Cell c) {
		int cols = column - c.getColumn();
		int rows = row - c.getRow();
		if ((cols < 0) ^ (rows < 0))
			return Math.abs(cols) + Math.abs(rows);
		else
			return Math.max(Math.abs(cols), Math.abs(rows));

	}

	public boolean equals(Cell another) {
		return (row == another.getRow() && column == another.getColumn());
	}

	public String toString() {
		return Character.toString((char) ((int) 'A' + row - 1))
				+ Integer.toString(column);
	}

}
