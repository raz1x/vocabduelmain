package de.htwberlin.game.impl;

import de.htwberlin.game.export.*;
import de.htwberlin.vocab.export.CategoryNotFoundException;
import de.htwberlin.vocab.export.VocabListNotFoundException;
import de.htwberlin.vocab.export.VocabNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManageGameImplTest {
    private static ManageGameImpl manageGame;

    @BeforeAll
    public static void init() {
        manageGame = new ManageGameImpl();
    }

    @Test
    public void testCreateGame() throws UserDoesNotExistException {
        // get Users from DB
        Game game = manageGame.createGame(1,2);
        assertEquals(game.getGameId(), 0);
    }
}