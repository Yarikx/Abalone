package mechanics;

/**
 * Service class for debugging from the PC.
 * 
 * @author Bytopia
 */
public class ConsoleWatcher implements Watcher {

	/**
	 * Game instance being watched.
	 */
	private Game game;

	/**
	 * Sets the game to watch.
	 * 
	 * @param g
	 *            Abalone game
	 */
	public void setGame(Game g) {
		game = g;
	}

	/**
	 * Updates the information about the game.
	 */
	public void updateView() {
		System.out.println(game.getBoard());
	}

	/**
	 * Shows animation. Not used.
	 */
	public void doAnimation(MoveType moveType, Direction direction) {
	}

	/**
	 * Triggers when one of the sides wins.
	 */
	public void win(byte side) {
		System.exit(0);
	}

	/**
	 * Triggers when one of the side captures enemy's marble. Not used.
	 */
	public void marbleCaptured(byte side) {
	}

}
