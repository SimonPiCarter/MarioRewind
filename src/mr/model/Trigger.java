package mr.model;

import java.util.List;

public class Trigger {
	private List<Event> events;

	public Trigger(List<Event> events) {
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
