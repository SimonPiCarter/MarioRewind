package mr.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import mr.core.exception.FormatModelException;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.model.AIModel;
import mr.model.model.EnemyModel;
import mr.model.model.HeroModel;
import mr.model.model.Model;
import mr.model.model.ProjectileModel;

public class ModelHandler {

	private Map<String, Model> models;
	private Map<String, ProjectileModel> projectileModels;
	private Map<String, AIModel> aiModels;
	private Map<String, EnemyModel> enemyModels;
	private Map<String, HeroModel> heroModels;

	public Model getModel(String name) {
		if ( !models.containsKey(name) ) {
			return models.get("default");
		}
		return models.get(name);
	}

	public ProjectileModel getProjectileModel(String name) {
		if ( !projectileModels.containsKey(name) ) {
			return projectileModels.get("default");
		}
		return projectileModels.get(name);
	}

	public AIModel getAIModel(String name) {
		if ( !aiModels.containsKey(name) ) {
			return aiModels.get("default");
		}
		return aiModels.get(name);
	}

	public EnemyModel getEnemyModel(String name) {
		if ( !enemyModels.containsKey(name) ) {
			return enemyModels.get("default");
		}
		return enemyModels.get(name);
	}

	public HeroModel getHeroModel(String name) {
		if ( !heroModels.containsKey(name) ) {
			return heroModels.get("default");
		}
		return heroModels.get(name);
	}

	// Model ProjectileModel AIModel EnemyModel HeroModel
	public void load(String path) throws InputFileNotFoundException, FormatModelException {
		Path pathFile = Paths.get(path);
		Scanner scanner = null;
		try {
			scanner = new Scanner(pathFile, GameConstant.ENCODING.name());

			if ( !scanner.hasNextLine() ) {
				throw new FormatModelException("Files "+path+" has wrong format. (empty file?)");
			}
			String line = scanner.nextLine();
			String[] numbersOfModel = line.split(" ");
			for ( int i = 0 ; i < numbersOfModel.length ; ++ i ) {
				for ( int j = 0 ; j < Integer.parseInt(numbersOfModel[i]) ; ++ j ) {
					if ( i == 0 ) {
						Model model = new Model();
						parseModel(model,scanner);
						models.put(model.getId(),model);
					} else if ( i == 1 ) {
						ProjectileModel model = new ProjectileModel();
						parseProjectileModel(model,scanner);
						projectileModels.put(model.getId(),model);
					} else if ( i == 2 ) {
						AIModel model = new AIModel();
						parseAIModel(model,scanner);
						aiModels.put(model.getId(),model);
					} else if ( i == 3 ) {
						EnemyModel model = new EnemyModel();
						parseEnemyModel(model,scanner);
						enemyModels.put(model.getId(),model);
					} else if ( i == 4 ) {
						HeroModel model = new HeroModel();
						parseHeroModel(model,scanner);
						heroModels.put(model.getId(),model);
					}
				}
			}


		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new FormatModelException("Files "+path+" has wrong format.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new InputFileNotFoundException("File not found : "+path+".");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new FormatModelException("Files "+path+" has wrong format. (first line must be numbers)");
		} finally {
			scanner.close();
		}
	}

	private void parseModel(Model model, Scanner scanner) {
		model.setId(scanner.next());
		model.setSprite(scanner.next());
		model.getSize().x = scanner.nextFloat();
		model.getSize().y = scanner.nextFloat();
		model.getHitBoxOffset().x = scanner.nextFloat();
		model.getHitBoxOffset().y = scanner.nextFloat();
		model.getHitBoxSize().x = scanner.nextFloat();
		model.getHitBoxSize().y = scanner.nextFloat();
	}

	private void parseProjectileModel(ProjectileModel model, Scanner scanner) {
		parseModel(model,scanner);
		model.setSpeed(scanner.nextFloat());
		model.setDamage(scanner.nextInt());
	}

	private void parseAIModel(AIModel model, Scanner scanner) {
		parseModel(model,scanner);
		model.setSpeed(scanner.nextFloat());
		model.setProjectileModel(scanner.next());
	}

	private void parseEnemyModel(EnemyModel model, Scanner scanner) {
		parseAIModel(model,scanner);
		model.setLife(scanner.nextInt());
		model.setDeathThreshold(scanner.nextInt());
	}

	private void parseHeroModel(HeroModel model, Scanner scanner) {
		parseModel(model,scanner);
		model.setSpeed(scanner.nextFloat());
		model.setLife(scanner.nextInt());
		model.setBacktrack(scanner.nextFloat());
		model.setProjectileModel(scanner.next());
	}

	private static ModelHandler instance;

	private ModelHandler() {
		models = new HashMap<String, Model>();
		projectileModels = new HashMap<String, ProjectileModel>();
		aiModels = new HashMap<String, AIModel>();
		enemyModels = new HashMap<String, EnemyModel>();
		heroModels = new HashMap<String, HeroModel>();
	}

	public static ModelHandler get() {
		if ( instance == null ) {
			instance = new ModelHandler();
		}
		return instance;
	}
}
