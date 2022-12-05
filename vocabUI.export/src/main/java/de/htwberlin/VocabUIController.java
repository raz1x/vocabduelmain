package de.htwberlin;

import de.htwberlin.game.export.GameDoesNotExistException;
import de.htwberlin.game.export.RoundDoesNotExistException;
import de.htwberlin.game.export.UserDoesNotExistException;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.vocab.export.CategoryNotFoundException;
import de.htwberlin.vocab.export.VocabListNotFoundException;
import de.htwberlin.vocab.export.VocabNotFoundException;

import java.io.IOException;

public interface VocabUIController {


    /**
     * Starts the application.
     */
    public void run() throws Exception;

    /**
     * Shows the login menu.
     * @throws UserAlreadyExistsException
     */
    public void showLoginMenu() throws UserAlreadyExistsException;

    /**
     * Shows the main menu.
     */
    public void showMainMenu();

    /**
     * Shows the user menu.
     */
    public void manageUserMenu() throws UserAlreadyExistsException;

    /**
     * Shows the vocab menu.
     */
    public void manageVocabMenu();

    public void manageGameMenu();

    public int readUserSelection();

    public String readUserInput();

    public void displayVocabList();

}
