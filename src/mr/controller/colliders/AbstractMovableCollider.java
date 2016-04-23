package mr.controller.colliders;

import mr.controller.movable.Movable;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState;

public abstract class AbstractMovableCollider extends Movable implements ICollider {

	public AbstractMovableCollider(Coordinate position, Model model, String id, AbstractState state) {
		super(position, model, id, state);
	}
}
