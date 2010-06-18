package mechanics;

public abstract class Layout {

	public static final byte N = 0;
	public static final byte E = 1;
	public static final byte W = (byte) Board.WHITE;
	public static final byte B = (byte) Board.BLACK;

	public abstract byte[][] getStartField();

	public static int getOpposite(int side) {
		return side == W ? B : W;
	}
}