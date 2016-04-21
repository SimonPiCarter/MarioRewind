package mr.model.model;

public class AIModel extends Model {
	private float speed;
	private int life;
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
	public String getProjectileModel() {
		return projectileModel;
	}
	public void setProjectileModel(String projectileModel) {
		this.projectileModel = projectileModel;
	}
}
