package mr.controller.ai;

import java.util.List;
import java.util.ListIterator;

import mr.controller.ai.action.IAction;
import mr.controller.movable.ItemMovable;
import mr.model.misc.Coordinate;
import mr.model.model.AIModel;

public class AI {

	private final ItemMovable movable;
	private final AIModel model;

	private List<IAction> actions;
	private ListIterator<IAction> it;
	private IAction current;

	public AI(ItemMovable movable, AIModel model) {
		this.movable = movable;
		this.model = model;
		this.current = null;
	}

	public void update(int delta) {
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

	public void move(boolean right) {
		if ( right ) {
			movable.getSpeed().x = model.speed;
		} else {
			movable.getSpeed().x = -model.speed;
		}
	}

	public void shoot(Coordinate direction) {

	}

	public void jump() {
		movable.getForce().y -= 200;
	}

	public ItemMovable getMovable() {
		return movable;
	}

	public void setActions(List<IAction> actions) {
		this.actions = actions;
		this.it = actions.listIterator();
		this.current = null;
	}
}
