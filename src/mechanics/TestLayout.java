package mechanics;

public class TestLayout extends Layout {

	private static byte[][] start = { 
			{ N, N, N, N, N, N, N, N, N, N, N },
			{ N, W, E, E, E, E, N, N, N, N, N }, // A
			{ N, E, E, E, E, E, E, N, N, N, N }, // B
			{ N, E, E, E, E, E, E, E, N, N, N }, // C
			{ N, E, E, E, E, E, E, E, E, N, N }, // D
			{ N, E, E, E, E, E, B, B, B, W, N }, // E
			{ N, N, E, E, E, E, E, E, E, E, N }, // F
			{ N, N, N, E, E, E, E, E, E, E, N }, // G
			{ N, N, N, N, E, E, E, E, E, E, N }, // H
			{ N, N, N, N, N, E, E, E, E, E, N }, // I
			{ N, N, N, N, N, N, N, N, N, N, N } };

	public byte[][] getStartField() {
		return start;
	}

}
