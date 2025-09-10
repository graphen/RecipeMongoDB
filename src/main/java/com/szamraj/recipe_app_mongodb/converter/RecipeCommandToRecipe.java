package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.RecipeCommand;
import com.szamraj.recipe_app_mongodb.model.Category;
import com.szamraj.recipe_app_mongodb.model.Difficulty;
import com.szamraj.recipe_app_mongodb.model.Notes;
import com.szamraj.recipe_app_mongodb.model.Recipe;
import com.szamraj.recipe_app_mongodb.model.RecipeIngredient;
import com.szamraj.recipe_app_mongodb.repository.CategoryRepository;

import java.util.Optional;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
	
	RecipeIngredientCommandToRecipeIngredient recipeIngredientCommandToRecipeIngredient;
	NotesCommandToNotes notesCommandToNotes;
	CategoryRepository categoryRepository;
	
	public RecipeCommandToRecipe(RecipeIngredientCommandToRecipeIngredient recipeIngredientCommandToRecipeIngredient,
			NotesCommandToNotes notesCommandToNotes, CategoryRepository categoryRepository) {
		super();
		this.recipeIngredientCommandToRecipeIngredient = recipeIngredientCommandToRecipeIngredient;
		this.notesCommandToNotes = notesCommandToNotes;
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public Recipe convert(RecipeCommand recipeCommand) {
		if (recipeCommand == null) {
			return null;
		}
		
		Recipe recipe = new Recipe();
		
	    // Only set the ID if it is non-empty
	    if (recipeCommand.getId() != null && !recipeCommand.getId().isEmpty()) {
	        recipe.setId(recipeCommand.getId());
	    } else {
	        recipe.setId(null); // ensure new document
	    }
		
		recipe.setDescription(recipeCommand.getDescription());
		
		recipe.setCookTime(recipeCommand.getCookTime());
		
		recipe.setPrepTime(recipeCommand.getPrepTime());
		
		recipe.setServings(recipeCommand.getServings());
		
		recipe.setSource(recipeCommand.getSource());
		
		recipe.setUrl(recipeCommand.getUrl());
		
		recipe.setDirections(recipeCommand.getDirections());
		
		if (recipeCommand.getRecipeIngredients() != null) {
			recipeCommand.getRecipeIngredients().forEach(ingredientCommand -> {
				RecipeIngredient ingredient = recipeIngredientCommandToRecipeIngredient.convert(ingredientCommand);
				recipe.getRecipeIngredients().add(ingredient);
			});
		}
		
		recipe.setDifficulty(recipeCommand.getDifficulty() != null ? 
			Difficulty.valueOf(recipeCommand.getDifficulty()) : null);
		
	    if (recipeCommand.getImage() != null) {
	        recipe.setImage(recipeCommand.getImage());
	    }
		
		if (recipeCommand.getCategories() != null) {
			recipeCommand.getCategories().forEach(categoryId -> {
				Optional<Category> category = categoryRepository.findById(categoryId);
				if (category.isPresent()) {
					recipe.getCategories().add(category.get());
				}
			});
		}
		
		if (recipeCommand.getNotes() != null) {
			Notes notes = notesCommandToNotes.convert(recipeCommand.getNotes());
	        if (notes != null) {
	        	recipe.setNotes(notes);
	        }
		}
		
		return recipe;
	}
}
