package mr.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mr.model.GameConstant;
import mr.model.Item;
import mr.model.Sequence;
import mr.model.Sprite;
import mr.model.misc.Coordinate;

public class RenderingContext {
	private List<List<RenderingImage>> layersImages;
	private List<Map<Item,RenderingImage>> layersItems;
	private List<Map<Sprite,RenderingImage>> layersSprites;

	public RenderingContext() {
		layersImages = new ArrayList<List<RenderingImage>>();
		layersItems = new ArrayList<Map<Item,RenderingImage>>();
		layersSprites = new ArrayList<Map<Sprite,RenderingImage>>();
	}

	public void update(int delta) {
		for ( Map<Item,RenderingImage> layer : layersItems ) {
			for ( Entry<Item, RenderingImage> entry : layer.entrySet() ) {
				update(entry.getKey(),entry.getValue(),delta);
			}
		}
		for ( Map<Sprite,RenderingImage> layer : layersSprites ) {
			for ( Entry<Sprite, RenderingImage> entry : layer.entrySet() ) {
				update(entry.getValue(), entry.getKey().getState(), delta);
			}
		}
	}

	private void update(Item item, RenderingImage image, int delta) {
		if ( item.getSequence().current() == null ) {
			item.getSequence().initSequence();
		}
		if ( item.getSequence().current() != null && item.getSequence().current() < image.getNbStates() ) {
			// If change of state
			if ( Math.abs(item.getSequence().current()*image.getSizeSrc().getY()-image.getStartSrc().getY()) > 1e-3f ) {
				image.setFrame(0);
				image.setTime(0);
			}
			update(image,item.getSequence(),delta);
		}
	}

	private void update(RenderingImage image, Sequence sequence, int delta) {
		int state = sequence.current();
		if ( state < image.getNbStates() ) {
			// time elapsed since last frame change
			int time = image.getTime()+delta;
			// frame to add to current
			int deltaFrame = time/image.getFrametime();
			// index of new frame
			int frame = (image.getFrame()+deltaFrame);
			// We finished the state
			if ( frame > image.getNbFrames()[sequence.current()] ) {
				if ( sequence.isLoop() ) {
					frame = frame%image.getNbFrames()[state];
				} else {
					sequence.setOver(true);
					frame = image.getNbFrames()[state]-1;
				}
			}
			time = time - deltaFrame*image.getFrametime();

			Coordinate startSrc = new Coordinate(frame*image.getSizeSrc().getX(),state*image.getSizeSrc().getY());
			Coordinate endSrc = new Coordinate((frame+1)*image.getSizeSrc().getX(),(state+1)*image.getSizeSrc().getY());

			image.setTime(time);
			image.setFrame(frame);
			image.setStartSrc(startSrc);
			image.setEndSrc(endSrc);
		}
	}



	private void update(RenderingImage image, int state, int delta) {
		if ( state < image.getNbStates() ) {
			// time elapsed since last frame change
			int time = image.getTime()+delta;
			// frame to add to current
			int deltaFrame = time/image.getFrametime();
			// index of new frame
			int frame = (image.getFrame()+deltaFrame)%image.getNbFrames()[state];
			time = time - deltaFrame*image.getFrametime();

			Coordinate startSrc = new Coordinate(frame*image.getSizeSrc().getX(),state*image.getSizeSrc().getY());
			Coordinate endSrc = new Coordinate((frame+1)*image.getSizeSrc().getX(),(state+1)*image.getSizeSrc().getY());

			image.setTime(time);
			image.setFrame(frame);
			image.setStartSrc(startSrc);
			image.setEndSrc(endSrc);
		}
	}

	public List<List<RenderingImage>> getLayersImages() {
		return layersImages;
	}

	public List<Map<Item, RenderingImage>> getLayersItems() {
		return layersItems;
	}

	public List<Map<Sprite, RenderingImage>> getLayersSprites() {
		return layersSprites;
	}

	public void addToLayer(GameConstant.Layers layer, RenderingImage... images) {
		setUpLayer(layersImages,layer);
		for ( RenderingImage image : images ) {
			if ( image != null ) {
				layersImages.get(layer.ordinal()).add(image);
			}
		}
	}

	public void addToLayer(GameConstant.Layers layer, Item... items) {
		addToLayer(layersItems,layer,items);
	}

	public void addToLayer(GameConstant.Layers layer, Sprite... sprites) {
		addToLayer(layersSprites,layer,sprites);
	}

	public void removeFromLayer(GameConstant.Layers layer, Item... items) {
		removeFromLayer(layersItems,layer,items);
	}

	public void removeFromLayer(GameConstant.Layers layer, Sprite... sprites) {
		removeFromLayer(layersSprites,layer,sprites);
	}

	@SuppressWarnings("unchecked")
	private <T extends Sprite> void addToLayer(List<Map<T,RenderingImage>> map, GameConstant.Layers layer, T... list) {
		setUpLayerMap(map,layer);
		for ( T object : list ) {
			if ( object != null ) {
				RenderingImage image = ResourceHandler.getRenderingImage(object);
				// link image to item
				image.setPosition(object.getPosition());
				image.setSize(object.getSize());
				map.get(layer.ordinal()).put(object,image);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends Sprite> void removeFromLayer(List<Map<T,RenderingImage>> map, GameConstant.Layers layer, T... list) {
		if ( map.size() > layer.ordinal() ) {
			for ( T key : list ) {
				map.get(layer.ordinal()).remove(key);
			}
		}
	}

	public void clearLayer(GameConstant.Layers layer) {
		if ( layersImages.size() > layer.ordinal() && layersImages.get(layer.ordinal()) != null ) {
			layersImages.get(layer.ordinal()).clear();
		}
		if ( layersItems.size() > layer.ordinal() && layersItems.get(layer.ordinal()) != null ) {
			layersItems.get(layer.ordinal()).clear();
		}
		if ( layersSprites.size() > layer.ordinal() && layersSprites.get(layer.ordinal()) != null ) {
			layersSprites.get(layer.ordinal()).clear();
		}
	}

	private <T> void setUpLayer(List<List<T>> layers, GameConstant.Layers layer) {
		while ( layers.size() <= layer.ordinal() ) {
			layers.add(new ArrayList<T>());
		}
	}

	private <T,V> void setUpLayerMap(List<Map<T,V>> layers, GameConstant.Layers layer) {
		while ( layers.size() <= layer.ordinal() ) {
			layers.add(new HashMap<T,V>());
		}
	}
}
