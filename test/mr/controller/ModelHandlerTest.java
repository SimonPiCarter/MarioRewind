package mr.controller;

import org.junit.Assert;
import org.junit.Test;

import mr.core.exception.FormatModelException;
import mr.core.exception.InputFileNotFoundException;
import mr.model.model.AIModel;
import mr.model.model.Model;
import mr.model.model.ProjectileModel;

public class ModelHandlerTest {

	@Test
	public void test() throws InputFileNotFoundException, FormatModelException {
		ModelHandler.get().load("resources/models.data.txt");

		Object[][] expecteds = new Object[][] {
			{"model1", "model1.png"},
			{"model2", "model2.png"},
			{"model3", "model3.png"}
		};

		for ( Object[] expected : expecteds ) {
			Model model = ModelHandler.get().getModel((String) expected[0]);
			Assert.assertEquals(expected[0], model.getId());
			Assert.assertEquals(expected[1], model.getSprite());
		}

		expecteds = new Object[][] {
			{"projmodel1", "projmodel1.png", 12.1f, 1},
			{"projmodel2", "projmodel2.png", 12.2f, 2}
		};

		for ( Object[] expected : expecteds ) {
			ProjectileModel model = ModelHandler.get().getProjectileModel((String) expected[0]);
			Assert.assertEquals(expected[0], model.getId());
			Assert.assertEquals(expected[1], model.getSprite());
			Assert.assertEquals((float)expected[2], model.getSpeed(),0.0001f);
			Assert.assertEquals(expected[3], model.getDamage());
		}

		expecteds = new Object[][] {
			{"AImodel1", "AImodel1.png", 12.2f, 3, "projmodel1"}
		};

		for ( Object[] expected : expecteds ) {
			AIModel model = ModelHandler.get().getAIModel((String) expected[0]);
			Assert.assertEquals(expected[0], model.getId());
			Assert.assertEquals(expected[1], model.getSprite());
			Assert.assertEquals((float)expected[2], model.getSpeed(),0.0001f);
			Assert.assertEquals(expected[3], model.getLife());
			Assert.assertEquals(expected[4], model.getProjectileModel());
		}
	}

}
