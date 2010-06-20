package mechanics;

public interface ArtificialIntilligence extends Player {

	public abstract Move findNextMove(Board b, byte side, int steps);
}
