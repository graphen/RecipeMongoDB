package com.szamraj.recipe_app_mongodb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(Exception ex) {
    	log.error("Handling not found exception: " + ex.getMessage());
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("error404");
    	modelAndView.addObject("exception", ex);
	 	return modelAndView;
	}
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView NumberFormatException(Exception ex) {
    	log.error("Handling not found exception: " + ex.getMessage());
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("exception");
    	modelAndView.addObject("exception", ex);
	 	return modelAndView;
	}
}
