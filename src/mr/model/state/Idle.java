package mr.model.state;

public class Idle implements IState {

	public final boolean right;

	public Idle(boolean right) {
		this.right = right;
	}

	@Override
	public IState handleEvent(StateEvent event) {
		switch (event) {
		case Die:
			return new Die(right);
		case Fall:
			return new Fall(right);
		case Jump:
			return new Jump(right);
		case MoveLeft:
			return new Move(false);
		case MoveRight:
			return new Move(true);
		case None:
		case Stop:
		default:
			return this;
		}
	}

	@Override
	public int getState() {
		int off = right?0:1;
		return off;
	}

}
