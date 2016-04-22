package mr.model.state.trap;

import mr.controller.colliders.ColliderToolbox;
import mr.controller.entity.Hero;
import mr.model.Item;
import mr.model.misc.Coordinate;

public class TrapUp extends TrapState {

	private final TrapDown other;

	public TrapUp() {
		super(1,false);
		this.other = new TrapDown(this);
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
		if ( ColliderToolbox.collide(
				Coordinate.add(hero.getPosition(),hero.getSpeed()), hero.getHitBox(),
				Coordinate.add(item.getPosition(),itemSpeed), item.getHitBox()) ) {
			if ( !hero.isDying() ) {
				hero.damage(1);
			}
		}
	}
}
