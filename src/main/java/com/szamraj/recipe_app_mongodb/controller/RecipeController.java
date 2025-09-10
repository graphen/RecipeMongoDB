package com.szamraj.recipe_app_mongodb.controller;

import java.util.Set;
import java.io.IOException;
import java.math.BigDecimal;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.szamraj.recipe_app_mongodb.command.CategoryCommand;
import com.szamraj.recipe_app_mongodb.command.IngredientCommand;
import com.szamraj.recipe_app_mongodb.command.RecipeCommand;
import com.szamraj.recipe_app_mongodb.command.RecipeIngredientCommand;
import com.szamraj.recipe_app_mongodb.command.UnitOfMeasureCommand;
import com.szamraj.recipe_app_mongodb.model.Difficulty;
import com.szamraj.recipe_app_mongodb.service.CategoryService;
import com.szamraj.recipe_app_mongodb.service.IngredientService;
import com.szamraj.recipe_app_mongodb.service.RecipeService;
import com.szamraj.recipe_app_mongodb.service.UnitOfMeasureService;

import org.springframework.validation.BindingResult;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipeController {
    
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final CategoryService categoryService;
    
    public RecipeController(RecipeService recipeService, 
                           IngredientService ingredientService,
                           UnitOfMeasureService unitOfMeasureService,
                           CategoryService categoryService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.categoryService = categoryService;
    }
    
    @GetMapping({"/list", "/", ""})
    public String listRecipes(Model model) {
        Set<RecipeCommand> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "recipes/list";
    }
    
    @GetMapping("/{id}")    
    public String showRecipe(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            RecipeCommand recipeCommand = recipeService.findRecipeById(id);
            long ingredientCount = recipeService.getIngredientCountForRecipe(id);
            model.addAttribute("recipe", recipeCommand);
            model.addAttribute("ingredientCount", ingredientCount);
            return "recipes/detail";
        }
        catch(RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/recipes";
        }
    }
    
    @GetMapping({"/new", "/create"})
    public String showCreateForm(Model model) {
        RecipeCommand recipeCommand = new RecipeCommand();
        // Add one empty ingredient line
        RecipeIngredientCommand emptyIngredient = new RecipeIngredientCommand();
        emptyIngredient.setAmount(BigDecimal.ZERO);
        recipeCommand.getRecipeIngredients().add(emptyIngredient);
        
        model.addAttribute("recipe", recipeCommand);
        model.addAttribute("action", "Create");
        addFormAttributes(model);
        return "recipes/form";
    }
    
    private void addFormAttributes(Model model) {
        Set<IngredientCommand> ingredients = ingredientService.getAllIngredients();
        Set<UnitOfMeasureCommand> unitsOfMeasure = unitOfMeasureService.getAllUnitsOfMeasure();
        Set<CategoryCommand> categories = categoryService.getAllCategories();
        
        model.addAttribute("availableIngredients", ingredients);
        model.addAttribute("availableUnitsOfMeasure", unitsOfMeasure);
        model.addAttribute("availableCategories", categories);
        model.addAttribute("difficulties", Difficulty.values());
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            RecipeCommand recipeCommand = recipeService.findRecipeById(id);
            // If no ingredients, add one empty line
            if (recipeCommand.getRecipeIngredients().isEmpty()) {
                RecipeIngredientCommand emptyIngredient = new RecipeIngredientCommand();
                emptyIngredient.setAmount(BigDecimal.ZERO);
                recipeCommand.getRecipeIngredients().add(emptyIngredient);
            }
            model.addAttribute("recipe", recipeCommand);
            model.addAttribute("action", "Edit");
            addFormAttributes(model);
            return "recipes/form";
        }
        catch(RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/recipes";
        }
    }
    
    @PostMapping
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, 
    						BindingResult bindingResult,
    		                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        // Remove empty ingredients before validation
        recipeCommand.getRecipeIngredients().removeIf(ri -> 
            ri.getIngredientId() == null || ri.getUnitOfMeasureId() == null || 
            ri.getAmount() == null || ri.getAmount().compareTo(BigDecimal.ZERO) <= 0);
        
        //Handle errors
        if(bindingResult.hasErrors()) {
            model.addAttribute("action", (recipeCommand.getId() != null && !recipeCommand.getId().isEmpty()) ? "Edit" : "Create");
            addFormAttributes(model);
            return "recipes/form";
        }
        
    	if (imageFile != null && !imageFile.isEmpty()) {
    		try {
	    		// If a new image was uploaded, set it
	    		recipeCommand.setImage(imageFile.getBytes());
            } catch (IOException ex) {
                bindingResult.reject("imageFile", "Error processing image file");
                model.addAttribute("errorMessage", "Error reading uploaded image: " + ex.getMessage());
                addFormAttributes(model);
                return "recipes/form";
            }
    	}
    	
        log.info("Image size: {}", 
        	    recipeCommand.getImage() != null ? recipeCommand.getImage().length : "null");
        
    	
        try {
        	if (recipeCommand.getId() != null && !recipeCommand.getId().isEmpty()) {
        	    if (recipeCommand.getImage() == null || recipeCommand.getImage().length == 0) {
        	        // Only fetch and preserve the existing image when no new image was uploaded
        	        RecipeCommand existingRecipeCommand = recipeService.findRecipeById(recipeCommand.getId());
        	        recipeCommand.setImage(existingRecipeCommand.getImage());
        	    }
        	}
            RecipeCommand savedRecipeCommand = recipeService.saveRecipe(recipeCommand);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Recipe '" + savedRecipeCommand.getDescription() + "' saved successfully!");
            return "redirect:/recipes";
        }
        catch(IllegalArgumentException ex) {
            model.addAttribute("action", (recipeCommand.getId() != null && !recipeCommand.getId().isEmpty())  ? "Edit" : "Create");
            model.addAttribute("errorMessage", ex.getMessage());
            addFormAttributes(model);
            return "recipes/form";
        }
    }
    
    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            RecipeCommand recipeCommand = recipeService.findRecipeById(id);
            long ingredientCount = recipeService.getIngredientCountForRecipe(id);
            model.addAttribute("recipe", recipeCommand);
            model.addAttribute("ingredientCount", ingredientCount);
            return "recipes/delete";
        }
        catch(RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/recipes";
        }
    }
    
    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            RecipeCommand recipeCommand = recipeService.findRecipeById(id);
            recipeService.deleteRecipeById(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Recipe '" + recipeCommand.getDescription() + "' deleted successfully!");
        }
        catch(RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting recipe: " + ex.getMessage());
        }
        return "redirect:/recipes";
    }
    
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> renderRecipeImage(@PathVariable String id) {
        RecipeCommand recipeCommand = recipeService.findRecipeById(id);

        if (recipeCommand.getImage() == null || recipeCommand.getImage().length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // If your image can be PNG, adjust dynamically
        return new ResponseEntity<>(recipeCommand.getImage(), headers, HttpStatus.OK);
    }
}
