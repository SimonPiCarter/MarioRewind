package mr.model.model;

public class HeroModel extends Model {
	private float speed;
	private int life;
	private float backtrack;
	private String projectileModel;

	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public float getBacktrack() {
		return backtrack;
	}
	public void setBacktrack(float backtrack) {
		this.backtrack = backtrack;
	}
	public String getProjectileModel() {
		return projectileModel;
	}
	public void setProjectileModel(String projectileModel) {
		this.projectileModel = projectileModel;
	}
}
