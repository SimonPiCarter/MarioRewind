package mr.model;

import java.util.ArrayList;

import mr.controller.entity.Hero;
import mr.controller.factory.DoorKeyFactory;
import mr.model.model.EnemyModel;
import mr.model.model.Model;

public class Level {
	private ArrayList<String> tiles;
	private Hero hero;
	private ArrayList<EnemyModel> enemyModels;
	private ArrayList<Model> trapModels;
	private ArrayList<DoorKeyFactory.Color> keyModels;
	private ArrayList<DoorKeyFactory.Color> DoorModels;
	private Screen startingScreen;

	private DoorKeyFactory doorKeyFactory;

	public Level() {
		tiles = new ArrayList<>();
		enemyModels = new ArrayList<>();
		trapModels = new ArrayList<>();
		keyModels = new ArrayList<>();
		DoorModels = new ArrayList<>();
		doorKeyFactory = new DoorKeyFactory();
	}
	public ArrayList<String> getTiles() {
		return tiles;
	}
	public void setTiles(ArrayList<String> tiles) {
		this.tiles = tiles;
	}
	public Hero getHero() {
		return hero;
	}
	public void setHero(Hero hero) {
		this.hero = hero;
	}
	public ArrayList<EnemyModel> getEnemyModels() {
		return enemyModels;
	}
	public void setEnemyModels(ArrayList<EnemyModel> enemyModels) {
		this.enemyModels = enemyModels;
	}
	public ArrayList<Model> getTrapModels() {
		return trapModels;
	}
	public void setTrapModels(ArrayList<Model> trapModels) {
		this.trapModels = trapModels;
	}
	public ArrayList<DoorKeyFactory.Color> getKeyModels() {
		return keyModels;
	}
	public void setKeyModels(ArrayList<DoorKeyFactory.Color> keyModels) {
		this.keyModels = keyModels;
	}
	public ArrayList<DoorKeyFactory.Color> getDoorModels() {
		return DoorModels;
	}
	public void setDoorModels(ArrayList<DoorKeyFactory.Color> doorModels) {
		DoorModels = doorModels;
	}

	public Screen getStartingScreen() {
		return startingScreen;
	}
	public void setStartingScreen(Screen startingScreen) {
		this.startingScreen = startingScreen;
	}
	public DoorKeyFactory getDoorKeyFactory() {
		return doorKeyFactory;
	}
}
