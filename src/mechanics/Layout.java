package mechanics;

public abstract class Layout {

	public static final byte N = 0;
	public static final byte E = 1;
	public static final byte W = 2;
	public static final byte B = 3;
	
	public abstract byte[][] getStartField();
	
	public static int getOpposite(int side) {
		return side == W ? B : W;
	}
}

//     ● ● ● ● ●
//    ● ● ● ● ● ●
//   - - ● ● ● - -
//  - - - - - - - -
// - - - - - - - - -
//  - - - - - - - -
//   - - ○ ○ ○ - -
//    ○ ○ ○ ○ ○ ○ 
//     ○ ○ ○ ○ ○