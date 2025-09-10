package com.szamraj.recipe_app_mongodb.controller;

import java.util.Set;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.szamraj.recipe_app_mongodb.command.IngredientCommand;
import com.szamraj.recipe_app_mongodb.service.IngredientService;

import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
	
	IngredientService ingredientService;

	public IngredientController(IngredientService ingredientService) {
		super();
		this.ingredientService = ingredientService;
	}
	
	@GetMapping({"/list", "/", ""})
	public String listIngredients(Model model) {
		Set<IngredientCommand> ingredients = ingredientService.getAllIngredients();
		model.addAttribute("ingredients", ingredients);
		return "ingredients/list";
	}
	
	@GetMapping("/{id}")
	public String showIngredient(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
		try {
			IngredientCommand ingredientCommand = ingredientService.findIngredientById(id);
			long recipeCount = ingredientService.getRecipeCountForIngredient(id);
			model.addAttribute("ingredient", ingredientCommand);
			model.addAttribute("recipeCount", recipeCount);
			return "ingredients/detail";
		}
		catch(RuntimeException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
			return "redirect:/ingredients";
		}
	}
	
	@GetMapping({"/new", "/create"})
	public String showCreateForm(Model model) {
		model.addAttribute("ingredient", new IngredientCommand());
		model.addAttribute("action", "Create");
		return "ingredients/form";
	}
	
	@GetMapping({"/{id}/edit"})
	public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
		try {
			IngredientCommand ingredientCommand = ingredientService.findIngredientById(id);
			model.addAttribute("ingredient", ingredientCommand);
			model.addAttribute("action", "Edit");
			return "ingredients/form";
		}
		catch(RuntimeException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
			return "redirect:/ingredients";
		}
	}
	
	@PostMapping
	public String saveIngredient(@Valid @ModelAttribute("ingredient") IngredientCommand ingredientCommand, 
								BindingResult bindingResult, 
								Model model,
								RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("action", ingredientCommand.getId() != null ? "Edit" : "Create");
			return "ingredients/form";
		}
		
		try {
			IngredientCommand savedIngredientCommand = ingredientService.saveIngredient(ingredientCommand);
			redirectAttributes.addFlashAttribute("successMessage", 
					"Ingredient '" + savedIngredientCommand.getDescription() + "' saved successfully!");
			return "redirect:/ingredients";
		}
		catch(IllegalArgumentException ex) {
			model.addAttribute("action", ingredientCommand.getId() != null ? "Edit" : "Create");
			model.addAttribute("errorMessage", ex.getMessage());
			return "ingredients/form";
		}
	}
	
	@GetMapping("/{id}/delete")
	public String showDeleteConfirmation(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
		try {
			IngredientCommand ingredientCommand = ingredientService.findIngredientById(id);
			Long recipeCount = ingredientService.getRecipeCountForIngredient(id);
			model.addAttribute("ingredient", ingredientCommand);
			model.addAttribute("recipeCount", recipeCount);
			model.addAttribute("canDelete", recipeCount == 0);
			return "ingredients/delete";
		}
		catch(RuntimeException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
			return "redirect:/ingredients";
		}
	}

	@PostMapping("/{id}/delete")
	public String showDeleteConfirmation(@PathVariable String id, RedirectAttributes redirectAttributes) {
		try {
			IngredientCommand ingredientCommand = ingredientService.findIngredientById(id);
			ingredientService.deleteIngredientById(ingredientCommand.getId());
			redirectAttributes.addFlashAttribute("successMessage", 
	                "Ingredient '" + ingredientCommand.getDescription() + "' deleted successfully!");
		}
		catch(IllegalStateException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
			return "redirect:/ingredients";
		}
		catch(RuntimeException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error deleting ingredient: " + ex.getMessage());
			return "redirect:/ingredients";
		}
		return "redirect:/ingredients";
	}
}
