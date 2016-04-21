package mr.controller;

import java.util.ArrayList;
import java.util.List;

import mr.model.Projectile;

public class ProjectileHandler {

	private List<Projectile> projectiles;

	public void addProjectile(Projectile proj) {
		projectiles.add(proj);
	}

	private static ProjectileHandler instance;

	private ProjectileHandler() {
		projectiles = new ArrayList<Projectile>();
	}

	public static ProjectileHandler get() {
		if ( instance == null ) {
			instance = new ProjectileHandler();
		}
		return instance;
	}
}
