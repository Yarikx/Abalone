package mechanics;

/**
 * Interface that describes how Player objects (Controller entities in MVC
 * pattern) should work.
 * 
 * @author Bytopia
 */
public interface Player {

	/**
	 * Returns the move chosen by player for the given game.
	 * 
	 * @param g
	 *            game currently played
	 * @return move that player wants to make
	 */
	public Move requestMove(Game g);

}
