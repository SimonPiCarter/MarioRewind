package mr.controller.entity;

import mr.controller.colliders.ColliderToolbox;
import mr.controller.colliders.ColliderToolbox.Collision;
import mr.controller.colliders.ICollider;
import mr.controller.movable.Movable;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState;
import mr.model.state.AbstractState.StateEvent;

public class Enemy extends Movable implements ICollider {

	public Enemy(Coordinate position, Model model, String id, AbstractState state) {
		super(position, model, id, state);
	}

	@Override
	public void collide(Hero hero, Coordinate heroSpeed, Coordinate ownSpeed) {
		Collision col = ColliderToolbox.detectKillCollision(hero, this, heroSpeed, ownSpeed);
		if ( col == Collision.SECOND_ITEM_KILLED ) {
			hero.setOnGround(true);
			updateState(StateEvent.Die);
			setDying(true);
		} else if ( col == Collision.FIRST_ITEM_KILLED ){
			hero.updateState(StateEvent.Die);
			hero.damage(1);
		}
	}

}
