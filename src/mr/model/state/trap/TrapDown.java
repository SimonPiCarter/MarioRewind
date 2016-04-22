package mr.model.state.trap;

import mr.controller.entity.Hero;
import mr.model.Item;
import mr.model.state.AbstractState;

public class TrapDown extends TrapState {

	private final TrapUp other;

	public TrapDown(TrapUp other) {
		super(0,false);
		this.other = other;
	}

	@Override
	public AbstractState handleEvent(StateEvent event) {
		if ( getSequence().isOver() ) {
			return other;
		} else {
			return this;
		}
	}

	@Override
	public void collide(Hero hero, Item item) {
		// Do Nothing
	}
}
