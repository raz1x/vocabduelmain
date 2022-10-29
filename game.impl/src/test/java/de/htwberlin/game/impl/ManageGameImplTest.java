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

    @Test
    public void testContinueGame() throws GameDoesNotExistException {
        Game game = manageGame.continueGame(0);
        assertEquals(game.getGameId(), 0);
    }

    @Test
    public void testEndGame() {
        manageGame.endGame(0);
        // cannot assert yet
    }

    @Test
    public void testCreateRound() throws GameDoesNotExistException {
        Round round = manageGame.createRound(0, 1, 1);
        assertEquals(round.getGameId(), 0);
        assertEquals(round.getRound(), 1);
        assertEquals(round.getCategoryId(), 1);
    }

    @Test
    public void testCreateRoundResult() throws UserDoesNotExistException {
        RoundResult roundResult = manageGame.createRoundResult(0, 1);
        assertEquals(roundResult.getChosenAnswerId(), 0);
        assertEquals(roundResult.getUserId(), 1);
    }

    @Test
    public void testGenerateQuestions() throws VocabNotFoundException, VocabListNotFoundException, CategoryNotFoundException {
        List<GameQuestion> gameQuestions = manageGame.generateQuestions(1, 0, 1);
        assertEquals(gameQuestions.size(), 3);
    }

    @Test
    public void testGenerateAnswers() throws VocabNotFoundException {
        List<GameAnswer> gameAnswers = manageGame.generateAnswers(0);
        assertEquals(gameAnswers.size(), 3);
    }
}