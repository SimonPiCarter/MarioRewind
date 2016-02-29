package mr.model;

import mr.model.misc.Coordinate;

public class NPC extends Item {
	private int life;
	
	public NPC(Coordinate position, Coordinate size, String type, String id, String state, int life) {
		super(position, size, type, id, state);
		this.life = life;
	}
	
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
}
