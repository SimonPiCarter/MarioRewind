package mr.controller.entity;

import mr.controller.colliders.AbstractMovableCollider;
import mr.controller.colliders.ColliderToolbox;
import mr.model.Trigger;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState;

public class Key extends AbstractMovableCollider {
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
	public void collide(Hero hero) {
		if ( ColliderToolbox.collide(
				Coordinate.add(hero.getPosition(),hero.getSpeed()), hero.getHitBox(),
				Coordinate.add(getPosition(),getSpeed()), getHitBox()) ) {
			used = true;
			trigger.trigger();
		}

	}

	@Override
	public boolean isActive() {
		return !isUsed();
	}
}
