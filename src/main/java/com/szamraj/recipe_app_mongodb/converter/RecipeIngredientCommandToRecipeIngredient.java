package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.RecipeIngredientCommand;
import com.szamraj.recipe_app_mongodb.model.Ingredient;
import com.szamraj.recipe_app_mongodb.model.RecipeIngredient;
import com.szamraj.recipe_app_mongodb.model.UnitOfMeasure;
import com.szamraj.recipe_app_mongodb.repository.IngredientRepository;
import com.szamraj.recipe_app_mongodb.repository.RecipeRepository;
import com.szamraj.recipe_app_mongodb.repository.UnitOfMeasureRepository;

import java.util.Optional;

@Component
public class RecipeIngredientCommandToRecipeIngredient implements Converter<RecipeIngredientCommand, RecipeIngredient> {
	
	RecipeRepository recipeRepository;
	IngredientRepository ingredientRepository;
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	public RecipeIngredientCommandToRecipeIngredient(RecipeRepository recipeRepository, 
			IngredientRepository ingredientRepository, 
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.ingredientRepository = ingredientRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}
	
	@Override
	public RecipeIngredient convert(RecipeIngredientCommand recipeIngredientCommand) {
		if (recipeIngredientCommand == null) {
			return null;
		}
		
		RecipeIngredient recipeIngredient = new RecipeIngredient();
		
		recipeIngredient.setId(recipeIngredientCommand.getId());
		
		if (recipeIngredientCommand.getIngredientId() != null) {
			Optional<Ingredient> ingredient = ingredientRepository.findById(recipeIngredientCommand.getIngredientId());
			if (ingredient.isPresent()) {
				recipeIngredient.setIngredient(ingredient.get());
			}
		}
		
		recipeIngredient.setAmount(recipeIngredientCommand.getAmount());
		
		if (recipeIngredientCommand.getUnitOfMeasureId() != null) {
			Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findById(recipeIngredientCommand.getUnitOfMeasureId());
			if (uom.isPresent()) {
				recipeIngredient.setUnitOfMeasure(uom.get());
			}
		}
		
		return recipeIngredient;
	}
}
