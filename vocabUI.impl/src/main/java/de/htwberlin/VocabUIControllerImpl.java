package de.htwberlin;

import de.htwberlin.game.export.ManageGame;
import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.vocab.export.ManageVocab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VocabUIControllerImpl implements VocabUIController {

    @Autowired
    private ManageUser manageUser;

    @Autowired
    private ManageGame manageGame;

    @Autowired
    private ManageVocab manageVocab;

    @Override
    public void run() throws UserAlreadyExistsException {
        manageUser.registerUser("daimox", "12354");
    }

    @Override
    public void showLoginMenu() throws UserAlreadyExistsException {

    }

    @Override
    public void showMainMenu() {

    }

    @Override
    public void manageUserMenu() throws UserAlreadyExistsException {

    }

    @Override
    public void manageVocabMenu() {

    }

    @Override
    public void manageGameMenu() {

    }

    @Override
    public int readUserSelection() {
        return 0;
    }

    @Override
    public String readUserInput() {
        return null;
    }

    @Override
    public void displayVocabList() {

    }
}

