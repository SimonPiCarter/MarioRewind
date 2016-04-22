package mr.controller.ai.action;

import mr.controller.ai.AI;
import mr.model.misc.Coordinate;
import mr.model.state.IState;

public class Waypoint implements IAction {

	private static final float distance = 15f;

	private IState stateRight;
	private IState stateLeft;
	private IState stateIdle;
	private Coordinate waypoint;
	private boolean over;



	public Waypoint(IState stateRight, IState stateLeft, IState stateIdle, Coordinate waypoint) {
		this.stateRight = stateRight;
		this.stateLeft = stateLeft;
		this.stateIdle = stateIdle;
		this.waypoint = waypoint;
	}

	@Override
	public IState update(AI ai, int delta) {
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
