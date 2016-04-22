package mr.controller.ai.action;

import mr.controller.ai.AI;
import mr.model.misc.Coordinate;
import mr.model.state.AbstractState;

public class Shoot implements IAction {

	private final int time;
	private int elapsedTime;

	private AbstractState stateRight;
	private AbstractState stateLeft;
	private AbstractState stateIdle;
	private Coordinate direction;
	private boolean over;

	public Shoot(AbstractState stateRight, AbstractState stateLeft, AbstractState stateIdle, Coordinate direction, int time) {
		this.time = time;
		this.stateRight = stateRight;
		this.stateLeft = stateLeft;
		this.stateIdle = stateIdle;
		this.direction = direction;
	}

	@Override
	public AbstractState update(AI ai, int delta) {
		if ( elapsedTime == 0 ) {
			ai.shoot(direction);
		}
		elapsedTime += delta;
		ai.stop();
		if ( elapsedTime > time ){
			elapsedTime = 0;
			over = true;
		}
		if ( direction.x > 0 ) {
			return stateRight;
		} else if ( direction.x < 0 ) {
			return stateLeft;
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
