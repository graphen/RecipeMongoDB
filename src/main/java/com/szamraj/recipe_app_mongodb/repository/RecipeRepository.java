package com.szamraj.recipe_app_mongodb.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.szamraj.recipe_app_mongodb.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
	
    // Check if any recipe contains a RecipeIngredient with given Ingredient ID
    @Query(value = "{ 'recipeIngredients.ingredient.id': ?0 }")
    boolean existsByIngredientId(String ingredientId);

    // Check if any recipe contains a RecipeIngredient with given UnitOfMeasure ID
    @Query(value = "{ 'recipeIngredients.unitOfMeasure.id': ?0 }")
    boolean existsByUnitOfMeasureId(String unitId);

    // Count recipes containing a RecipeIngredient with given Ingredient ID
    @Query(value = "{ 'recipeIngredients.ingredient.id': ?0 }", count = true)
    long countByIngredientId(String ingredientId);

    // Count recipes containing a RecipeIngredient with given UnitOfMeasure ID
    @Query(value = "{ 'recipeIngredients.unitOfMeasure.id': ?0 }", count = true)
    long countByUnitOfMeasureId(String unitId);

    // Count recipes containing a RecipeIngredient pointing to a given Recipe ID
    @Query(value = "{ 'recipeIngredients.recipe.id': ?0 }", count = true)
    long countByRecipeId(String recipeId);
    
    @Query(value = "{ 'categories.id': ?0 }", count = true)
    long countByCategoriesId(String categoryId);

}
