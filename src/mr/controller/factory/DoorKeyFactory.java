package mr.controller.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mr.controller.ModelHandler;
import mr.controller.colliders.AbstractMovableCollider;
import mr.controller.entity.Door;
import mr.controller.entity.Key;
import mr.core.exception.FormatLevelException;
import mr.model.Event;
import mr.model.Trigger;
import mr.model.misc.Coordinate;
import mr.model.state.Idle;

public class DoorKeyFactory {
	public enum Color {
		red,
		green,
		blue;

		public String getDoorModel() {
			return this.toString()+"Door";
		}

		public String getKeyModel() {
			return this.toString()+"Key";
		}
	};

	private final Map<Color, List<Event> > eventByColor;
	private final Map<Color, Trigger > triggerByColor;

	public DoorKeyFactory() {
		this.eventByColor = new HashMap<>();
		this.triggerByColor = new HashMap<>();
	}

	public Key getNewKey(Color type, Coordinate pos) throws FormatLevelException {
		if ( triggerByColor.containsKey(type) ) {
			throw new FormatLevelException("Error : tried to create a second key of the color : "+type);
		}
		if ( !eventByColor.containsKey(type) ) {
			eventByColor.put(type, new ArrayList<Event>());
		}
		Trigger trigger = new Trigger(eventByColor.get(type));
		triggerByColor.put(type, trigger);
		return buildKey(type.getKeyModel(), type.getKeyModel(), pos, trigger);
	}

	public Door getNewDoor(Color type, Coordinate pos) {
		if ( !eventByColor.containsKey(type) ) {
			eventByColor.put(type, new ArrayList<Event>());
		}
		Event event = new Event(type.getDoorModel()+eventByColor.get(type).size());
		eventByColor.get(type).add(event);
		return buildDoor(type.getDoorModel(), type.getDoorModel(), pos, event);
	}

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
