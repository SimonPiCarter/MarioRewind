package mr.controller;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import mr.controller.entity.Hero;
import mr.model.misc.Coordinate;

public class Rewinder {
	private static final double globalLimit = 750;
	private static final double minTime = 4;
	private static final int timeStep = 5;
	private double globalTime;
	private int elapsedTime;

	private Deque<Double> deltas;
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

	public void rewind(int delta, Hero hero) {
		if ( !deltas.isEmpty() ) {
			elapsedTime += delta;
			while ( !deltas.isEmpty() && elapsedTime > timeStep ) {
				double lastDelta = deltas.pollLast();
				elapsedTime -= timeStep;
				Coordinate oldSpeed = speeds.pollLast();
				hero.getSpeed().x = -oldSpeed.x;
				hero.getSpeed().y = -oldSpeed.y;
				Coordinate oldPos = positions.pollLast();
				hero.getPosition().x = oldPos.x;
				hero.getPosition().y = oldPos.y;
				points.pollLast();
				globalTime -= lastDelta;
			}
			hero.setBacktrack(Math.max(0, hero.getBacktrack()-delta/globalLimit));
		}
	}

	private double computeSquareDistance(Coordinate A, Coordinate B) {
		return (A.x-B.x)*(A.x-B.x) + (A.y-B.y)*(A.y-B.y);
	}

	public void record(int delta, Hero hero) {
		double newDelta = 0;
		if ( !deltas.isEmpty() && !positions.isEmpty() ) {
			newDelta = computeSquareDistance(positions.peekLast(), hero.getPosition());
			double olderDelta = deltas.peekFirst();
			if ( globalTime - olderDelta + newDelta > globalLimit ) {
				globalTime -= olderDelta;
				deltas.removeFirst();
				positions.removeFirst();
				speeds.removeFirst();
				points.removeFirst();
			}
		}

		if ( deltas.isEmpty() || newDelta > minTime ) {
			deltas.addLast(newDelta);
			positions.addLast(new Coordinate(hero.getPosition()));
			speeds.addLast(new Coordinate(hero.getSpeed()));
			points.addLast(new Coordinate(hero.getPosition().x+hero.getSize().x/2,
					hero.getPosition().y+hero.getSize().y/2));
			globalTime += newDelta;
		}
	}

	public Collection<Coordinate> getPoints() {
		return points;
	}



}
