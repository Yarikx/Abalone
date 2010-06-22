package mechanics;

public interface ArtificialIntilligence extends Player {

	public Move findNextMove(Board b, byte side, int steps);
}
