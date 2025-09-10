package com.szamraj.recipe_app_mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.command.NotesCommand;
import com.szamraj.recipe_app_mongodb.model.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Override
	public NotesCommand convert(Notes notes) {
		if (notes == null) {
			return null;
		}

		NotesCommand notesCommand = new NotesCommand();

		notesCommand.setId(notes.getId());
		notesCommand.setRecipeNotes(notes.getRecipeNotes());

		return notesCommand;
	}
}
