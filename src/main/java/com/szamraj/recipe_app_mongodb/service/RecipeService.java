package com.szamraj.recipe_app_mongodb.service;

import java.util.Set;

import com.szamraj.recipe_app_mongodb.command.RecipeCommand;

public interface RecipeService {
	public Set<RecipeCommand> getAllRecipes();
	public RecipeCommand findRecipeById(String id);
	public RecipeCommand saveRecipe(RecipeCommand command);
	public void deleteRecipeById(String id);
	public boolean hasRecipeIngredients(String id);
	public long getIngredientCountForRecipe(String id);
}
