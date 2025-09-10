package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.UnitOfMeasureCommand;
import com.szamraj.recipe_app_mongodb.model.UnitOfMeasure;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand unitOfMeasureCommand) {
		if (unitOfMeasureCommand == null) {
			return null;
		}
		
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		
		unitOfMeasure.setId(unitOfMeasureCommand.getId());
		unitOfMeasure.setDescription(unitOfMeasureCommand.getDescription());
		
		return unitOfMeasure;
	}
}
