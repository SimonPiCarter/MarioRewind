package mr.controller.entity;

import mr.controller.colliders.ColliderToolbox;
import mr.controller.colliders.ICollider;
import mr.controller.movable.Movable;
import mr.model.Trigger;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState;

public class Key extends Movable implements ICollider  {
	private boolean used;
	private final Trigger trigger;

	public Key(Coordinate position, Model model, String id, AbstractState state, Trigger trigger) {
		super(position, model, id, state);
		used = false;
		this.trigger = trigger;
	}

	public boolean isUsed() {
		return used;
	}

	@Override
	public void collide(Hero hero, Coordinate heroSpeed, Coordinate ownSpeed) {
		if ( ColliderToolbox.collide(
				Coordinate.add(hero.getPosition(),hero.getSpeed()), hero.getHitBox(),
				Coordinate.add(getPosition(),ownSpeed), getHitBox()) ) {
			used = true;
			trigger.trigger();
		}

	}
}
