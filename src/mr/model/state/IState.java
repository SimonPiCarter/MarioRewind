package mr.model.state;

public interface IState {
	enum StateEvent {
		None,
		Stop,
		MoveRight,
		MoveLeft,
		Jump,
		Fall,
		Die
	};

	IState handleEvent(StateEvent event);

	int getState();
}
