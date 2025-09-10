package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.IngredientCommand;
import com.szamraj.recipe_app_mongodb.model.Ingredient;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	@Override
	public Ingredient convert(IngredientCommand ingredientCommand) {
		if (ingredientCommand == null) {
			return null;
		}

		Ingredient ingredient = new Ingredient();

		ingredient.setId(ingredientCommand.getId());
		ingredient.setDescription(ingredientCommand.getDescription());

		return ingredient;
	}
}
