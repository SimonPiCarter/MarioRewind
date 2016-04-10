package mr.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.misc.Coordinate;
import mr.view.RenderingImage;

public class RenderingImageLoader {



	/**
	 * Load a rendering image from a text file formatted as follow :
	 * Path to image
	 * t x y n(t = frame time in ms & x,y = size of frame & n = number of states
	 * m_i (number of frames per state)
	 * @param path
	 * @return
	 * @throws InputFileNotFoundException
	 */
	public static RenderingImage LoadRenderingImage(String path) throws InputFileNotFoundException {

		Path pathFile = Paths.get(path);
		Scanner scanner = null;
		RenderingImage level = null;
		try {
			scanner = new Scanner(pathFile, GameConstant.ENCODING.name());

			if ( scanner != null && scanner.hasNext() ) {
				String pathToImage = scanner.nextLine();
				int frameTime = scanner.nextInt();
				float sizeX = scanner.nextInt();
				float sizeY = scanner.nextInt();
				int nbStates = scanner.nextInt();
				int nbFrames[] = new int[nbStates];
				for ( int i = 0 ; i < nbStates ; ++ i ) {
					nbFrames[i] = scanner.nextInt();
				}

				level = new RenderingImage(new Coordinate(), new Coordinate(sizeX, sizeY), pathToImage);
				level.setFrametime(frameTime);
				level.setSizeSrc(new Coordinate(sizeX,sizeY));
				level.setNbStates(nbStates);
				level.setNbFrames(nbFrames);

				scanner.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new InputFileNotFoundException("File not found "+path);
		} finally {
			scanner.close();
		}

		return level;
	}
}
