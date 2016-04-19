package mr.controller.movable;

import mr.model.GameConstant;
import mr.model.Item;
import mr.model.state.IState.StateEvent;

public class ItemMovable extends AbstractMovable {

	private final Item item;

	public ItemMovable(Item sprite) {
		super();
		this.item = sprite;
	}

	@Override
	public Item getMovable() {
		return item;
	}

	public void updateState() {
		if ( getSpeed().x > GameConstant.epsilon ) {
			item.updateState(StateEvent.MoveRight);
		} else if ( getSpeed().x < GameConstant.epsilon ) {
			item.updateState(StateEvent.MoveLeft);
		} else {
			item.updateState(StateEvent.Stop);
		}
		if ( getSpeed().y > GameConstant.epsilon ) {
			item.updateState(StateEvent.Fall);
		} else if ( getSpeed().y < GameConstant.epsilon ) {
			item.updateState(StateEvent.Jump);
		}
	}
}
