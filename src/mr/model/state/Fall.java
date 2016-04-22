package mr.model.state;

public class Fall extends AbstractState {

	public final boolean right;

	public Fall(boolean right) {
		super(6+(right?0:1));
		this.right = right;
	}

	@Override
	public AbstractState handleEvent(StateEvent event) {
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
				return new Fall(false);
			}
		case MoveRight:
			if ( right ) {
				return this;
			} else {
				return new Fall(true);
			}
		case Stop:
			return new Idle(right);
		case None:
		default:
			return this;
		}
	}
}
