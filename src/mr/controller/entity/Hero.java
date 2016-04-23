package mr.controller.entity;

import mr.controller.movable.Movable;
import mr.model.misc.Coordinate;
import mr.model.model.HeroModel;
import mr.model.state.AbstractState;

public class Hero extends Movable {
	private int life;
	private float backtrack;
	private final HeroModel model;

	public Hero(Coordinate position, HeroModel model, String id, AbstractState state) {
		super(position, model, id, state);
		this.life = model.getLife();
		this.backtrack = model.getBacktrack();
		this.model = model;
	}

	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public float getBacktrack() {
		return backtrack;
	}
	public void setBacktrack(float backtrack) {
		this.backtrack = backtrack;
	}

	public void damage(int dmg) {
		life = Math.max(0, life-dmg);
		setDying(true);
	}

	public HeroModel getModel() {
		return model;
	}
}
