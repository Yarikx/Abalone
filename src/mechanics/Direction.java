package mechanics;

public enum Direction {

	NorthWest, North, East, West, South, SouthEast;
	
	private static Direction[] primary = {East, South, SouthEast};
	private static Direction[] secondary = {West, North, NorthWest};
	private static Direction[] notNorthSouth = {NorthWest, SouthEast, West, East};
	private static Direction[] notNWSE = {North, South, West, East};
	private static Direction[] notWestEast = {NorthWest, SouthEast, North, South};
	
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
	
	public static Direction[] getNotDirection(Direction d) {
		if (d == North || d == South)
			return notNorthSouth;
		else if (d == NorthWest || d == SouthEast)
			return notNWSE;
		else
			return notWestEast;
	}
}
