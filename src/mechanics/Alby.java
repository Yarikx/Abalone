package mechanics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Alby implements ArtificialIntilligence {

	private static Cell center = new Cell(5, 5);

	private Cell getNextCell(Board b, int side, Cell current) {
		Cell c = current;
		do {
			if (c.hasNext())
				return null;
			c = c.next();
		} while (b.getState(c) != side);
		return c;
	}

	private void searchForLines(Board b, Cell c, List<Group> l, Direction d,
			int side) {
		Cell neighbour = c.shift(d);
		if (b.getState(neighbour) == side) {
			l.add(new Group(c, neighbour));
			neighbour = neighbour.shift(d);
			if (b.getState(neighbour) == side)
				l.add(new Group(c, neighbour));
		}
	}

	private List<Group> getAllGroups(Board b, int side) {
		List<Group> list = new ArrayList<Group>();
		for (Cell c : b.getSideMarbles(side)) {
			list.add(new Group(c));
			searchForLines(b, c, list, Direction.East, side);
			searchForLines(b, c, list, Direction.SouthEast, side);
			searchForLines(b, c, list, Direction.South, side);
		}
		return list;
	}

	public List<Move> getAllPossibleMoves(Board b, byte side) {
		List<Move> list = new ArrayList<Move>();
		for (Group group : getAllGroups(b, side)) {
			for (Direction d : Direction.getAll()) {
				Move m = new Move(group, d, side);
				if (b.getMoveType(m) != Board.NOMOVE) {
					list.add(m);
				}
			}
		}
		return list;
	}

	public double evaluatePosition(Board b, byte side) {
		double sum = 2 * (b.getMarblesCaptured(Board.oppositeSide(side)) - b
				.getMarblesCaptured(side));
		for (Cell c : b.getAllMarbles()) {
			if (b.getState(c) == side)
				sum += 1 / (c.findDistance(center) + 1.0);
			else
				sum -= 1 / (c.findDistance(center) + 1.0);
		}
		return sum;
	}
	
	public Move findNextMove(Board b, byte side, int steps) {
		Board futureBoard;
		Move bestMove = null;
		double currValue, bestValue = Double.NEGATIVE_INFINITY;
		for (Move m : getAllPossibleMoves(b, side)) {
			futureBoard = b.clone();
			futureBoard.makeMove(m);
			currValue = evaluatePosition(futureBoard, side);
			if (currValue > bestValue) {
				bestValue = currValue;
				bestMove = m;
			}
		}
		return bestMove;
	}

	@Override
	public Move calculateMove(Board b, int side) {
		// TODO Auto-generated method stub
		return null;
	}

}
