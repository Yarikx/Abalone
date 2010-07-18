package mechanics;

/**
 * Class that contains player sides. Not a enumeration because of performance
 * issues.
 * 
 * @author Ajee Interactive
 */
public final class Side {

	/**
	 * White side.
	 */
	public static byte WHITE = 2;
	
	/**
	 * Black side.
	 */
	public static byte BLACK = 3;

	/**
	 * Returns the side that is opposite to the given.
	 * 
	 * @param s
	 *            given side
	 * @return opposite side
	 */
	public static byte opposite(byte s) {
		return s == BLACK ? WHITE : BLACK;
	}
	
	/**
	 * Private empty constructor to make the class the service one.
	 */
	private Side() {
	}

}
