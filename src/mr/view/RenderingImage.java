package mr.view;

import mr.model.misc.Coordinate;

public class RenderingImage {
	private Coordinate position;
	private Coordinate size;
	private Coordinate startSrc;
	private Coordinate endSrc;
	private String image;

	public RenderingImage(Coordinate position, String image) {
		super();
		this.position = position;
		this.image = image;
	}
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}
	public Coordinate getSize() {
		return size;
	}
	public void setSize(Coordinate size) {
		this.size = size;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Coordinate getStartSrc() {
		return startSrc;
	}
	public void setStartSrc(Coordinate startSrc) {
		this.startSrc = startSrc;
	}
	public Coordinate getEndSrc() {
		return endSrc;
	}
	public void setEndSrc(Coordinate endSrc) {
		this.endSrc = endSrc;
	}
}
