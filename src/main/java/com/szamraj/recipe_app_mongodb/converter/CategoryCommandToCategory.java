package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.CategoryCommand;
import com.szamraj.recipe_app_mongodb.model.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
	
	@Override
	public Category convert(CategoryCommand categoryCommand) {
		if(categoryCommand == null) {
			return null;
		}
		
		Category category = new Category();
		
		category.setId(categoryCommand.getId());
		category.setDescription(categoryCommand.getDescription());
		
		return category;
	}
}
