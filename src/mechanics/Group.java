package mechanics;

public class Group {

	private Cell[] cells;
	private int length;

	public Group(Cell cell) {
		cells = new Cell[1];
		cells[0] = cell;
		length = 1;
	}

	public Group(Cell start, Cell end) {
		length = cellsInRange(start, end) + 1;
		cells = new Cell[length];
		cells[0] = start;
		cells[length - 1] = end;
		if (length == 3)
			cells[1] = new Cell(Math.abs(start.getRow() + end.getRow()) / 2,
					Math.abs(start.getColumn() + end.getColumn()) / 2);
		else if (length == 4) {
			cells[1] = new Cell(Math.abs(start.getRow() + end.getRow()) / 2,
					Math.abs(start.getColumn() + end.getColumn()) / 2);
			cells[2] = new Cell(
					Math.abs(start.getRow() + end.getRow() + 1) / 2, Math
							.abs(start.getColumn() + end.getColumn() + 1) / 2);
		} else if (length == 5) {
			cells[2] = new Cell(Math.abs(start.getRow() + end.getRow()) / 2,
					Math.abs(start.getColumn() + end.getColumn()) / 2);
			cells[1] = new Cell(
					Math.abs(start.getRow() + cells[2].getRow()) / 2, Math
							.abs(start.getColumn() + cells[2].getColumn()) / 2);
			cells[3] = new Cell(Math.abs(cells[2].getRow() + end.getRow()) / 2,
					Math.abs(cells[2].getColumn() + end.getColumn()) / 2);
		}
	}

	public Cell getFirstEnd() {
		return cells[0];
	}

	public Cell getSecondEnd() {
		return cells[length - 1];
	}

	public Cell[] getCells() {
		return cells;
	}

	public boolean atom() {
		return length == 1;
	}

	public int lineLength() {
		return length;
	}

	private static int cellsInRange(Cell start, Cell end) {
		return Math.max(Math.abs(start.getRow() - end.getRow()), Math.abs(start
				.getColumn()
				- end.getColumn()));
	}

	public Group shift(Direction d) {
		if (atom())
			return new Group(cells[0].shift(d));
		else
			return new Group(cells[0].shift(d), cells[length - 1].shift(d));
	}

	public boolean isLineDirected(Direction d) {
		if (atom())
			return false;
		return cells[0].onLine(cells[length - 1], d);
	}

	public String toString() {
		if (atom()) {
			return cells[0].toString();
		} else {
			return cells[0].toString() + "-" + cells[length - 1].toString();
		}
	}

	public boolean onAnyLine() {
		return cells[0].onAnyLine(cells[length - 1]);
	}
	
	public Cell getPeak(Direction d) {
		Cell cell;
		switch (d) {
		case NorthWest:
		case North:
			cell = cells[0].getRow() < cells[1].getRow() ? cells[0] : cells[1];
			if (length == 3)
				cell = cell.getRow() < cells[2].getRow() ? cell : cells[2];
			break;
		case East:
			cell = cells[0].getColumn() > cells[1].getColumn() ? cells[0]
					: cells[1];
			if (length == 3)
				cell = cell.getColumn() > cells[2].getColumn() ? cell
						: cells[2];
			break;
		case SouthEast:
		case South:
			cell = cells[0].getRow() > cells[1].getRow() ? cells[0] : cells[1];
			if (length == 3)
				cell = cell.getRow() > cells[2].getRow() ? cell : cells[2];
			break;
		case West:
			cell = cells[0].getColumn() < cells[1].getColumn() ? cells[0]
					: cells[1];
			if (length == 3)
				cell = cell.getColumn() < cells[2].getColumn() ? cell
						: cells[2];
			break;
		default:
			cell = null;
		}
		return cell;
	}
	
	public boolean isCellInGroup(Cell c) {
		for (int i = 0; i < length; i++) {
			if (cells[0].equals(c))
				return true;
		}
		return false;
	}

}
