package com.szamraj.recipe_app_mongodb.command;

import java.math.BigDecimal;

public class RecipeIngredientCommand {
	private String id;
	private String ingredientId;
	private String ingredientDescription;
	private BigDecimal amount;
	private String unitOfMeasureId;
	private String unitOfMeasureDescription;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIngredientId() {
		return ingredientId;
	}
	
	public void setIngredientId(String ingredientId) {
		this.ingredientId = ingredientId;
	}
	
	public String getIngredientDescription() {
		return ingredientDescription;
	}
	
	public void setIngredientDescription(String ingredientDescription) {
		this.ingredientDescription = ingredientDescription;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getUnitOfMeasureId() {
		return unitOfMeasureId;
	}
	
	public void setUnitOfMeasureId(String unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
	}
	
	public String getUnitOfMeasureDescription() {
		return unitOfMeasureDescription;
	}
	
	public void setUnitOfMeasureDescription(String unitOfMeasureDescription) {
		this.unitOfMeasureDescription = unitOfMeasureDescription;
	}
}
