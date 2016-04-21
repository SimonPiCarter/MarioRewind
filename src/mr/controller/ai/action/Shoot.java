package mr.controller.ai.action;

import mr.controller.ai.AI;
import mr.model.misc.Coordinate;
import mr.model.state.IState;

public class Shoot implements IAction {

	private static int time = 750;
	private int elapsedTime;

	private IState stateRight;
	private IState stateLeft;
	private IState stateIdle;
	private Coordinate direction;
	private boolean over;




	public Shoot(IState stateRight, IState stateLeft, IState stateIdle, Coordinate direction) {
		this.stateRight = stateRight;
		this.stateLeft = stateLeft;
		this.stateIdle = stateIdle;
		this.direction = direction;
	}

	@Override
	public IState update(AI ai, int delta) {
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
