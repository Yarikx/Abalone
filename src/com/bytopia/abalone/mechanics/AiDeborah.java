package com.bytopia.abalone.mechanics;

/**
 * Strong configuration of AI. Uses two-step depth and neighbouring marbles
 * analysis. NOT READY YET.
 * 
 * @author Bytopia
 */
public class AiDeborah extends ArtificialIntilligence {

	public Move requestMove(Game g) {
		return findNextMove(g.getBoard(), g.getSide(), 2, CLEVER, true);
	}
}