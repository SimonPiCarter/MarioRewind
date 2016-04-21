package mr.model;

import mr.controller.colliders.ColliderToolbox;
import mr.model.misc.Coordinate;
import mr.model.model.ProjectileModel;
import mr.model.state.IState;

public class Projectile extends Item {
	private Coordinate direction;
	private ProjectileModel model;

	public Projectile(Coordinate position, ProjectileModel model, String id, IState state, Coordinate direction) {
		super(position, model, id, state);
		this.setDirection(direction);
		this.model = model;
	}

	public boolean update() {
		float speedSquared = direction.x*direction.x + direction.y*direction.y;
		getPosition().x += direction.x*model.getSpeed()/Math.sqrt(speedSquared);
		getPosition().y += direction.y*model.getSpeed()/Math.sqrt(speedSquared);

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
