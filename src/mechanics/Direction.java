package mechanics;

/**
 * Enumeration that represents possible all directions of movement.
 * 
 * @author Ajee Interactive
 */
public enum Direction {

	NorthWest, North, East, West, South, SouthEast;

	/**
	 * Converts an integer value into a Direction enumeration object.
	 * @param value numeric value of the direction 
	 * @return direction object
	 */
	public static Direction convert(int value) {
		return Direction.class.getEnumConstants()[value];
	}

	/**
	 * Returns the opposite direction for a given direction.
	 * @param d arbitrary direction
	 * @return opposite direction
	 */
	public static Direction opposite(Direction d) {
		return convert(5 - d.ordinal());
	}

}