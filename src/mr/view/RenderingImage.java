package mr.view;

import mr.model.misc.Coordinate;

public class RenderingImage {
	private Coordinate position;
	private Coordinate size;
	private Coordinate startSrc;
	private Coordinate endSrc;
	private Coordinate sizeSrc;
	private int frametime;
	private int time;
	private int frame;
	private int nbStates;
	private int[] nbFrames;

	private String image;

	public RenderingImage(Coordinate position, Coordinate size, String image) {
		super();
		this.position = position;
		this.size = size;
		this.image = image;
		startSrc = new Coordinate();
		endSrc = new Coordinate(size);
		sizeSrc = new Coordinate();
	}


	public RenderingImage(RenderingImage other) {
		this.position = other.position;
		this.size = other.size;
		this.startSrc = other.startSrc;
		this.endSrc = other.endSrc;
		this.sizeSrc = other.sizeSrc;
		this.frametime = other.frametime;
		this.time = other.time;
		this.frame = other.frame;
		this.nbStates = other.nbStates;
		this.nbFrames = other.nbFrames;
		this.image = other.image;
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
	public Coordinate getSizeSrc() {
		return sizeSrc;
	}
	public void setSizeSrc(Coordinate sizeSrc) {
		this.sizeSrc = sizeSrc;
	}
	public int getFrametime() {
		return frametime;
	}
	public void setFrametime(int frametime) {
		this.frametime = frametime;
	}
	public int getFrame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
	public int getNbStates() {
		return nbStates;
	}
	public void setNbStates(int nbStates) {
		this.nbStates = nbStates;
	}
	public int[] getNbFrames() {
		return nbFrames;
	}
	public void setNbFrames(int[] nbFrames) {
		this.nbFrames = nbFrames;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}
