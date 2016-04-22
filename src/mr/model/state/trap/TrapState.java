package mr.model.state.trap;

import mr.controller.entity.Hero;
import mr.model.Item;
import mr.model.state.AbstractState;

public abstract class TrapState extends AbstractState {

	public TrapState(int state, boolean loop) {
		super(state, loop);
	}

	public abstract void collide(Hero hero, Item item);
}
