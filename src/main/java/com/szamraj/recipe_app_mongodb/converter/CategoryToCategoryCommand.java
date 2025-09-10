package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.CategoryCommand;
import com.szamraj.recipe_app_mongodb.model.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

	@Override
	public CategoryCommand convert(Category category) {
		if(category == null) {
			return null;
		}
		
		CategoryCommand categoryCommand = new CategoryCommand();
		
		categoryCommand.setId(category.getId());
		categoryCommand.setDescription(category.getDescription());
		
		return categoryCommand;
	}
}
