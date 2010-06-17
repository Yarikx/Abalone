package mechanics;

public class Move {

	private Group source;
	private Group destination;
	private Direction direction;
	private byte side;
	
	public Move(Group group, Direction d, byte side) {
		source = group;
		destination = group.shift(d);
		direction = d;
		this.side = side;
	}
	
	public Group getSource() {
		return source;
	}

	public Group getDestination() {
		return destination;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public byte getSide() {
		return side;
	}
	
	public boolean isPushing() {
		return source.isLineDirected(direction);
	}

	public Cell getPeak() {
		return source.getPeak(direction);
	}
	
	public Cell getTail() {
		return source.getPeak(Direction.opposite(direction));
	}
	
	public String toString() {
		return source.toString() + " " + direction.toString();
	}
}
