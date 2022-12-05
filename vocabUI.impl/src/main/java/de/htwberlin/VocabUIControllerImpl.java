package de.htwberlin;

import de.htwberlin.game.export.*;
import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.vocab.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

@Controller
public class VocabUIControllerImpl implements VocabUIController {

    @Autowired
    private ManageUser manageUser;

    @Autowired
    private ManageGame manageGame;

    @Autowired
    private ManageVocab manageVocab;

    @Override
    public void run() throws Exception {
/*
        User user1 = manageUser.getByName("user1");
        User user2 = manageUser.getByName("user2");
        Game game = manageGame.createGame(user1.getUserId(), user2.getUserId());
        manageGame.createRound(game.getGameId(), 1, 5);
  */
        User user1 = manageUser.registerUser("user1", "password");
        User user2 = manageUser.registerUser("user2", "password");
        File file = new File("textFiles/animals_farm_zoo.txt");
        manageVocab.parseVocabList(file);
        //manageUser.registerUser("daimox", "12354");
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

