package mr.model;

import java.util.List;

import mr.model.misc.Coordinate;
import mr.model.state.IState;

public class Trigger extends Item {
	private List<Event> events;

	public Trigger(Coordinate position, Coordinate size, String type, String id, IState state, List<Event> events) {
		super(position, size, type, id, state);
		this.setEvents(events);
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
