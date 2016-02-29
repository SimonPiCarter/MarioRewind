package mr.model;

public class Event {
	private String id;
	private boolean triggered;
	
	public Event(String id) {
		this.id = id;
		this.triggered = false;
	}
	
	public Event(String id, boolean triggered) {
		this.id = id;
		this.triggered = triggered;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public boolean isTriggered() {
		return triggered;
	}
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}
}
