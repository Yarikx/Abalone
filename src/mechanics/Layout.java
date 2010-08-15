package mechanics;

/**
 * Class that represents an abstract factory of starting positions (layouts).
 * 
 * @author Bytopia
 */
public abstract class Layout {

	/**
	 * Represents a non-existing cell.
	 */
	public static final byte N = 0;

	/**
	 * Represents an empty cell.
	 */
	public static final byte E = 1;

	/**
	 * Represents a cell with white marble.
	 */
	public static final byte W = Side.WHITE;
	
	/**
	 * Represents a cell with black marble.
	 */
	public static final byte B = Side.BLACK;

	/**
	 * Returns a two-dimensional array filled with definitions of cells for the starting position with black marbles downside.
	 * @return array that contains starting position with black marbles downside
	 */
	public abstract byte[][] getBlackStartField();

	/**
	 * Returns a mirrored layout with white marbles downside respectively. 
	 * @return array that contains starting position with white marbles downside
	 */
	public byte[][] getWhiteStartField() {
		byte[][] field = getBlackStartField();
		byte t;
		for (int i = 1; i < 5; i++) {
			for (int j = Board.getMinColumn(i); j <= Board.getMaxColumn(i); j++) {
				t = field[i][j];
				field[i][j] = field[10 - i][Board.getMaxColumn(10 - i) - j + 1];
				field[10 - i][Board.getMaxColumn(10 - i) - j + 1] = t;
			}
		}
		return field;
	}

}