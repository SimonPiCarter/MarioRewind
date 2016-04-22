package mr.controller.entity;

import mr.controller.movable.Movable;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState.StateEvent;
import mr.model.state.trap.TrapState;

public class Trap extends Movable {

	private TrapState state;

	public Trap(Coordinate position, Model model, String id, TrapState state) {
		super(position, model, id, state);
		this.state = state;
	}

	@Override
	public void updateState(StateEvent event) {
		super.updateState(event);
		state = state.handleEvent(event);
	}

	public void collide(Hero hero) {
		state.collide(hero, this, getSpeed());
	}
}
