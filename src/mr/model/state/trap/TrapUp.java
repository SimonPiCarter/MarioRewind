package mr.model.state.trap;

import mr.controller.colliders.ColliderToolbox;
import mr.controller.entity.Hero;
import mr.model.Item;
import mr.model.state.AbstractState;

public class TrapUp extends TrapState {

	private final TrapDown other;

	public TrapUp() {
		super(0,false);
		this.other = new TrapDown(this);
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
		if ( ColliderToolbox.collide(hero.getPosition(), hero.getHitBox(), item.getPosition(), item.getHitBox()) ) {
			hero.setDying(true);
		}
	}
}
