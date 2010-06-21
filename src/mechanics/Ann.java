package mechanics;

import java.util.ArrayList;
import java.util.List;

public class Ann implements ArtificialIntilligence {

	private static Cell center = new Cell(5, 5);
	private Move bestMove;

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

	private List<Group> getAllGroups(Board b, byte side) {
		List<Group> list = new ArrayList<Group>();
		for (Cell c : b.getSideMarbles(side)) {
			list.add(new Group(c));
			searchForLines(b, c, list, Direction.East, side);
			searchForLines(b, c, list, Direction.SouthEast, side);
			searchForLines(b, c, list, Direction.South, side);
		}
		return list;
	}

	private List<Move> getAllPossibleMoves(Board b, byte side) {
		List<Move> list = new ArrayList<Move>();
		for (Group group : getAllGroups(b, side)) {
			for (Direction d : Direction.getAll()) {
				Move m = new Move(group, d, side);
				if (b.getMoveType(m).getResult() != MoveType.NOMOVE) {
					list.add(m);
				}
			}
		}
		return list;
	}

	private double evaluatePosition(Board b, byte side, int steps,
			double alphabeta) {
		if (steps == 0) {
			double sum = 4 * (b.getMarblesCaptured(Board.oppositeSide(side)) - b
					.getMarblesCaptured(side)) + Math.random()*0.000001;
			for (Cell c : b.getAllMarbles()) {
				if (b.getState(c) == side)
					sum += 1 / (c.findDistance(center) + 1.0);
				else
					sum -= 1 / (c.findDistance(center) + 1.0);
			}
			return sum;
		} else {
			Board futureBoard;
			Move bestMove = null;
			double currValue, bestValue = Double.POSITIVE_INFINITY, ab = alphabeta;
			for (Move m : getAllPossibleMoves(b, side)) {
				futureBoard = b.clone();
				futureBoard.makeMove(m);
				currValue = evaluatePosition(futureBoard, Board
						.oppositeSide(side), steps - 1, ab);
				if (currValue < bestValue) {
					bestValue = currValue;
					bestMove = m;
					if (alphabeta > bestValue)
						break;
					ab = bestValue;
				}
			}
			this.bestMove = bestMove;
			return -bestValue;
		}
	}

	public Move findNextMove(Board b, byte side, int steps) {
		evaluatePosition(b, side, steps, Double.NEGATIVE_INFINITY);
		return bestMove;
	}

	public Move requestMove(Game g) {
		return findNextMove(g.getBoard(), g.getSide(), 1);
	}
}
