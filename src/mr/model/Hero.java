package mr.model;

import mr.model.misc.Coordinate;
import mr.model.state.IState;

public class Hero extends Item {
	private int life;
	private double backtrack;

	public Hero(Coordinate position, Coordinate size, String type, String id, IState state, int life,
			double backtrack) {
		super(position, size, type, id, state);
		this.life = life;
		this.backtrack = backtrack;
	}

	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public double getBacktrack() {
		return backtrack;
	}
	public void setBacktrack(double backtrack) {
		this.backtrack = backtrack;
	}
}
