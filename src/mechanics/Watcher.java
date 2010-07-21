package mechanics;

/**
 * Interface that describes how the Watcher objects should operate in order to
 * display game progress.
 * 
 * @author Ajee Interactive
 */
public interface Watcher {

	/**
	 * Updates the current view managed by the watcher.
	 */
	void updateView();

	/**
	 * Starts the animation of the move with a given type made in the following
	 * direction.
	 * 
	 * @param moveType
	 *            type of the move that was performed
	 * @param direction
	 *            the direction of this move
	 */
	void doAnimation(MoveType moveType, Direction direction);

	/**
	 * Displays information that the game is over and shows the given side as a
	 * winner.
	 * 
	 * @param side
	 *            side that won the game
	 */
	void win(byte side);
	void ballCaptured(byte side);
}
