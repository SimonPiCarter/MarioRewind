package mr.model.state;

public class Jump extends AbstractState {

	public final boolean right;

	public Jump(boolean right) {
		super(4+(right?0:1));
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
			return this;
		case MoveLeft:
			if ( !right ) {
				return this;
			} else {
				return new Jump(false);
			}
		case MoveRight:
			if ( right ) {
				return this;
			} else {
				return new Jump(true);
			}
		case Stop:
			return new Idle(right);
		case None:
		default:
			return this;
		}
	}

}
