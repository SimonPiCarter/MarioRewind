package mr.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mr.core.exception.FormatLevelException;
import mr.core.exception.InputFileNotFoundException;
import mr.model.Level;
import mr.model.Screen;

public class LevelLoader {
	private final static int WIDTH = 20;
	private final static int HEIGHT = 20;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	
	public static Level loadLevel(String path) throws InputFileNotFoundException, FormatLevelException {
		
		Path pathFile = Paths.get(path);
		Scanner scanner = null;
		try {
			scanner = new Scanner(pathFile, ENCODING.name());
		} catch (IOException e) {
			throw new InputFileNotFoundException("File not found "+path);
		}
	    
	    Level level = null;
	    Screen startingScreen = null;
	    ArrayList<String> tiles = new ArrayList<String>();
	    List<String[]> screensData = new ArrayList<String[]>();
	    
		if ( scanner != null && scanner.hasNext() ) {
			int nbTiles = scanner.nextInt();
			// Scan all tiles names in right order
			for ( int i = 0 ; i < nbTiles && scanner.hasNextLine() ; ++ i ) {
				tiles.add(scanner.nextLine());
			}
			// Read screens information
			while ( scanner.hasNextLine() ) {
				screensData.add(scanner.nextLine().split(" "));
			}
			startingScreen = buildScreens(screensData);
			
			level = new Level(tiles,startingScreen);
			scanner.close();
		}
		else {
			scanner.close();
			throw new InputFileNotFoundException("File not valid "+path);
		}
		
		return level;
	}

	private static Screen buildScreens(List<String[]> screensData) throws FormatLevelException {
		if ( screensData.size()%HEIGHT != 0 ) {
			throw new FormatLevelException("Level format is unrecognized, wrong pattern for screens tiles");
		}
		Screen[] upScreens = null;
		Screen[] curScreens = null;
		
		
		int max = 0;
		for ( int y = 0 ; y  < screensData.size() ; ++ y ) {
			String[] curLine = screensData.get(y);
			if ( curLine.length%WIDTH != 0 ) {
				throw new FormatLevelException("Level format is unrecognized, wrong pattern for screens tiles");
			}
			if ( curLine.length/WIDTH > max ) {
				max = curLine.length/WIDTH;
			}
		}

		int nbScreensX = max;
		int nbScreensY = screensData.size()/HEIGHT;
		Screen[][] screens = allocateScreens(nbScreensX,nbScreensY);
		
		for ( int i = 0 ; i < nbScreensX ; ++ i ) {
			for ( int j = 0 ; j < nbScreensY ; ++ j ) {
				loadScreen(screensData,screens,i,j);
			}
		}
		
		
		Screen startingScreen = new Screen(null, null, null, null, null);
		
		return startingScreen;
	}

	private static void loadScreen(List<String[]> screensData, Screen[][] screens, int i, int j) {
		int[] tiles = null;
		
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
		
	}

	private static Screen[][] allocateScreens(int nbScreensX, int nbScreensY) {
		Screen[][] screens = new Screen[nbScreensX][nbScreensY];
		for ( int i = 0 ; i < nbScreensX ; ++ i ) {
			for ( int j = 0 ; j < nbScreensY ; ++ j ) {
				screens[i][j] = new Screen(null,null,null,null,null);
			}
		}
		return null;
	}
}
