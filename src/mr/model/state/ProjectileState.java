package mr.model.state;

public class ProjectileState implements IState {

	public enum Type {
		Up,
		UpLeft,
		Left,
		DownLeft,
		Down,
		DownRight,
		Right,
		UpRight
	};

	private final Type type;
	private boolean dying;

	public ProjectileState(Type type) {
		this.type = type;
		this.dying = false;
	}

	@Override
	public IState handleEvent(StateEvent event) {
		if ( event == StateEvent.Die ) {
			dying = true;
		}
		return this;
	}

	@Override
	public int getState() {
		return type.ordinal()+ (dying?Type.values().length:0);
	}

}
