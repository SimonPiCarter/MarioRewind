package mr.model.state;

public class Die extends AbstractState {

	public final boolean right;

	public Die(boolean right) {
		super(8+(right?0:1),false);
		this.right = right;
	}

	@Override
	public AbstractState handleEvent(StateEvent event) {
		switch (event) {
		case EndDie:
			return new Idle(right);
		case Die:
		case Fall:
		case Jump:
		case MoveLeft:
		case MoveRight:
		case Stop:
		case None:
		default:
			return this;
		}
	}
}
