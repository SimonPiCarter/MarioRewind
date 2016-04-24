package mr.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mr.controller.entity.Enemy;
import mr.controller.entity.Hero;
import mr.controller.entity.Trap;
import mr.controller.factory.DoorKeyFactory;
import mr.core.exception.FormatLevelException;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.Level;
import mr.model.Screen;
import mr.model.misc.Coordinate;
import mr.model.state.Idle;
import mr.model.state.trap.TrapUp;

public class LevelLoader {

	/***
	 * Read a file with this format :<br>
	 * <ul>
	 * <li>nbTilesType
	 * <li>Tile1(path)
	 * <li>Tile2
	 * <li>...
	 * <li>screens infos line 1
	 * <li>screens infos line 2
	 * <li>screens infos line...
	 * </ul>
	 * @param path
	 * @return
	 * @throws InputFileNotFoundException
	 * @throws FormatLevelException
	 */
	public static Level loadLevel(String path) throws InputFileNotFoundException {

		Path pathFile = Paths.get(path);
		Scanner scanner = null;
		Level level = new Level();
		try {
			scanner = new Scanner(pathFile, GameConstant.ENCODING.name());

			if ( scanner != null && scanner.hasNext() ) {

				// Load hero into the level
				level.setHero(new Hero(
						new Coordinate(),
						ModelHandler.get().getHeroModel("hero"),
						"hero",
						new Idle(true)));

				loadTilesAndModels(scanner,level);
				Screen startingScreen = loadScreens(scanner, level);


				level.setStartingScreen(startingScreen);
				scanner.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new InputFileNotFoundException("File not found "+path);
		} catch (FormatLevelException e) {
			e.printStackTrace();
			throw new InputFileNotFoundException("File not valid "+path);
		} finally {
			scanner.close();
		}

		return level;
	}

	private static void loadTilesAndModels(Scanner scanner, Level level) {
		int nbTiles = scanner.nextInt();
		int nbEnemies = scanner.nextInt();
		int nbTrap = scanner.nextInt();
		int nbDoorKey = scanner.nextInt();
		scanner.nextLine();
		// Scan all tiles names in right order
		for ( int i = 0 ; i < nbTiles && scanner.hasNextLine() ; ++ i ) {
			level.getTiles().add(scanner.nextLine());
		}
		// Scan all enemy models in right order
		for ( int i = 0 ; i < nbEnemies && scanner.hasNextLine() ; ++ i ) {
			level.getEnemyModels().add(ModelHandler.get().getEnemyModel(scanner.nextLine()));
		}
		// Scan all trap model in right order
		for ( int i = 0 ; i < nbTrap && scanner.hasNextLine() ; ++ i ) {
			level.getTrapModels().add(ModelHandler.get().getModel(scanner.nextLine()));
		}
		// Scan all trap model in right order
		for ( int i = 0 ; i < nbDoorKey && scanner.hasNextLine() ; ++ i ) {
			level.getDoorModels().add(DoorKeyFactory.Color.valueOf(scanner.nextLine()));
		}
		// Scan all trap model in right order
		for ( int i = 0 ; i < nbDoorKey && scanner.hasNextLine() ; ++ i ) {
			level.getKeyModels().add(DoorKeyFactory.Color.valueOf(scanner.nextLine()));
		}
	}

	private static Screen loadScreens(Scanner scanner, Level level) throws FormatLevelException {
		List<String[]> screensData = new ArrayList<String[]>();
		Screen startingScreen = null;
		// Read screens information
		while ( scanner.hasNextLine() ) {
			screensData.add(scanner.nextLine().split(" "));
		}
		startingScreen = buildScreens(screensData, level);

		return startingScreen;
	}



	private static Screen buildScreens(List<String[]> screensData, Level level) throws FormatLevelException {
		if ( screensData.size()%GameConstant.HEIGHT != 0 ) {
			throw new FormatLevelException("Level format is unrecognized, wrong pattern for screens tiles");
		}

		int max = 0;
		for ( int y = 0 ; y  < screensData.size() ; ++ y ) {
			String[] curLine = screensData.get(y);
			if ( curLine.length%GameConstant.WIDTH != 0 ) {
				throw new FormatLevelException("Level format is unrecognized, wrong pattern for screens tiles");
			}
			if ( curLine.length/GameConstant.WIDTH > max ) {
				max = curLine.length/GameConstant.WIDTH;
			}
		}

		Screen startingScreen = null;

		int nbScreensX = max;
		int nbScreensY = screensData.size()/GameConstant.HEIGHT;
		Screen[][] screens = allocateScreens(nbScreensX,nbScreensY, level);

		for ( int i = 0 ; i < nbScreensX ; ++ i ) {
			for ( int j = 0 ; j < nbScreensY ; ++ j ) {
				if ( loadScreen(screensData,screens,i,j, level) ) {
					startingScreen = screens[i][j];
				}
			}
		}

		if ( startingScreen == null ) {
			throw new FormatLevelException("The level does not contain a starting screen");
		}


		return startingScreen;
	}

	private static boolean loadScreen(List<String[]> screensData, Screen[][] screens, int i, int j, Level level) throws FormatLevelException {
		int[] tiles = null;
		int offsetX = i*GameConstant.WIDTH;
		int offsetY = j*GameConstant.HEIGHT;

		boolean startingScreen = false;
		EntityHandler handler = new EntityHandler(level.getHero());

		if ( screensData.get(offsetY) != null && screensData.get(offsetY).length > offsetX ) {
			tiles =  new int[GameConstant.WIDTH*GameConstant.HEIGHT];
			for ( int k = 0 ; k < GameConstant.WIDTH*GameConstant.HEIGHT ; ++ k ) {
				int x = k % GameConstant.WIDTH;
				int y = (k-x)/GameConstant.WIDTH;
				tiles[k] = Integer.parseInt(screensData.get(offsetY+y)[offsetX+x]);
				if ( tiles[k] == -1 ) {
					startingScreen = true;
					level.getHero().setPosition(new Coordinate(x*GameConstant.TILE_SIZE,y*GameConstant.TILE_SIZE));
				}
				if ( tiles[k] >= level.getTiles().size() ) {
					loadEntity(handler, tiles[k], x, y, level);
				}

			}
		}

		screens[i][j].setHandler(handler);

		// If current screen is not empty
		if ( tiles  != null ) {
			screens[i][j].setTiles(tiles);
			// Check left screen
			if ( i > 0 ) {
				// If its not an empty screen we update the internal links
				if ( screens[i-1][j].getTiles() != null ) {
					screens[i-1][j].setRight(screens[i][j]);
					screens[i][j].setLeft(screens[i-1][j]);
				}
			}
			// Check top screen
			if ( j > 0 ) {
				// If its not an empty screen we update the internal links
				if ( screens[i][j-1].getTiles() != null ) {
					screens[i][j-1].setBottom(screens[i][j]);
					screens[i][j].setTop(screens[i-1][j]);
				}
			}
		}

		return startingScreen;
	}

	private static void loadEntity(EntityHandler handler, int index, int x, int y, Level level) throws FormatLevelException {
		int tmpIndex = index-level.getTiles().size()-1;
		if ( tmpIndex >= 0 ) {
			if ( tmpIndex < level.getEnemyModels().size() ) {
				handler.addEnemy(new Enemy(
						new Coordinate(x*GameConstant.TILE_SIZE,y*GameConstant.TILE_SIZE),
						level.getEnemyModels().get(tmpIndex),
						level.getEnemyModels().get(tmpIndex).getId(),
						new Idle(true)));
			} else {
				tmpIndex = tmpIndex-level.getEnemyModels().size();
				if ( tmpIndex < level.getTrapModels().size() ) {
					handler.addCollider(new Trap(
							new Coordinate(x*GameConstant.TILE_SIZE,y*GameConstant.TILE_SIZE),
							level.getTrapModels().get(tmpIndex),
							level.getTrapModels().get(tmpIndex).getId(),
							new TrapUp()));
				} else {
					tmpIndex = tmpIndex-level.getTrapModels().size();
					if ( tmpIndex < level.getDoorModels().size() ) {
						handler.addCollider(level.getDoorKeyFactory().getNewDoor(
								level.getDoorModels().get(tmpIndex),
								new Coordinate(x*GameConstant.TILE_SIZE,y*GameConstant.TILE_SIZE)));
					} else {
						tmpIndex = tmpIndex-level.getDoorModels().size();
						if ( tmpIndex < level.getKeyModels().size() ) {
							handler.addCollider(level.getDoorKeyFactory().getNewKey(
									level.getKeyModels().get(tmpIndex),
									new Coordinate(x*GameConstant.TILE_SIZE,y*GameConstant.TILE_SIZE)));
						}
					}
				}
			}
		}
	}

	private static Screen[][] allocateScreens(int nbScreensX, int nbScreensY, Level level) {
		Screen[][] screens = new Screen[nbScreensX][nbScreensY];
		for ( int i = 0 ; i < nbScreensX ; ++ i ) {
			for ( int j = 0 ; j < nbScreensY ; ++ j ) {
				screens[i][j] = new Screen(null,null,null,null,null);
				screens[i][j].setNbTiles(level.getTiles().size());
			}
		}
		return screens;
	}
}
