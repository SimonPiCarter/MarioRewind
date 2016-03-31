package mr.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mr.core.exception.FormatLevelException;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.Level;
import mr.model.Screen;

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
		Level level = null;
		try {
			scanner = new Scanner(pathFile, GameConstant.ENCODING.name());

			if ( scanner != null && scanner.hasNext() ) {
				ArrayList<String> tiles = loadTiles(scanner);
				Screen startingScreen = loadScreens(scanner);


				level = new Level(tiles,startingScreen);
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

	private static ArrayList<String> loadTiles(Scanner scanner) {
		int nbTiles = scanner.nextInt();
		ArrayList<String> tiles = new ArrayList<String>();
		scanner.nextLine();
		// Scan all tiles names in right order
		for ( int i = 0 ; i < nbTiles && scanner.hasNextLine() ; ++ i ) {
			tiles.add(scanner.nextLine());
		}
		return tiles;
	}

	private static Screen loadScreens(Scanner scanner) throws FormatLevelException {
		List<String[]> screensData = new ArrayList<String[]>();
		Screen startingScreen = null;
		// Read screens information
		while ( scanner.hasNextLine() ) {
			screensData.add(scanner.nextLine().split(" "));
		}
		startingScreen = buildScreens(screensData);

		return startingScreen;
	}



	private static Screen buildScreens(List<String[]> screensData) throws FormatLevelException {
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
		Screen[][] screens = allocateScreens(nbScreensX,nbScreensY);

		for ( int i = 0 ; i < nbScreensX ; ++ i ) {
			for ( int j = 0 ; j < nbScreensY ; ++ j ) {
				if ( loadScreen(screensData,screens,i,j) ) {
					startingScreen = screens[i][j];
				}
			}
		}

		if ( startingScreen == null ) {
			throw new FormatLevelException("The level does not contain a starting screen");
		}


		return startingScreen;
	}

	private static boolean loadScreen(List<String[]> screensData, Screen[][] screens, int i, int j) {
		int[] tiles = null;
		int offsetX = i*GameConstant.WIDTH;
		int offsetY = j*GameConstant.HEIGHT;

		boolean startingScreen = false;

		if ( screensData.get(offsetY) != null && screensData.get(offsetY).length > offsetX ) {
			tiles =  new int[GameConstant.WIDTH*GameConstant.HEIGHT];
			for ( int k = 0 ; k < GameConstant.WIDTH*GameConstant.HEIGHT ; ++ k ) {
				int x = k % GameConstant.WIDTH;
				int y = (k-x)/GameConstant.WIDTH;
				tiles[k] = Integer.parseInt(screensData.get(offsetY+y)[offsetX+x]);
				if ( tiles[k] == -1 ) {
					startingScreen = true;
				}
			}
		}

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

	private static Screen[][] allocateScreens(int nbScreensX, int nbScreensY) {
		Screen[][] screens = new Screen[nbScreensX][nbScreensY];
		for ( int i = 0 ; i < nbScreensX ; ++ i ) {
			for ( int j = 0 ; j < nbScreensY ; ++ j ) {
				screens[i][j] = new Screen(null,null,null,null,null);
			}
		}
		return screens;
	}
}
