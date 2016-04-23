package mr.view;

import mr.core.exception.FormatModelException;
import mr.model.misc.Coordinate;

public class RenderingImageLoader {

	/**
	 * Load a rendering image from a scanner line file formatted as follow :
	 * Path to image t x y m_0...m_n(t = frame time in ms & x,y = size of frame & number of frames per state (n = number of states)
	 * @param path
	 * @return
	 * @throws FormatModelException
	 */
	public static RenderingImage LoadRenderingImage(String[] strings) throws FormatModelException {
		RenderingImage image = null;
		try {
			String pathToImage = strings[0];
			int frameTime = Integer.parseInt(strings[1]);
			float sizeX = Integer.parseInt(strings[2]);
			float sizeY = Integer.parseInt(strings[3]);
			int nbStates = strings.length-4;
			int nbFrames[] = new int[nbStates];
			for ( int i = 0 ; i < nbStates ; ++ i ) {
				nbFrames[i] = Integer.parseInt(strings[4+i]);
			}

			image = new RenderingImage(new Coordinate(), new Coordinate(sizeX, sizeY), pathToImage);
			image.setFrametime(frameTime);
			image.setSizeSrc(new Coordinate(sizeX,sizeY));
			image.setNbStates(nbStates);
			image.setNbFrames(nbFrames);
		}
		catch (NumberFormatException e) {
			StringBuilder builder = new StringBuilder();
			builder.append(strings);
			throw new FormatModelException("Wrong pattern while reading sprite model :"+builder.toString());
		}

		return image;
	}
}
