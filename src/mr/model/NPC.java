package mr.model;

import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.IState;

public class NPC extends Item {
	private int life;

	public NPC(Coordinate position, Model model, String id, IState state, int life) {
		super(position, model, id, state);
		this.life = life;
	}

	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
}
