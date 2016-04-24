package mr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mr.controller.colliders.AbstractMovableCollider;
import mr.controller.entity.Enemy;
import mr.controller.entity.Hero;
import mr.model.GameConstant.Layers;
import mr.model.Screen;
import mr.view.RenderingContext;

public class EntityHandler {
	private Map<Enemy,Integer> enemiesWithDeathTimer;
	private Hero hero;
	private int heroRecoverTimer;

	private List<AbstractMovableCollider> colliders;

	public EntityHandler(Hero hero) {
		this.enemiesWithDeathTimer = new HashMap<Enemy,Integer>();
		this.colliders = new ArrayList<AbstractMovableCollider>();
		this.hero = hero;
	}

	public void addEnemy(Enemy enemy) {
		enemiesWithDeathTimer.put(enemy, Integer.valueOf(0));
	}

	public void addCollider(AbstractMovableCollider... collider) {
		for ( AbstractMovableCollider col : collider ) {
			colliders.add(col);
		}
	}

	public Set<Enemy> getEnemies() {
		return enemiesWithDeathTimer.keySet();
	}

	public List<AbstractMovableCollider> getColliders() {
		return colliders;
	}

	public void update(int timeStep, Screen screen, RenderingContext context) {
		hero.updateSpeed();
		for ( Enemy enemy : enemiesWithDeathTimer.keySet() ) {
			if ( !enemy.isDead() ) {
				updateEnemy(timeStep,screen,enemy);
			} else {
				context.removeFromLayer(Layers.FOREGROUND, enemy);
			}
		}
		for ( AbstractMovableCollider collider : colliders) {
			if ( collider.isActive() ) {
				collider.updateState();
				collider.collide(hero);
			} else {
				context.removeFromLayer(Layers.FOREGROUND, collider);
			}
		}
		updateHero(timeStep,screen);
	}

	private void updateEnemy(int timeStep, Screen screen, Enemy enemy) {
		Integer elapsedDyingTime = enemiesWithDeathTimer.get(enemy);
		if ( !enemy.isDead() && !enemy.isDying() ) {
			enemy.update(timeStep);
			enemy.updateState();
			enemy.collide(hero);
		}
		if ( enemy.isDying() ) {
			enemy.stop();
			elapsedDyingTime += timeStep;
			if ( elapsedDyingTime > enemy.getModel().getDeathThreshold() ) {
				enemy.setDead(true);
				enemy.setDying(false);
			}
			enemiesWithDeathTimer.put(enemy, elapsedDyingTime);
		}
		enemy.move(screen);
	}

	private void updateHero(int timeStep, Screen screen) {
		if ( hero.isDying() ) {
			heroRecoverTimer += timeStep;
			if ( heroRecoverTimer > hero.getModel().getRecoverThreshold() ) {
				heroRecoverTimer = 0;
				hero.setDying(false);

			}
		}
		hero.move(screen);
	}
}
