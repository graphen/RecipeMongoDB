package com.szamraj.recipe_app_mongodb.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.szamraj.recipe_app_mongodb.command.RecipeCommand;
import com.szamraj.recipe_app_mongodb.converter.RecipeCommandToRecipe;
import com.szamraj.recipe_app_mongodb.converter.RecipeToRecipeCommand;
import com.szamraj.recipe_app_mongodb.model.Recipe;
import com.szamraj.recipe_app_mongodb.repository.RecipeRepository;

class RecipeServiceImplTest {
	
	RecipeService recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(recipeRepository.findAll()).thenReturn(Set.of(new Recipe(), new Recipe()));

		recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
	}

	@Test
	void testGetAllRecipes() {
		Recipe r1 = new Recipe();
		Recipe r2 = new Recipe();
		Set<Recipe> recipes = Set.of(r1, r2);
		when(recipeRepository.findAll()).thenReturn(recipes);
	    when(recipeToRecipeCommand.convert(r1)).thenReturn(new RecipeCommand());
	    when(recipeToRecipeCommand.convert(r2)).thenReturn(new RecipeCommand());
		
		Set<RecipeCommand> recipeCommands = recipeService.getAllRecipes();
		assertEquals(2, recipeCommands.size());
		verify(recipeRepository, times(1)).findAll();
	}
	
	@Test
	void testGetRecipeById() {
		Recipe recipe = new Recipe();
		recipe.setId("1L");
	    RecipeCommand recipeCommand = new RecipeCommand();
	    recipeCommand.setId("1L");
	    when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);
		when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
		
		RecipeCommand foundRecipe = recipeService.findRecipeById("1L");
		assertNotNull(foundRecipe);
		assertEquals("1L", foundRecipe.getId());
		verify(recipeRepository, times(1)).findById("1L");
	}

}
