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
        manageUser.registerUser("raz", "12354");
    }
}

