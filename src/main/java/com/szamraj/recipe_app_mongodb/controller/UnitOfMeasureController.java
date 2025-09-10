package com.szamraj.recipe_app_mongodb.controller;


import java.util.Set;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.szamraj.recipe_app_mongodb.command.UnitOfMeasureCommand;
import com.szamraj.recipe_app_mongodb.service.UnitOfMeasureService;

import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/unitsofmeasure")
public class UnitOfMeasureController {
    
    private final UnitOfMeasureService unitOfMeasureService;
    
    public UnitOfMeasureController(UnitOfMeasureService unitOfMeasureService) {
        this.unitOfMeasureService = unitOfMeasureService;
    }
    
    @GetMapping({"/list", "/", ""})
    public String listUnitsOfMeasure(Model model) {
        Set<UnitOfMeasureCommand> unitsOfMeasure = unitOfMeasureService.getAllUnitsOfMeasure();
        model.addAttribute("unitsOfMeasure", unitsOfMeasure);
        return "unitsofmeasure/list";
    }
    
    @GetMapping("/{id}")
    public String showUnitOfMeasure(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UnitOfMeasureCommand unitOfMeasure = unitOfMeasureService.findUnitOfMeasureById(id);
            long usageCount = unitOfMeasureService.getRecipeIngredientCountForUnitOfMeasure(id);
            model.addAttribute("unitOfMeasure", unitOfMeasure);
            model.addAttribute("usageCount", usageCount);
            return "unitsofmeasure/detail";
        } 
        catch(RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/unitsofmeasure";
        }
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("unitOfMeasure", new UnitOfMeasureCommand());
        model.addAttribute("action", "Create");
        return "unitsofmeasure/form";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UnitOfMeasureCommand unitOfMeasure = unitOfMeasureService.findUnitOfMeasureById(id);
            model.addAttribute("unitOfMeasure", unitOfMeasure);
            model.addAttribute("action", "Edit");
            return "unitsofmeasure/form";
        } 
        catch(RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/unitsofmeasure";
        }
    }
    
    @PostMapping
    public String saveUnitOfMeasure(@Valid @ModelAttribute("unitOfMeasure") UnitOfMeasureCommand unitOfMeasureCommand,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", unitOfMeasureCommand.getId() != null ? "Edit" : "Create");
            return "unitsofmeasure/form";
        }
        
        try {
            UnitOfMeasureCommand savedUnitOfMeasure = unitOfMeasureService.saveUnitOfMeasure(unitOfMeasureCommand);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Unit of Measure '" + savedUnitOfMeasure.getDescription() + "' saved successfully!");
            return "redirect:/unitsofmeasure";
        } 
        catch(IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("action", unitOfMeasureCommand.getId() != null ? "Edit" : "Create");
            return "unitsofmeasure/form";
        }
    }
    
    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UnitOfMeasureCommand unitOfMeasure = unitOfMeasureService.findUnitOfMeasureById(id);
            long usageCount = unitOfMeasureService.getRecipeIngredientCountForUnitOfMeasure(id);
            model.addAttribute("unitOfMeasure", unitOfMeasure);
            model.addAttribute("usageCount", usageCount);
            model.addAttribute("canDelete", usageCount == 0);
            return "unitsofmeasure/delete";
        } 
        catch(RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/unitsofmeasure";
        }
    }
    
    @PostMapping("/{id}/delete")
    public String deleteUnitOfMeasure(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            UnitOfMeasureCommand unitOfMeasure = unitOfMeasureService.findUnitOfMeasureById(id);
            unitOfMeasureService.deleteUnitOfMeasureById(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Unit of Measure '" + unitOfMeasure.getDescription() + "' deleted successfully!");
        } 
        catch(IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } 
        catch(RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting unit of measure: " + ex.getMessage());
        }
        return "redirect:/unitsofmeasure";
    }   
}
