package com.szamraj.recipe_app_mongodb.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szamraj.recipe_app_mongodb.command.RecipeCommand;
import com.szamraj.recipe_app_mongodb.converter.RecipeCommandToRecipe;
import com.szamraj.recipe_app_mongodb.converter.RecipeToRecipeCommand;
import com.szamraj.recipe_app_mongodb.model.Recipe;
import com.szamraj.recipe_app_mongodb.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {
    
    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    
    public RecipeServiceImpl(RecipeRepository recipeRepository,
            RecipeToRecipeCommand recipeToRecipeCommand,
            RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<RecipeCommand> getAllRecipes() {
        log.info("Getting all recipes");
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Set<RecipeCommand> recipeCommands = StreamSupport
                .stream(recipes.spliterator(), false)
                .map(recipe -> recipeToRecipeCommand.convert(recipe))
                .collect(Collectors.toSet());
        return recipeCommands;
    }
    
    @Override
    @Transactional(readOnly = true)
    public RecipeCommand findRecipeById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Recipe ID cannot be null");
        }
        log.info("Finding recipe with id: {}", id);
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        
        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe with ID " + id + " not found");
        }
        return recipeToRecipeCommand.convert(recipeOptional.get());
    }
    
    @Override
    public RecipeCommand saveRecipe(RecipeCommand recipeCommand) {
        if (recipeCommand == null) {
            throw new IllegalArgumentException("Recipe command cannot be null");
        }
        
        if (recipeCommand.getDescription() == null || recipeCommand.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe description cannot be empty");
        }
        
        log.info("Saving recipe...");
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(recipe);
        
        log.info("Saved recipe with id: {}", savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }
    
    @Override
    public void deleteRecipeById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Recipe ID cannot be null");
        }
        
        log.info("Deleting recipe with id: {}", id);
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe with ID " + id + " not found");
        }
        
        recipeRepository.deleteById(id);
        log.info("Recipe with id {} deleted successfully", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean hasRecipeIngredients(String id) {
        if (id == null) {
            return false;
        }
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.isPresent() && !recipe.get().getRecipeIngredients().isEmpty();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getIngredientCountForRecipe(String id) {
        if (id == null) {
            return 0;
        }
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.isPresent() ? recipe.get().getRecipeIngredients().size() : 0;
    }
}
