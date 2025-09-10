package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.IngredientCommand;
import com.szamraj.recipe_app_mongodb.model.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
	
	@Override
	public IngredientCommand convert(Ingredient ingredient) {
		if (ingredient == null) {
			return null;
		}

		IngredientCommand ingredientCommand = new IngredientCommand();

		ingredientCommand.setId(ingredient.getId());
		ingredientCommand.setDescription(ingredient.getDescription());

		return ingredientCommand;
	}	
}
