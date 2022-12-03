package de.htwberlin;

import de.htwberlin.userManager.export.UserAlreadyExistsException;

public interface VocabUIController {


    /**
     * Starts the application.
     */
    public void run() throws UserAlreadyExistsException;

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
