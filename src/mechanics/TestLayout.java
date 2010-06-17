package mechanics;

public class TestLayout extends Layout {

	private static byte[][] start = { 
			{ N, N, N, N, N, N, N, N, N, N, N },
			{ N, E, E, B, E, E, N, N, N, N, N }, // A
			{ N, E, E, B, B, B, B, N, N, N, N }, // B
			{ N, E, E, B, B, W, E, E, N, N, N }, // C
			{ N, E, E, E, B, B, W, B, E, N, N }, // D
			{ N, E, E, E, B, B, W, W, E, E, N }, // E
			{ N, N, E, E, E, B, B, W, E, E, N }, // F
			{ N, N, N, E, E, W, W, W, W, W, N }, // G
			{ N, N, N, N, W, W, E, E, W, E, N }, // H
			{ N, N, N, N, N, E, W, E, E, E, N }, // I
			{ N, N, N, N, N, N, N, N, N, N, N } };

	public byte[][] getStartField() {
		return start;
	}

}
