package mechanics;

public abstract class Layout {

	public static final byte N = 0;
	public static final byte E = 1;
	public static final byte W = (byte) Board.WHITE;
	public static final byte B = (byte) Board.BLACK;

	public abstract byte[][] getBlackStartField();
	
	public byte[][] getWhiteStartField() {
		byte[][] field = getBlackStartField();
		byte t;
		for (int i = 1; i < 5; i++) {
			for (int j = Board.getMinColumn(i); j <= Board.getMaxColumn(i); j++) {
				t = field[i][j];
				field[i][j] = field[10-i][Board.getMaxColumn(10-i)-j+1];
				field[10-i][Board.getMaxColumn(10-i)-j+1] = t;
			}
		}
		return field;
	}

}