package mr.model.state;

public class Jump implements IState {

	public final boolean right;

	public Jump(boolean right) {
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
			return this;
		case MoveLeft:
			if ( !right ) {
				return this;
			} else {
				return new Jump(true);
			}
		case MoveRight:
			if ( right ) {
				return this;
			} else {
				return new Jump(false);
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
		return 4+off;
	}

}
