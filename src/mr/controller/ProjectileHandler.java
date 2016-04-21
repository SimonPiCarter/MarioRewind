package mr.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mr.model.GameConstant;
import mr.model.Projectile;
import mr.model.misc.Coordinate;
import mr.model.model.ProjectileModel;
import mr.model.state.IState;
import mr.model.state.ProjectileState;
import mr.model.state.ProjectileState.Type;
import mr.view.RenderingContext;

public class ProjectileHandler {

	private List<Projectile> projectiles;
	private RenderingContext context;

	public void addProjectile(String projectileModel, Coordinate startPoint, Coordinate direction) {
		ProjectileModel model = ModelHandler.get().getProjectileModel(projectileModel);
		Type type = Type.Right;
		if ( direction.x > GameConstant.epsilon ) {
			if ( direction.y > GameConstant.epsilon ) {
				type = Type.DownRight;
			} else if ( direction.y < -GameConstant.epsilon ) {
				type = Type.UpRight;
			} else {
				type = Type.Right;
			}
		} else if ( direction.x < -GameConstant.epsilon ) {
			if ( direction.y > GameConstant.epsilon ) {
				type = Type.DownLeft;
			} else if ( direction.y < -GameConstant.epsilon ) {
				type = Type.UpLeft;
			} else {
				type = Type.Left;
			}
		} else {
			if ( direction.y > GameConstant.epsilon ) {
				type = Type.Down;
			} else if ( direction.y < -GameConstant.epsilon ) {
				type = Type.Up;
			}
		}
		IState state = new ProjectileState(type);
		Projectile proj = new Projectile(startPoint, model.getSize(), projectileModel, projectileModel, state, direction);
		addProjectile(proj);
	}

	public void addProjectile(Projectile proj) {
		projectiles.add(proj);
		context.addToLayer(GameConstant.Layers.FOREGROUND, proj);
	}

	public void update() {
		Iterator<Projectile> it = projectiles.iterator();
		while ( it.hasNext() ) {
			Projectile proj = it.next();
			if ( !proj.update() ) {
				context.removeFromLayer(GameConstant.Layers.FOREGROUND, proj);
				it.remove();
			}
		}
	}

	public RenderingContext getContext() {
		return context;
	}

	public void setContext(RenderingContext context) {
		this.context = context;
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
