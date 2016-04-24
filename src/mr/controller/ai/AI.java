package mr.controller.ai;

import java.util.List;
import java.util.ListIterator;

import mr.controller.ProjectileHandler;
import mr.controller.ai.action.IAction;
import mr.controller.movable.Movable;
import mr.model.misc.Coordinate;
import mr.model.model.AIModel;
import mr.model.state.AbstractState;

public class AI extends Movable {

	private final AIModel model;

	private List<IAction> actions;
	private ListIterator<IAction> it;
	private IAction current;

	public AI(Coordinate position, AIModel model, String id, AbstractState state) {
		super(position, model, id, state);
		this.model = model;
		this.current = null;
	}

	public void update(int delta) {
		if ( it != null ) {
			if ( current == null && it.hasNext() ) {
				current = it.next();
				current.start();
			}
			current.update(this, delta);

			if ( current.isOver() ) {
				if ( !it.hasNext() ) {
					it = actions.listIterator();
				}
				current = null;
			}
		}
	}

	public void stop() {
		getSpeed().x = 0;
	}

	public void move(boolean right) {
		if ( right ) {
			getSpeed().x = model.getSpeed();
		} else {
			getSpeed().x = -model.getSpeed();
		}
	}

	public void shoot(Coordinate direction) {
		Coordinate startPoint = new Coordinate(getPosition());
		ProjectileHandler.get().addProjectile(model.getProjectileModel(),startPoint, direction);
	}

	public void jump() {
		getForce().y -= 200;
	}

	public void setActions(List<IAction> actions) {
		this.actions = actions;
		this.it = actions.listIterator();
		this.current = null;
	}
}
