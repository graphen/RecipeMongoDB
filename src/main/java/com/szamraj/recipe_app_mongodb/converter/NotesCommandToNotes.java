package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.NotesCommand;
import com.szamraj.recipe_app_mongodb.model.Notes;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

	@Override
	public Notes convert(NotesCommand notesCommand) {
		if (notesCommand == null) {
			return null;
		}
		
		Notes notes = new Notes();
		
		notes.setId(notesCommand.getId());
		notes.setRecipeNotes(notesCommand.getRecipeNotes());
		
		return notes;
	}
}
