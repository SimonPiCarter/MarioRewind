package mr.model;

import java.util.List;

import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.IState;

public class Trigger extends Item {
	private List<Event> events;

	public Trigger(Coordinate position, Model model, String id, IState state, List<Event> events) {
		super(position, model, id, state);
		this.setEvents(events);
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void trigger() {
		for ( Event event : events ) {
			event.notifyTriggered(this);
		}
	}
}
