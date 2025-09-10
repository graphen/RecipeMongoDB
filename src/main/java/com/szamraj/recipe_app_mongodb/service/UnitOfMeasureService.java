package com.szamraj.recipe_app_mongodb.service;

import java.util.Set;

import com.szamraj.recipe_app_mongodb.command.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	public Set<UnitOfMeasureCommand> getAllUnitsOfMeasure();
	public UnitOfMeasureCommand findUnitOfMeasureById(String id);
	public UnitOfMeasureCommand findUnitOfMeasureByDescription(String description);
	public UnitOfMeasureCommand saveUnitOfMeasure(UnitOfMeasureCommand unitOfMeasureCommand);
	public void deleteUnitOfMeasureById(String id);
	public boolean isUnitOfMeasureUsedInRecipes(String id);
	public long getRecipeIngredientCountForUnitOfMeasure(String id);
}
