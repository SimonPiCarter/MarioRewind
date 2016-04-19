package mr.model.state;

public class Die implements IState {

	public final boolean right;

	public Die(boolean right) {
		this.right = right;
	}

	@Override
	public IState handleEvent(StateEvent event) {
		switch (event) {
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

	@Override
	public int getState() {
		int off = right?0:1;
		return 8+off;
	}
}
