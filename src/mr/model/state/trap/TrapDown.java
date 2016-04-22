package mr.model.state.trap;

import mr.controller.entity.Hero;
import mr.model.Item;
import mr.model.misc.Coordinate;

public class TrapDown extends TrapState {

	private final TrapUp other;

	public TrapDown(TrapUp other) {
		super(0,false);
		this.other = other;
	}

	@Override
	public TrapState handleEvent(StateEvent event) {
		if ( getSequence().isOver() ) {
			return other;
		} else {
			return this;
		}
	}

	@Override
	public void collide(Hero hero, Item item, Coordinate itemSpeed) {
		// Do Nothing
	}
}
