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
import mr.model.model.Model;
import mr.model.model.ProjectileModel;

public class ModelHandler {

	private Map<String, Model> models;
	private Map<String, ProjectileModel> projectileModels;
	private Map<String, AIModel> aiModels;

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
			//int[] numbersOfModelInt = new int[numbersOfModel.length];
			for ( int i = 0 ; i < numbersOfModel.length ; ++ i ) {
				//numbersOfModelInt[i] = Integer.parseInt(numbersOfModel[i]);
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
		model.setLife(scanner.nextInt());
		model.setProjectileModel(scanner.next());
	}

	private static ModelHandler instance;

	private ModelHandler() {
		models = new HashMap<String, Model>();
		projectileModels = new HashMap<String, ProjectileModel>();
		aiModels = new HashMap<String, AIModel>();
	}

	public static ModelHandler get() {
		if ( instance == null ) {
			instance = new ModelHandler();
		}
		return instance;
	}
}
