package mr.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mr.model.GameConstant;

public class RenderingContext {
	private List<List<RenderingImage>> layers;


	public RenderingContext(List<List<RenderingImage>> layers) {
		super();
		this.layers = layers;
	}

	public List<List<RenderingImage>> getLayers() {
		return layers;
	}

	public void setLayers(List<List<RenderingImage>> layers) {
		this.layers = layers;
	}

	public void addToLayer(GameConstant.Layers layer, Collection<RenderingImage> images) {
		if ( layers == null ) {
			layers = new ArrayList<List<RenderingImage>>(layer.ordinal());
		}
		while ( layers.size() < layer.ordinal() ) {
			layers.add(new ArrayList<RenderingImage>());
		}
		layers.get(layer.ordinal()).addAll(images);
	}

	public void addToLayer(GameConstant.Layers layer, RenderingImage images) {
		if ( layers == null ) {
			layers = new ArrayList<List<RenderingImage>>(layer.ordinal());
		}
		while ( layers.size() <= layer.ordinal() ) {
			layers.add(new ArrayList<RenderingImage>());
		}
		layers.get(layer.ordinal()).add(images);
	}

	public void clearLayer(GameConstant.Layers layer) {
		if ( layers.size() > layer.ordinal() && layers.get(layer.ordinal()) != null ) {
			layers.get(layer.ordinal()).clear();
		}
	}
}
