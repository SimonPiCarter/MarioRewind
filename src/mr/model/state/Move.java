package mr.model.state;

public class Move implements IState {

	public final boolean right;

	public Move(boolean right) {
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
			if ( !right ) {
				return this;
			} else {
				return new Move(true);
			}
		case MoveRight:
			if ( right ) {
				return this;
			} else {
				return new Move(false);
			}
		case Stop:
			return new Idle(right);
		case None:
		default:
			return this;
		}
	}

	@Override
	public int getState() {
		int off = right?0:1;
		return 2+off;
	}

}
