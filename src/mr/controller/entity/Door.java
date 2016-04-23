package mr.controller.entity;

import mr.controller.colliders.ColliderToolbox;
import mr.controller.colliders.ICollider;
import mr.controller.movable.Movable;
import mr.model.Event;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState;

public class Door extends Movable implements ICollider  {
	private final Event event;

	public Door(Coordinate position, Model model, String id, AbstractState state, Event event) {
		super(position, model, id, state);
		this.event = event;
	}

	@Override
	public void collide(Hero hero) {
		Coordinate pos = Coordinate.add(getPosition(), getHitBox().offset);
		ColliderToolbox.adjustPositionToTile(hero, pos.x, pos.x+getHitBox().size.x, pos.y, pos.x+getHitBox().size.y);
	}

	public boolean isOpen() {
		return event.isTriggered();
	}

	@Override
	public boolean isActive() {
		return !isOpen();
	}
}
