package mechanics;

public interface Watcher {

	void updateView();
	void doAnimation(MoveType moveType, Direction direction);
	void win(byte side);
}
