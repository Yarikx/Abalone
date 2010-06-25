package mechanics;

public enum Direction {

	NorthWest, North, East, West, South, SouthEast;
	
	private static Direction[] primary = {East, South, SouthEast};
	private static Direction[] secondary = {West, North, NorthWest};
	
	public static Direction convert(int value) {
		return Direction.class.getEnumConstants()[value];
	}

	public static Direction opposite(Direction d) {
		return convert(5 - d.ordinal());
	}
	
	public static Direction[] getPrimary() {
		return primary;
	}
	
	public static Direction[] getSecondary() {
		return secondary;
	}
}
