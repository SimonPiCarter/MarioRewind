package mr.controller;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import mr.controller.movable.HeroMovable;
import mr.model.misc.Coordinate;

public class Rewinder {
	private static final int globalLimit = 2500;
	private static final int minTime = 25;
	private static final double rewindFactor = 1.5;
	private int globalTime;
	private int elapsedTime;

	private Deque<Integer> deltas;
	private Deque<Coordinate> positions;
	private Deque<Coordinate> speeds;
	private Deque<Coordinate> points;



	public Rewinder() {
		this.globalTime = 0;
		this.elapsedTime = 0;
		this.deltas = new ArrayDeque<>();
		this.positions = new ArrayDeque<>();
		this.speeds = new ArrayDeque<>();
		this.points = new ArrayDeque<>();
	}

	public void rewind(int delta, HeroMovable hero) {
		if ( !deltas.isEmpty() ) {
			elapsedTime -= rewindFactor*delta;
			while ( !deltas.isEmpty() && elapsedTime < -deltas.peekLast() ) {
				int lastDelta = deltas.pollLast();
				elapsedTime += lastDelta;
				Coordinate oldSpeed = speeds.pollLast();
				hero.getSpeed().x = -oldSpeed.x;
				hero.getSpeed().y = -oldSpeed.y;
				Coordinate oldPos = positions.pollLast();
				hero.getMovable().getPosition().x = oldPos.x;
				hero.getMovable().getPosition().y = oldPos.y;
				points.pollLast();
				globalTime -= lastDelta;
			}
			hero.getMovable().setBacktrack(Math.max(0, hero.getMovable().getBacktrack()-(double)delta/globalLimit));
		}
	}

	public void record(int delta, HeroMovable hero) {
		if ( !deltas.isEmpty() && !positions.isEmpty() ) {
			Integer olderDelta = deltas.peekFirst();
			if ( globalTime - olderDelta + elapsedTime + delta > globalLimit ) {
				globalTime -= olderDelta;
				deltas.removeFirst();
				positions.removeFirst();
				speeds.removeFirst();
				points.removeFirst();
			}
		}
		if ( elapsedTime + delta > minTime ) {
			deltas.addLast(elapsedTime + delta);
			positions.addLast(new Coordinate(hero.getMovable().getPosition()));
			speeds.addLast(new Coordinate(hero.getSpeed()));
			points.addLast(new Coordinate(hero.getMovable().getPosition().x+hero.getMovable().getSize().x/2,
					hero.getMovable().getPosition().y+hero.getMovable().getSize().y/2));
			globalTime += elapsedTime + delta;
			elapsedTime = 0;
		} else {
			elapsedTime += delta;
		}
	}

	public Collection<Coordinate> getPoints() {
		return points;
	}



}
