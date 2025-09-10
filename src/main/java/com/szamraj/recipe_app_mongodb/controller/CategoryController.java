package com.szamraj.recipe_app_mongodb.controller;


import java.util.Set;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.szamraj.recipe_app_mongodb.command.CategoryCommand;
import com.szamraj.recipe_app_mongodb.service.CategoryService;

import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService) {
    	super();
        this.categoryService = categoryService;
    }
    
    @GetMapping({"/list", "/", ""})
    public String listCategories(Model model) {
        Set<CategoryCommand> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }
    
    @GetMapping("/{id}")
    public String showCategory(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            CategoryCommand category = categoryService.findCategoryById(id);
            long recipeCount = categoryService.getRecipeCountForCategory(id);
            model.addAttribute("category", category);
            model.addAttribute("recipeCount", recipeCount);
            return "categories/detail";
        } 
        catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/categories";
        }
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryCommand());
        model.addAttribute("action", "Create");
        return "categories/form";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            CategoryCommand category = categoryService.findCategoryById(id);
            model.addAttribute("category", category);
            model.addAttribute("action", "Edit");
            return "categories/form";
        } 
        catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/categories";
        }
    }
    
    @PostMapping
    public String saveCategory(@Valid @ModelAttribute("category") CategoryCommand categoryCommand,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", categoryCommand.getId() != null ? "Edit" : "Create");
            return "categories/form";
        }
        
        try {
            CategoryCommand savedCategory = categoryService.saveCategory(categoryCommand);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Category '" + savedCategory.getDescription() + "' saved successfully!");
            return "redirect:/categories";
        } 
        catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("action", categoryCommand.getId() != null ? "Edit" : "Create");
            return "categories/form";
        }
    }
    
    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            CategoryCommand category = categoryService.findCategoryById(id);
            long recipeCount = categoryService.getRecipeCountForCategory(id);
            model.addAttribute("category", category);
            model.addAttribute("recipeCount", recipeCount);
            model.addAttribute("canDelete", recipeCount == 0);
            return "categories/delete";
        } 
        catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/categories";
        }
    }
    
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            CategoryCommand category = categoryService.findCategoryById(id);
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Category '" + category.getDescription() + "' deleted successfully!");
        } 
        catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } 
        catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting category: " + ex.getMessage());
        }
        return "redirect:/categories";
    }
}
