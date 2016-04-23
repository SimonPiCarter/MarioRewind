package mr.controller.entity;

import mr.controller.ai.AI;
import mr.controller.colliders.ColliderToolbox;
import mr.controller.colliders.ColliderToolbox.Collision;
import mr.controller.colliders.ICollider;
import mr.model.misc.Coordinate;
import mr.model.model.EnemyModel;
import mr.model.state.AbstractState;
import mr.model.state.AbstractState.StateEvent;

public class Enemy extends AI implements ICollider {
	private final EnemyModel model;

	public Enemy(Coordinate position, EnemyModel model, String id, AbstractState state) {
		super(position, model, id, state);
		this.model = model;
	}

	@Override
	public void collide(Hero hero) {
		Collision col = ColliderToolbox.detectKillCollision(hero, this, hero.getSpeed(), getSpeed());
		if ( col == Collision.SECOND_ITEM_KILLED ) {
			hero.setOnGround(true);
			updateState(StateEvent.Die);
			setDying(true);
		} else if ( col == Collision.FIRST_ITEM_KILLED ){
			if ( !hero.isDying() ) {
				hero.damage(1);
			}
		}
	}

	public EnemyModel getModel() {
		return model;
	}

	@Override
	public boolean isActive() {
		return !isDead();
	}
}
