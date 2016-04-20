package mr.controller.colliders;

import mr.controller.colliders.ColliderToolbox.Collision;
import mr.controller.movable.HeroMovable;
import mr.model.Hero;
import mr.model.Item;
import mr.model.misc.Coordinate;
import mr.model.state.IState.StateEvent;

public class EnemyCollider implements ICollider {

	private final Item item;

	public EnemyCollider(Item item) {
		this.item = item;
	}

	@Override
	public void collide(HeroMovable heroMovable, Coordinate heroSpeed, Coordinate ownSpeed) {
		Hero hero = heroMovable.getMovable();
		Collision col = ColliderToolbox.detectKillCollision(hero, item, heroSpeed, ownSpeed);
		if ( col == Collision.SECOND_ITEM_KILLED ) {
			System.out.println("item killed");
			heroMovable.setOnGround(true);
			item.updateState(StateEvent.Die);
			item.setDead(true);
		} else if ( col == Collision.FIRST_ITEM_KILLED ){
			System.out.println("hero killed");
			heroMovable.getMovable().updateState(StateEvent.Die);
			heroMovable.getMovable().setDead(true);
		}
	}

	public Item getItem() {
		return item;
	}

}
