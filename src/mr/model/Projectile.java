package mr.model;

import mr.controller.colliders.ColliderToolbox;
import mr.model.misc.Coordinate;
import mr.model.model.ProjectileModel;
import mr.model.state.IState;
import mr.model.state.IState.StateEvent;

public class Projectile extends Item {
	private Coordinate direction;
	private ProjectileModel model;

	public Projectile(Coordinate position, ProjectileModel model, String id, IState state, Coordinate direction) {
		super(position, model, id, state);
		this.setDirection(direction);
		this.model = model;
	}

	public boolean update(Hero hero) {
		float speedSquared = direction.x*direction.x + direction.y*direction.y;
		getPosition().x += direction.x*model.getSpeed()/Math.sqrt(speedSquared);
		getPosition().y += direction.y*model.getSpeed()/Math.sqrt(speedSquared);

		if ( ColliderToolbox.isInside(hero.getPosition(), hero.getSize(), getPosition(), getSize())
				|| ColliderToolbox.isInside(getPosition(), getSize(), hero.getPosition(), hero.getSize()) ) {
			hero.setDying(true);
			hero.updateState(StateEvent.Die);
		}

		if ( !ColliderToolbox.isInside(new Coordinate(),new Coordinate(GameConstant.WINDOW_WIDTH, GameConstant.WINDOW_HEIGHT),getPosition(),getSize()) ) {
			return false;
		}
		return true;
	}

	public Coordinate getDirection() {
		return direction;
	}
	public void setDirection(Coordinate direction) {
		this.direction = direction;
	}


}
