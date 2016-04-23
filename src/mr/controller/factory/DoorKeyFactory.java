package mr.controller.factory;

import java.util.ArrayList;
import java.util.List;

import mr.controller.ModelHandler;
import mr.controller.colliders.AbstractMovableCollider;
import mr.controller.entity.Door;
import mr.controller.entity.Key;
import mr.model.Event;
import mr.model.Trigger;
import mr.model.misc.Coordinate;
import mr.model.state.Idle;

public class DoorKeyFactory {
	public enum Color {
		red;

		public String getDoorModel() {
			return this.toString()+"Door";
		}

		public String getKeyModel() {
			return this.toString()+"Key";
		}
	};

	public static List<AbstractMovableCollider> getNewDoorKey(Color type, Coordinate PosDoor, Coordinate PosKey) {
		List<AbstractMovableCollider> colliders = new ArrayList<AbstractMovableCollider>();

		Trigger trigger = new Trigger(new ArrayList<Event>());
		Event event = new Event("id");
		trigger.getEvents().add(event);
		colliders.add(buildKey(type.getKeyModel(), type.getKeyModel(), PosKey, trigger));
		colliders.add(buildDoor(type.getDoorModel(), type.getDoorModel(), PosDoor, event));

		return colliders;
	}

	private static Key buildKey(String id, String model, Coordinate pos, Trigger trigger) {
		return new Key(pos,
				ModelHandler.get().getModel(model),
				id,
				new Idle(true),
				trigger);
	}

	private static Door buildDoor(String id, String model, Coordinate pos, Event event) {
		return new Door(pos,
				ModelHandler.get().getModel(model),
				id,
				new Idle(true),
				event);
	}
}
