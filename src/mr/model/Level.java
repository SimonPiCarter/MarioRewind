package mr.model;

import java.util.ArrayList;

public class Level {
	private ArrayList<String> tiles;
	private Screen startingScreen;

	public Level(ArrayList<String> tiles, Screen startingScreen) {
		this.tiles = tiles;
		this.startingScreen = startingScreen;
	}
	public ArrayList<String> getTiles() {
		return tiles;
	}
	public void setTiles(ArrayList<String> tiles) {
		this.tiles = tiles;
	}

	public Screen getStartingScreen() {
		return startingScreen;
	}
	public void setStartingScreen(Screen startingScreen) {
		this.startingScreen = startingScreen;
	}
}
