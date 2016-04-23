package mr.model.state;

import mr.model.Sequence;

public abstract class AbstractState {
	public enum StateEvent {
		None,
		Stop,
		MoveRight,
		MoveLeft,
		Jump,
		Fall,
		Die,
		EndDie
	};

	private Sequence sequence;

	public AbstractState(int state) {
		sequence = new Sequence();
		sequence.getStates().add(Integer.valueOf(state));
		sequence.setLoop(true);
	}

	public AbstractState(int state, boolean loop) {
		sequence = new Sequence();
		sequence.getStates().add(Integer.valueOf(state));
		sequence.setLoop(loop);
	}

	public abstract AbstractState handleEvent(StateEvent event);

	public Sequence getSequence() {
		return sequence;
	}
}
