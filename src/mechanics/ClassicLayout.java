package mechanics;

public class ClassicLayout extends Layout {

	private byte[][] start = { 
			{ N, N, N, N, N, N, N, N, N, N, N },
			{ N, W, W, W, W, W, N, N, N, N, N }, // A
			{ N, W, W, W, W, W, W, N, N, N, N }, // W
			{ N, E, E, W, W, W, E, E, N, N, N }, // C
			{ N, E, E, E, E, E, E, E, E, N, N }, // D
			{ N, E, E, E, E, E, E, E, E, E, N }, // E
			{ N, N, E, E, E, E, E, E, E, E, N }, // F
			{ N, N, N, E, E, B, B, B, E, E, N }, // G
			{ N, N, N, N, B, B, B, B, B, B, N }, // H
			{ N, N, N, N, N, B, B, B, B, B, N }, // I
			{ N, N, N, N, N, N, N, N, N, N, N } };

	public byte[][] getBlackStartField() {
		return start;
	}
	
}
