package mr.controller.entity;

import mr.controller.movable.Movable;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState;

public class Hero extends Movable {
	private int life;
	private double backtrack;

	public Hero(Coordinate position, Model model, String id, AbstractState state, int life,
			double backtrack) {
		super(position, model, id, state);
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
