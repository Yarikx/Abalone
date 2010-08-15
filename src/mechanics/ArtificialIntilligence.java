package mechanics;

/**
 * Interface that describes how the AI classes should work.
 * 
 * @author Bytopia
 */
public interface ArtificialIntilligence extends Player {

	/**
	 * Finds the next best move for the given side on the given board with
	 * specified depth of computing.
	 * 
	 * @param b
	 *            board to find a move for
	 * @param side
	 *            side to find a move for
	 * @param steps
	 *            number of steps (depth of analysis)
	 * @return the best move for the given side
	 */
	public Move findNextMove(Board b, byte side, int steps);
}
