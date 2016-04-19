package mr.model.state;

public interface IState {
	enum Event {
		None,
		Stop,
		MoveRight,
		MoveLeft,
		Jump,
		Fall,
		Die
	};

	IState handleEvent(Event event);

	int getState();
}
