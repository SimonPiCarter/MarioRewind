package mr.model.model;

public class EnemyModel extends AIModel {
	private int deathThreshold;
	private int life;

	public int getDeathThreshold() {
		return deathThreshold;
	}
	public void setDeathThreshold(int deathThreshold) {
		this.deathThreshold = deathThreshold;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
}
