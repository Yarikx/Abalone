package mechanics;

/**
 * Class that contains player sides. Not a enumeration because of performance
 * issues.
 * 
 * @author Ajee Interactive
 */
public final class Side {

	public static byte WHITE = 2;
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

}
