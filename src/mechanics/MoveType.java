package mechanics;

public class MoveType {

	public static final int NOMOVE = 0;
	public static final int LEAP = 1;
	public static final int SILENTPUSH = 2;
	public static final int ENEMYPUSH = 3;
	
	private int result;
	private Group cells;
	
	public MoveType(int result) {
		this(result,null);
	}
	
	public MoveType(int result, Group group) {
		this.result = result;
		cells = group;
	}
	
	
	public int getResult() {
		return result;
	}
	
	public Group getMovedCells() {
		return cells;
	}
	
}
