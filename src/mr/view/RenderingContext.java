package mr.view;

import java.util.List;

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
}
