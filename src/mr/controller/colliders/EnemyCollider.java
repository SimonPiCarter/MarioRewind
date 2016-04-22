package mr.controller.colliders;

import mr.controller.colliders.ColliderToolbox.Collision;
import mr.controller.entity.Hero;
import mr.controller.movable.Movable;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.IState;
import mr.model.state.IState.StateEvent;

public class EnemyCollider extends Movable implements ICollider {

	public EnemyCollider(Coordinate position, Model model, String id, IState state) {
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
			hero.setDying(true);
		}
	}

}
