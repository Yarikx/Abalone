package mechanics;

public class Cell {

	private int row;
	private int column;
	private static Cell[][] cellStorage = new Cell[12][12];

	static void init() {
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++)
				cellStorage[i][j] = new Cell(i, j);
	}

	private Cell(int newRow, int newColumn) {
		row = newRow;
		column = newColumn;
	}

	public static Cell get(int row, int column) {
		return cellStorage[row][column];
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
		return cellStorage[newRow][newColumn];
	}

	public boolean onAnyLine(Cell c) {
		for (Direction d : Direction.values()) {
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

	public int findDistance(Cell c) {
		int cols = column - c.getColumn();
		int rows = row - c.getRow();
		if ((cols < 0) ^ (rows < 0))
			return Math.abs(cols) + Math.abs(rows);
		else
			return Math.max(Math.abs(cols), Math.abs(rows));

	}

	public String toString() {
		return Character.toString((char) ((int) 'A' + row - 1))
				+ Integer.toString(column);
	}

}
