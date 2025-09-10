package com.szamraj.recipe_app_mongodb.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szamraj.recipe_app_mongodb.command.UnitOfMeasureCommand;
import com.szamraj.recipe_app_mongodb.converter.UnitOfMeasureCommandToUnitOfMeasure;
import com.szamraj.recipe_app_mongodb.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.szamraj.recipe_app_mongodb.model.UnitOfMeasure;
import com.szamraj.recipe_app_mongodb.repository.RecipeRepository;
import com.szamraj.recipe_app_mongodb.repository.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    
    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                   RecipeRepository recipeRepository,
                                   UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand,
                                   UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<UnitOfMeasureCommand> getAllUnitsOfMeasure() {
		log.info("Getting all unitsOfMeasure");
		Iterable<UnitOfMeasure> unitsOfMeasure = unitOfMeasureRepository.findAll();
		Set<UnitOfMeasureCommand> unitOfMeasureCommands = StreamSupport
				.stream(unitsOfMeasure.spliterator(), false)
				.map(unitOfMeasure -> unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure))
				.collect(Collectors.toSet());
		return unitOfMeasureCommands;
    }
    
    @Override
    @Transactional(readOnly = true)
    public UnitOfMeasureCommand findUnitOfMeasureById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Unit Of Measure ID cannot be null");
        }
    	log.info("Finding unitOfMeasure with id: {}", id);
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findById(id);
        
        if (unitOfMeasureOptional.isEmpty()) {
            throw new RuntimeException("Unit of Measure with ID " + id + " not found");
        }
        return unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasureOptional.get());
    }
    
    @Override
    @Transactional(readOnly = true)
	public UnitOfMeasureCommand findUnitOfMeasureByDescription(String description) {
 		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		log.info("Finding unitOfMeasure with description: {}", description);
		Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription(description);
		
        if (unitOfMeasureOptional.isEmpty()) {
            throw new RuntimeException("Unit of Measure with description " + description + " not found");
        }
        return unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasureOptional.get());
	}
    
	@Override
    public UnitOfMeasureCommand saveUnitOfMeasure(UnitOfMeasureCommand unitOfMeasureCommand) {
        if (unitOfMeasureCommand == null) {
            throw new IllegalArgumentException("Unit of Measure command cannot be null");
        }
        
        if (unitOfMeasureCommand.getDescription() == null || unitOfMeasureCommand.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Unit of Measure description cannot be empty");
        }
        
        log.info("Saving unitOfMeasure...");
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);
        UnitOfMeasure savedUnitOfMeasure = unitOfMeasureRepository.save(unitOfMeasure);
        
        log.info("Saved unitOfMeasure with id: {}", savedUnitOfMeasure.getId());
        UnitOfMeasureCommand savedUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(savedUnitOfMeasure);
        
        return savedUnitOfMeasureCommand;
    }
	
    @Override
    public void deleteUnitOfMeasureById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Unit of Measure ID cannot be null");
        }
        log.info("Deleting unitOfMeasure with id: {}", id);
        
        if (!unitOfMeasureRepository.existsById(id)) {
            throw new RuntimeException("Unit of Measure with ID " + id + " not found");
        }
        if (isUnitOfMeasureUsedInRecipes(id)) {
            long usageCount = getRecipeIngredientCountForUnitOfMeasure(id);
            throw new IllegalStateException(
                String.format("Cannot delete unit of measure. It is currently used in %d recipe ingredient(s).", usageCount)
            );
        }
        unitOfMeasureRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUnitOfMeasureUsedInRecipes(String id) {
        return recipeRepository.existsByUnitOfMeasureId(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getRecipeIngredientCountForUnitOfMeasure(String id) {
        return recipeRepository.countByUnitOfMeasureId(id);	
    }
}
