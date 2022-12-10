package de.htwberlin;

import de.htwberlin.game.export.Game;
import de.htwberlin.game.export.GameDoesNotExistException;
import de.htwberlin.game.export.RoundDoesNotExistException;
import de.htwberlin.game.export.UserDoesNotExistException;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserNotFoundException;
import de.htwberlin.vocab.export.CategoryNotFoundException;
import de.htwberlin.vocab.export.VocabListNotFoundException;
import de.htwberlin.vocab.export.VocabNotFoundException;
import de.htwberlin.userManager.export.WrongPasswordException;

import java.io.IOException;

public interface VocabUIController {


    /**
     * Starts the application.
     */
    public void run();

    /**
     * Shows the login UI.
     */
    public void showLogin();

    /**
     * Shows the register UI.
     */
    public void showRegister();

    /**
     * Shows the main menu.
     */
    public void showMainMenu();

    /**
     * Shows the menu to access different components.
     */
    public void showManagerMenu();

    /**
     * Shows the user menu.
     */
    public void manageUserMenu();

    /**
     * Shows the delete user UI.
     */
    public void showDeleteUser();
    /**
     * Shows the vocab menu.
     */
    public void manageVocabMenu();

    /**
     * Imports a vocab list from a given textfile.
     */
    public void showImportVocabList();
    /**
     * Shows the game menu.
     */
    public void manageGameMenu();

    /**
     * Shows the create game UI.
     */
    public void showCreateGame();

    /**
     * Shows the create round UI.
     * @param game the game the round should be created for
     */
    public void showCreateRound(Game game);

    /**
     * Shows all games of a user.
     */
    public void showGamesOfUser();

    /**
     * Shows the round UI.
     * @param game The game the round belongs to.
     */
    public void playRoundUI(Game game);

    /**
     * Reads the user input as integer
     * @return The user input as integer
     */
    public int readUserSelection();

    /**
     * Reads the user input as string.
     * @return The user input as string.
     */
    public String readUserInput();

    /**
     * Shows the results of a game.
     * @param game The game to show the results of.
     */
    public void showGameResults(Game game);
}
