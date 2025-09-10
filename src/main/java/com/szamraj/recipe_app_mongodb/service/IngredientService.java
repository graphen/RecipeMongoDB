package com.szamraj.recipe_app_mongodb.service;

import java.util.Set;

import com.szamraj.recipe_app_mongodb.command.IngredientCommand;

public interface IngredientService {
	public Set<IngredientCommand> getAllIngredients();
	public IngredientCommand findIngredientById(String id);
	public IngredientCommand saveIngredient(IngredientCommand ingredientCommand);
	public void deleteIngredientById(String id);
	public boolean isIngredientUsedInRecipes(String id);
	public long getRecipeCountForIngredient(String id);
}
