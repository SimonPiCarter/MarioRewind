package mr.model.state;

public class Idle extends AbstractState {

	public final boolean right;

	public Idle(boolean right) {
		super((right?0:1));
		this.right = right;
	}

	@Override
	public AbstractState handleEvent(StateEvent event) {
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
}
