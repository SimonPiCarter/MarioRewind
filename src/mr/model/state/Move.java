package mr.model.state;

public class Move extends AbstractState {

	public final boolean right;

	public Move(boolean right) {
		super(2+(right?0:1));
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
			if ( !right ) {
				return this;
			} else {
				return new Move(false);
			}
		case MoveRight:
			if ( right ) {
				return this;
			} else {
				return new Move(true);
			}
		case Stop:
			return new Idle(right);
		case None:
		default:
			return this;
		}
	}

}
