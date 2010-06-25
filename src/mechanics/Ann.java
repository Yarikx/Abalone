package mechanics;

import java.util.ArrayList;
import java.util.List;

public class Ann implements ArtificialIntilligence {

	private static Cell center = Cell.get(5, 5);
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

	public List<Group> getAllGroups(Board b, byte side) {
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
			for (Direction d : Direction.values()) {
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
			double sum = 4
					* (b.getMarblesCaptured(Board.oppositeSide(side)) - b
							.getMarblesCaptured(side)) + Math.random()
					* 0.000001;
			for (Cell c : b.getAllMarbles()) {
				if (b.getState(c) == side)
					sum += 1 / (c.findDistance(center) + 1.0);
				else
					sum -= 1 / (c.findDistance(center) + 1.0);
			}
			return sum;
		} else {
			Board futureBoard;
			Move bestMove = null, m = null;
			double currValue, bestValue = Double.POSITIVE_INFINITY, ab = alphabeta;
			Cell original, shifted1, shifted2, shifted3, shifted4;
			byte state1, state2, state3, state4, state5;
			byte oppSide = Board.oppositeSide(side);
			byte[][] f = b.getField();
			flag : for (int i = 0; i < 11; i++)
				for (int j = 0; j < 11; j++) {
					if (f[i][j] == side) {
						for (Direction d : Direction.getSecondary()) {
							original = Cell.get(i, j);
							shifted1 = original.shift(d);
							state1 = b.getState(shifted1);
							if (state1 == Layout.E) {
								futureBoard = b.clone();
								m = new Move(new Group(original), d, side);
								futureBoard.makeMove(m);
								currValue = evaluatePosition(futureBoard,
										oppSide, steps - 1, ab);
								if (currValue < bestValue) {
									bestValue = currValue;
									bestMove = m;
									if (alphabeta > bestValue)
										break flag;
									ab = bestValue;
								}
							} else if (state1 == side) {
								shifted2 = shifted1.shift(d);
								state2 = b.getState(shifted2);
								shifted3 = shifted2.shift(d);
								state3 = b.getState(shifted3);
								if (state2 == Layout.E
										|| (state2 == oppSide && (state3 == Layout.N || state3 == Layout.E))) {
									futureBoard = b.clone();
									m = new Move(new Group(original, shifted1),
											d, side);
									futureBoard.makeMove(m);
									currValue = evaluatePosition(futureBoard,
											oppSide, steps - 1, ab);
									if (currValue < bestValue) {
										bestValue = currValue;
										bestMove = m;
										if (alphabeta > bestValue)
											break flag;
										ab = bestValue;
									}
								} else {
									shifted4 = shifted3.shift(d);
									state4 = b.getState(shifted4);
									state5 = b.getState(shifted4.shift(d));
									if (state3 == Layout.E
											|| (state3 == oppSide
													&& state4 == oppSide && (state5 == Layout.N || state5 == Layout.E))) {
										futureBoard = b.clone();
										m = new Move(new Group(original,
												shifted2), d, side);
										futureBoard.makeMove(m);
										currValue = evaluatePosition(
												futureBoard, Board
														.oppositeSide(side),
												steps - 1, ab);
										if (currValue < bestValue) {
											bestValue = currValue;
											bestMove = m;
											if (alphabeta > bestValue)
												break flag;
											ab = bestValue;
										}
									}
								}
							}
						}
						for (Direction d : Direction.getPrimary()) {
							original = Cell.get(i, j);
							shifted1 = original.shift(d);
							state1 = b.getState(shifted1);
							if (state1 == Layout.E) {
								futureBoard = b.clone();
								m = new Move(new Group(original), d, side);
								futureBoard.makeMove(m);
								currValue = evaluatePosition(futureBoard,
										oppSide, steps - 1, ab);
								if (currValue < bestValue) {
									bestValue = currValue;
									bestMove = m;
									if (alphabeta > bestValue)
										break flag;
									ab = bestValue;
								}
							} else if (state1 == side) {
								shifted2 = shifted1.shift(d);
								state2 = b.getState(shifted2);
								shifted3 = shifted2.shift(d);
								state3 = b.getState(shifted3);
								if (state2 == Layout.E
										|| (state2 == oppSide && (state3 == Layout.N || state3 == Layout.E))) {
									futureBoard = b.clone();
									m = new Move(new Group(original, shifted1),
											d, side);
									futureBoard.makeMove(m);
									currValue = evaluatePosition(futureBoard,
											oppSide, steps - 1, ab);
									if (currValue < bestValue) {
										bestValue = currValue;
										bestMove = m;
										if (alphabeta > bestValue)
											break flag;
										ab = bestValue;
									}
								} else {
									shifted4 = shifted3.shift(d);
									state4 = b.getState(shifted4);
									state5 = b.getState(shifted4.shift(d));
									if (state3 == Layout.E
											|| (state3 == oppSide
													&& state4 == oppSide && (state5 == Layout.N || state5 == Layout.E))) {
										futureBoard = b.clone();
										m = new Move(new Group(original,
												shifted2), d, side);
										futureBoard.makeMove(m);
										currValue = evaluatePosition(
												futureBoard, Board
														.oppositeSide(side),
												steps - 1, ab);
										if (currValue < bestValue) {
											bestValue = currValue;
											bestMove = m;
											if (alphabeta > bestValue)
												break flag;
											ab = bestValue;
										}
									}
									for (Direction md : Direction
											.getNotDirection(d)) {
										if (b.getState(original.shift(md)) == Layout.N
												&& b.getState(shifted1
														.shift(md)) == Layout.N
												|| b.getState(shifted2
														.shift(md)) == Layout.N) {
											futureBoard = b.clone();
											m = new Move(new Group(original,
													shifted2), md, side);
											futureBoard.makeMove(m);
											currValue = evaluatePosition(
													futureBoard, Board
															.oppositeSide(side),
													steps - 1, ab);
											if (currValue < bestValue) {
												bestValue = currValue;
												bestMove = m;
												if (alphabeta > bestValue)
													break flag;
												ab = bestValue;
											}
										}
									}
								}
								for (Direction md : Direction
										.getNotDirection(d)) {
									if (b.getState(original.shift(md)) == Layout.N
											&& b.getState(shifted1.shift(md)) == Layout.N) {
										futureBoard = b.clone();
										m = new Move(new Group(original,
												shifted1), md, side);
										futureBoard.makeMove(m);
										currValue = evaluatePosition(
												futureBoard, Board
														.oppositeSide(side),
												steps - 1, ab);
										if (currValue < bestValue) {
											bestValue = currValue;
											bestMove = m;
											if (alphabeta > bestValue)
												break flag;
											ab = bestValue;
										}
									}
								}
							}
						}
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
