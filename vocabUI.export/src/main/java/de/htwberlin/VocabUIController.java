package de.htwberlin;

import de.htwberlin.userManager.export.UserAlreadyExistsException;

public interface VocabUIController {

    void run() throws UserAlreadyExistsException;
}
