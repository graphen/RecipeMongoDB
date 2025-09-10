package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.UnitOfMeasureCommand;
import com.szamraj.recipe_app_mongodb.model.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
	
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
		if (unitOfMeasure == null) {
			return null;
		}

		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();

		unitOfMeasureCommand.setId(unitOfMeasure.getId());
		unitOfMeasureCommand.setDescription(unitOfMeasure.getDescription());

		return unitOfMeasureCommand;
	}
}
