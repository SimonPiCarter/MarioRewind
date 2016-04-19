package mr.model.state;

public class Fall implements IState {

	public final boolean right;

	public Fall(boolean right) {
		this.right = right;
	}

	@Override
	public IState handleEvent(StateEvent event) {
		switch (event) {
		case Die:
			return new Die(right);
		case Fall:
			return this;
		case Jump:
			return new Jump(right);
		case MoveLeft:
			if ( !right ) {
				return this;
			} else {
				return new Fall(true);
			}
		case MoveRight:
			if ( right ) {
				return this;
			} else {
				return new Fall(false);
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
		return 6+off;
	}

}
