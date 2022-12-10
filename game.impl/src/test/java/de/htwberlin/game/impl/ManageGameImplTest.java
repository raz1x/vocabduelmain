package de.htwberlin.game.impl;

import de.htwberlin.AppConfig;
import de.htwberlin.game.export.*;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAO;
import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.junit.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

// TODO: Implement
// Probleme mit Spring DI

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ManageGameImplTest {

    @Autowired
    private ManageGame manageGame;

    @Autowired
    private static UserDAO userDAO;

    @Autowired
    private static GameDAO gameDAO;

    private static User user1;
    private static User user2;
    private static Game game;

    @BeforeClass
    public static void init() throws UserDAOPersistenceException, UserNotFoundException, GameDAOPersistenceException {
        userDAO.saveUser(new User("testUser1", "testUser1"));
        userDAO.saveUser(new User("testUser2", "testUser2"));
        user1 = userDAO.getUserByName("testUser1");
        user2 = userDAO.getUserByName("testUser2");
        game = gameDAO.saveGame(new Game(user1.getUserId(), user2.getUserId()));
    }

    @AfterClass
    public static void tearDown() throws UserDAOPersistenceException, GameDAOPersistenceException {
        userDAO.deleteUser(user1);
        userDAO.deleteUser(user2);
        gameDAO.deleteGame(game);
    }

    @Before
    public void setUp() throws UserDAOPersistenceException, UserNotFoundException {
    }

    @After
    public void destroy() throws UserDAOPersistenceException, UserNotFoundException {
    }

    @Test
    public void createGame() throws UserDoesNotExistException {
        Game game = manageGame.createGame(user1.getUserId(), user2.getUserId());
        assert (game.getGameId() > 0);
        assert (game.getUser1Id() == user1.getUserId());
        assert (game.getUser2Id() == user2.getUserId());
        assert (game.getIsOngoing());
        assert (game.getCurrentUser() == user1.getUserId());
    }

    @Test
    public void getGame() throws GameDoesNotExistException {
        Game game = manageGame.getGame(game.getGameId());
        assert (game.getGameId() > 0);
        assert (game.getUser1Id() == user1.getUserId());
        assert (game.getUser2Id() == user2.getUserId());
        assert (game.getIsOngoing());
        assert (game.getCurrentUser() == user1.getUserId());
    }
    @Test
    public void updateGame() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void endGame() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void getAllOngoingGamesForUser() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void createRound() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void getRound() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void updateRound() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void endRound() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void generateQuestions() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void generateAnswers() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void getGameQuestionsForRound() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void getGameAnswersForGameQuestion() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void getNextQuestion() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void lockInAnswer() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void getScoreForUser() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void getLatestRoundForGame() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }

    @Test
    public void getOngoingRoundForGame() {
        // TODO: Implement
        // Konnte nicht implementiert werden aufgrund von Problemen mit Spring DI
    }
}