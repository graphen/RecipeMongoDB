package com.szamraj.recipe_app_mongodb.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szamraj.recipe_app_mongodb.command.CategoryCommand;
import com.szamraj.recipe_app_mongodb.converter.CategoryCommandToCategory;
import com.szamraj.recipe_app_mongodb.converter.CategoryToCategoryCommand;
import com.szamraj.recipe_app_mongodb.model.Category;
import com.szamraj.recipe_app_mongodb.repository.CategoryRepository;
import com.szamraj.recipe_app_mongodb.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final RecipeRepository recipeRepository;
	private final CategoryToCategoryCommand categoryToCategoryCommand;
	private final CategoryCommandToCategory categoryCommandToCategory;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
			CategoryToCategoryCommand categoryToCategoryCommand, CategoryCommandToCategory categoryCommandToCategory) {
		super();
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
		this.categoryToCategoryCommand = categoryToCategoryCommand;
		this.categoryCommandToCategory = categoryCommandToCategory;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<CategoryCommand> getAllCategories() {
		log.info("Getting all categories");
		Iterable<Category> categories = categoryRepository.findAll();
		Set<CategoryCommand> categoryCommands = StreamSupport
				.stream(categories.spliterator(), false)
				.map(category -> categoryToCategoryCommand.convert(category))
				.collect(Collectors.toSet());
		return categoryCommands;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CategoryCommand findCategoryById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
		log.info("Finding category with id: {}", id);
		Optional<Category> categoryOptional = categoryRepository.findById(id);
        
        if (categoryOptional.isEmpty()) {
            throw new RuntimeException("Category with ID " + id + " not found");
        }
        return categoryToCategoryCommand.convert(categoryOptional.get());
	}
	
	@Override
	@Transactional(readOnly = true)
	public CategoryCommand findCategoryByDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
		log.info("Finding category with description: {}", description);
		Optional<Category> categoryOptional = categoryRepository.findByDescription(description);
        
        if (categoryOptional.isEmpty()) {
            throw new RuntimeException("Category with description " + description + " not found");
        }
        return categoryToCategoryCommand.convert(categoryOptional.get());
	}
	
    @Override
    public CategoryCommand saveCategory(CategoryCommand categoryCommand) {
        if (categoryCommand == null) {
            throw new IllegalArgumentException("Category command cannot be null");
        }
        
        if (categoryCommand.getDescription() == null || categoryCommand.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Category description cannot be empty");
        }
        
        log.info("Saving category...");
        Category category = categoryCommandToCategory.convert(categoryCommand);
        Category savedCategory = categoryRepository.save(category);
        
        log.info("Saved category with id: {}", savedCategory.getId());
        CategoryCommand savedCategoryCommand = categoryToCategoryCommand.convert(savedCategory);
        
        return savedCategoryCommand;
    }
    
    @Override
    public void deleteCategoryById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        
        log.info("Deleting category with id: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category with ID " + id + " not found");
        }
        if (isCategoryUsedInRecipes(id)) {
            long recipeCount = getRecipeCountForCategory(id);
            throw new IllegalStateException(
                String.format("Cannot delete category. It is currently used in %d recipe(s).", recipeCount)
            );
        }
        categoryRepository.deleteById(id);
    }
	
    @Override
    @Transactional(readOnly = true)
    public boolean isCategoryUsedInRecipes(String id) {
    	return recipeRepository.countByCategoriesId(id) > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getRecipeCountForCategory(String id) {
    	return recipeRepository.countByCategoriesId(id);
    }

}
