package mechanics;

public enum Direction {

	NorthWest, North, East, West, South, SouthEast;

	private static Direction[] directions = { NorthWest, North, East, West,
			South, SouthEast };

	public static Direction convert(int value) {
		return Direction.class.getEnumConstants()[value];
	}

	public static Direction opposite(Direction d) {
		return convert(5 - d.ordinal());
	}

	public static Direction[] getAll() {
		return directions;
	}
}
