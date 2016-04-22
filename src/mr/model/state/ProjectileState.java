package mr.model.state;

public class ProjectileState extends AbstractState {

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
		super(type.ordinal());
		this.type = type;
		this.dying = false;
	}

	@Override
	public AbstractState handleEvent(StateEvent event) {
		if ( event == StateEvent.Die ) {
			dying = true;
		}
		return this;
	}

}
