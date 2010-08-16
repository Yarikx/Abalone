package com.bytopia.abalone.mechanics;

/**
 * The most weak configuration of AI. Uses one-step depth analysis. Level
 * "Easy".
 * 
 * @author Bytopia
 */
public class AiAnn extends ArtificialIntilligence {

	public Move requestMove(Game g) {
		return findNextMove(g.getBoard(), g.getSide(), 1, CLEVER, false);
	}
}