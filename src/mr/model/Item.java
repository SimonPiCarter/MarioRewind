package mr.model;

import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState;
import mr.model.state.AbstractState.StateEvent;

public class Item extends Sprite {
	private String id;
	private HitBox hitBox;
	private AbstractState state;
	private boolean isDead;
	private boolean isDying;

	public Item(Coordinate position, Model model, String id, AbstractState state) {
		super(position, model.getSize(), model.getSprite());
		this.id = id;
		this.state = state;
		this.hitBox = new HitBox(model.getHitBoxOffset(), model.getHitBoxSize());
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Sequence getSequence() {
		return state.getSequence();
	}

	public void updateState(StateEvent event) {
		state = state.handleEvent(event);
	}

	public HitBox getHitBox() {
		return hitBox;
	}

	public void setHitBox(HitBox hitBox) {
		this.hitBox = hitBox;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isDying() {
		return isDying;
	}

	public void setDying(boolean isDying) {
		if ( !isDying ) {
			updateState(StateEvent.EndDie);
		}
		this.isDying = isDying;
	}

}
