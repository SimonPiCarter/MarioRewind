package mr.controller.ai.action;

import mr.controller.ai.AI;
import mr.model.state.IState;

public interface IAction {

	public abstract IState update(AI ai, int delta);

	public abstract void start();

	public abstract boolean isOver();
}
