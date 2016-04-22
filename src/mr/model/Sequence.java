package mr.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Sequence {
	private List<Integer> states;
	private ListIterator<Integer> it;
	private Integer current;
	private boolean loop;
	private boolean over;

	public Sequence() {
		states = new ArrayList<Integer>();
	}

	public List<Integer> getStates() {
		return states;
	}
	public void setStates(List<Integer> states) {
		this.states = states;
	}
	public boolean isLoop() {
		return loop;
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	public boolean isOver() {
		return over;
	}
	public void setOver(boolean over) {
		this.over = over;
	}

	public void initSequence() {
		it = states.listIterator();
		if ( it.hasNext() ) {
			current = it.next();
			over = false;
		} else {
			over = true;
		}
	}

	public boolean hasNext() {
		return it.hasNext();
	}

	public Integer next() {
		current = it.next();
		return current;
	}

	public Integer current() {
		return current;
	}
}
