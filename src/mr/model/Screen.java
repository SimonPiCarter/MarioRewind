package mr.model;

import mr.controller.EntityHandler;

public class Screen {
	private Screen top, bottom, left, right;
	private int[] tiles;
	private EntityHandler handler;
	private int nbTiles;

	public Screen(Screen top, Screen bottom, Screen left, Screen right, int[] tiles) {
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.tiles = tiles;
	}
	public Screen getTop() {
		return top;
	}
	public void setTop(Screen top) {
		this.top = top;
	}
	public Screen getBottom() {
		return bottom;
	}
	public void setBottom(Screen bottom) {
		this.bottom = bottom;
	}
	public Screen getLeft() {
		return left;
	}
	public void setLeft(Screen left) {
		this.left = left;
	}
	public Screen getRight() {
		return right;
	}
	public void setRight(Screen right) {
		this.right = right;
	}
	public int[] getTiles() {
		return tiles;
	}
	public void setTiles(int[] tiles) {
		this.tiles = tiles;
	}
	public EntityHandler getHandler() {
		return handler;
	}
	public void setHandler(EntityHandler handler) {
		this.handler = handler;
	}
	public int getNbTiles() {
		return nbTiles;
	}
	public void setNbTiles(int nbTiles) {
		this.nbTiles = nbTiles;
	}


}
