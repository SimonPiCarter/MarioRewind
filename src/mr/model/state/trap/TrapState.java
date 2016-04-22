package mr.model.state.trap;

import mr.controller.entity.Hero;
import mr.model.Item;
import mr.model.misc.Coordinate;
import mr.model.state.AbstractState;

public abstract class TrapState extends AbstractState {

	public TrapState(int state, boolean loop) {
		super(state, loop);
	}

	@Override
	public abstract TrapState handleEvent(StateEvent event);

	public abstract void collide(Hero hero, Item item, Coordinate itemSpeed);
}
