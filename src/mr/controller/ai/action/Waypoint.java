package mr.controller.ai.action;

import mr.controller.ai.AI;
import mr.model.misc.Coordinate;
import mr.model.state.AbstractState;

public class Waypoint implements IAction {

	private static final float distance = 15f;

	private AbstractState stateRight;
	private AbstractState stateLeft;
	private AbstractState stateIdle;
	private Coordinate waypoint;
	private boolean over;



	public Waypoint(AbstractState stateRight, AbstractState stateLeft, AbstractState stateIdle, Coordinate waypoint) {
		this.stateRight = stateRight;
		this.stateLeft = stateLeft;
		this.stateIdle = stateIdle;
		this.waypoint = waypoint;
	}

	@Override
	public AbstractState update(AI ai, int delta) {
		if ( ai.getPosition().x-waypoint.x > distance ) {
			ai.move(false);
			return stateRight;
		} else if ( ai.getPosition().x-waypoint.x < -distance ) {
			ai.move(true);
			return stateLeft;
		} else {
			over = true;
		}
		return stateIdle;
	}

	@Override
	public void start() {
		over = false;
	}

	@Override
	public boolean isOver() {
		return over;
	}
}
