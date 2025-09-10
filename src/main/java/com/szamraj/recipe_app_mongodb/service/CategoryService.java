package com.szamraj.recipe_app_mongodb.service;

import java.util.Set;

import com.szamraj.recipe_app_mongodb.command.CategoryCommand;

public interface CategoryService {
	public Set<CategoryCommand> getAllCategories();
	public CategoryCommand findCategoryById(String id);
	public CategoryCommand findCategoryByDescription(String description);
	public CategoryCommand saveCategory(CategoryCommand categoryCommand);
	public void deleteCategoryById(String id);
	public boolean isCategoryUsedInRecipes(String id);
	public long getRecipeCountForCategory(String id);
}
