package mr.controller;

import org.junit.Assert;
import org.junit.Test;

import mr.core.exception.FormatModelException;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.model.AIModel;
import mr.model.model.Model;
import mr.model.model.ProjectileModel;

public class ModelHandlerTest {

	@Test
	public void test() throws InputFileNotFoundException, FormatModelException {
		ModelHandler.get().load("resources/TESTmodels.data.txt");

		Object[][] expecteds = new Object[][] {
			{"model1", "model1.png", 1.f, 2.f, 3.f, 4.f, 5.f, 6.f},
			{"model2", "model2.png", 6.f, 5.f, 4.f, 3.f, 2.f, 1.f},
			{"model3", "model3.png", 9.f, 8.f, 7.f, 6.f, 5.f, 4.f}
		};

		for ( Object[] expected : expecteds ) {
			Model model = ModelHandler.get().getModel((String) expected[0]);
			Assert.assertEquals(expected[0], model.getId());
			Assert.assertEquals(expected[1], model.getSprite());
			Assert.assertEquals((float)expected[2], model.getSize().x,GameConstant.epsilon);
			Assert.assertEquals((float)expected[3], model.getSize().y,GameConstant.epsilon);
			Assert.assertEquals((float)expected[4], model.getHitBoxOffset().x,GameConstant.epsilon);
			Assert.assertEquals((float)expected[5], model.getHitBoxOffset().y,GameConstant.epsilon);
			Assert.assertEquals((float)expected[6], model.getHitBoxSize().x,GameConstant.epsilon);
			Assert.assertEquals((float)expected[7], model.getHitBoxSize().y,GameConstant.epsilon);
		}

		expecteds = new Object[][] {
			{"projmodel1", "projmodel1.png", 12.1f, 1},
			{"projmodel2", "projmodel2.png", 12.2f, 2}
		};

		for ( Object[] expected : expecteds ) {
			ProjectileModel model = ModelHandler.get().getProjectileModel((String) expected[0]);
			Assert.assertEquals(expected[0], model.getId());
			Assert.assertEquals(expected[1], model.getSprite());
			Assert.assertEquals((float)expected[2], model.getSpeed(),GameConstant.epsilon);
			Assert.assertEquals(expected[3], model.getDamage());
		}

		expecteds = new Object[][] {
			{"AImodel1", "AImodel1.png", 12.2f, 3, "projmodel1"}
		};

		for ( Object[] expected : expecteds ) {
			AIModel model = ModelHandler.get().getAIModel((String) expected[0]);
			Assert.assertEquals(expected[0], model.getId());
			Assert.assertEquals(expected[1], model.getSprite());
			Assert.assertEquals((float)expected[2], model.getSpeed(),GameConstant.epsilon);
			//Assert.assertEquals(expected[3], model.getLife());
			Assert.assertEquals(expected[4], model.getProjectileModel());
		}
	}

}
