package com.bytopia.abalone.mechanics;

/**
 * Interface that describes how the Watcher objects should operate in order to
 * display game progress.
 * 
 * @author Bytopia
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

	/**
	 * Triggers when one of the side captures enemy's marble.
	 * 
	 * @param side
	 *            the side that has captured the marble
	 */
	void marbleCaptured(byte side);
}
