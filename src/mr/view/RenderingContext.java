package mr.view;

import java.util.ArrayList;
import java.util.List;

import mr.model.GameConstant;

public class RenderingContext {
	private List<List<RenderingImage>> layersImages;

	public RenderingContext() {
		layersImages = new ArrayList<List<RenderingImage>>();
	}

	public List<List<RenderingImage>> getLayersImages() {
		return layersImages;
	}

	public void addToLayer(GameConstant.Layers layer, RenderingImage... images) {
		setUpLayer(layersImages,layer);
		for ( RenderingImage image : images ) {
			if ( image != null ) {
				layersImages.get(layer.ordinal()).add(image);
			}
		}
	}

	public void clearLayer(GameConstant.Layers layer) {
		if ( layersImages.size() > layer.ordinal() && layersImages.get(layer.ordinal()) != null ) {
			layersImages.get(layer.ordinal()).clear();
		}
	}

	private <T> void setUpLayer(List<List<T>> layers, GameConstant.Layers layer) {
		while ( layers.size() <= layer.ordinal() ) {
			layers.add(new ArrayList<T>());
		}
	}
}
