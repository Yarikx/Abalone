package mechanics;

public class ConsoleWatcher implements Watcher {

	private Game game;
	
	public void addGame(Game g) {
		game = g;
	}

	public void updateView() {
		System.out.println(game.getBoard());
	}

	public void doAnimation(MoveType moveType, Direction direction) {
	}

	@Override
	public void win(byte side) {
		System.exit(0);
	}

	@Override
	public void ballCaptured(byte side) {
	}

}
