package mr.controller.ai.action;

import mr.controller.ai.AI;
import mr.model.state.AbstractState;

public interface IAction {

	public abstract AbstractState update(AI ai, int delta);

	public abstract void start();

	public abstract boolean isOver();
}
