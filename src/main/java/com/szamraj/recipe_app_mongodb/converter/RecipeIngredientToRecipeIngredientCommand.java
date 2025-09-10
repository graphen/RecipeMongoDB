package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.RecipeIngredientCommand;
import com.szamraj.recipe_app_mongodb.model.RecipeIngredient;

@Component
public class RecipeIngredientToRecipeIngredientCommand implements Converter<RecipeIngredient, RecipeIngredientCommand> {
	
	@Override
	public RecipeIngredientCommand convert(RecipeIngredient recipeIngredient) {
		if (recipeIngredient == null) {
			return null;
		}
		
		RecipeIngredientCommand recipeIngredientCommand = new RecipeIngredientCommand();
		
		recipeIngredientCommand.setId(recipeIngredient.getId());
		
		if(recipeIngredient.getIngredient() != null) {
			recipeIngredientCommand.setIngredientId(recipeIngredient.getIngredient().getId());
			recipeIngredientCommand.setIngredientDescription(recipeIngredient.getIngredient().getDescription());
		}
		
		recipeIngredientCommand.setAmount(recipeIngredient.getAmount());
		
		if (recipeIngredient.getUnitOfMeasure() != null) {
			recipeIngredientCommand.setUnitOfMeasureId(recipeIngredient.getUnitOfMeasure().getId());
			recipeIngredientCommand.setUnitOfMeasureDescription(recipeIngredient.getUnitOfMeasure().getDescription());
		}
		
		return recipeIngredientCommand;
	}
}
