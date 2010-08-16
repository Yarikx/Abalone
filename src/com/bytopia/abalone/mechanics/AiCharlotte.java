package com.bytopia.abalone.mechanics;

/**
 * Normal configuration of AI. Uses two-step depth analysis. Level "Hard".
 * 
 * @author Bytopia
 */
public class AiCharlotte extends ArtificialIntilligence {

	public Move requestMove(Game g) {
		return findNextMove(g.getBoard(), g.getSide(), 2, CLEVER, false);
	}
}