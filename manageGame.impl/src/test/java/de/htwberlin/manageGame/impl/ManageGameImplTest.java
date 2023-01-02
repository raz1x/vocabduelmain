package de.htwberlin.manageGame.impl;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.ManageVocab;
import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.export.UserNotFoundException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

// TODO: Implement
// Probleme mit Spring DI

@ExtendWith(MockitoExtension.class)
public class ManageGameImplTest {

    @InjectMocks
    private ManageGameImpl manageGame;

    @Mock
    private static ManageUser manageUser;

    @Mock
    private static GameDAO gameDAO;

    @Mock
    private static ManageVocab vocabDAO;

    private static User user1;
    private static User user2;
    private static Game game;

    @BeforeAll
    public static void init() throws UserDAOPersistenceException, UserNotFoundException, GameDAOPersistenceException {
        user1 = new User("testuser1", "testpassword1");
        user1.setUserId(1);
        user2 = new User("testuser2", "testpassword2");
        user2.setUserId(2);
        game = new Game(1, 2);
        game.setGameId(1);
    }

    @AfterAll
    public static void tearDown() throws UserDAOPersistenceException, GameDAOPersistenceException {
    }

    @BeforeEach
    public void setUp() throws UserDAOPersistenceException, UserNotFoundException {
    }

    @AfterEach
    public void destroy() throws UserDAOPersistenceException, UserNotFoundException {
    }

    @Test
    public void testCreateGame() throws UserNotFoundException, UserDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        when(manageUser.getById(1)).thenReturn(user1);
        when(manageUser.getById(2)).thenReturn(user2);
        // 2. Act
        Game game = manageGame.createGame(1, 2);
        // 3. Assert
        assert (game.getUser1Id() == 1);
        assert (game.getUser2Id() == 2);
        assert (game.getCurrentUser() == 1);
        assert (game.getUserStartingRound() == 1);
        verify(manageUser, times(1)).getById(1);
        verify(manageUser, times(1)).getById(2);
        verify(gameDAO, times(1)).saveGame(game);

    }
    @Test
    public void testCreateGameUserNotFound() throws UserNotFoundException {
        // 1. Arrange
        // 2. Act & Assert
        Assertions.assertThrows(UserDoesNotExistException.class, () -> manageGame.createGame(user1.getUserId(), user2.getUserId()));
        verify(manageUser, times(1)).getById(user1.getUserId());
    }

    @Test
    public void testGetGame() throws GameDoesNotExistException {
        // 1. Arrange
        when(gameDAO.getGame(1)).thenReturn(game);
        // 2. Act
        Game game = manageGame.getGame(1);
        // 3. Assert
        assert (game.getGameId() == 1);
        assert (game.getUser1Id() == 1);
        assert (game.getUser2Id() == 2);
        verify(gameDAO, times(1)).getGame(1);
    }

    @Test
    public void testGetGameGameDoesNotExist() throws GameDoesNotExistException {
        // 1. Arrange
        // 2. Act
        Assertions.assertThrows(GameDoesNotExistException.class, ()-> manageGame.getGame(1));
        // 3. Assert
        verify(gameDAO, times(1)).getGame(1);
    }


    @Test
    public void testUpdateGame() throws GameDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        when(gameDAO.updateGame(game)).thenReturn(game);
        // 2. Act
        Game testGame = manageGame.updateGame(game);
        // 3. Assert
        assert (testGame.getUser1Id() == 1);
        assert (testGame.getUser2Id() == 2);
        verify(gameDAO, times(1)).updateGame(game);
    }

    @Test
    public void testUpdateGameGameDoesNotExist() throws GameDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        // 2. Act
        Assertions.assertThrows(GameDoesNotExistException.class, ()-> manageGame.updateGame(game));
        // 3. Assert
        verify(gameDAO, times(1)).updateGame(game);
    }


    @Test
    public void testEndGame() throws GameDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        when(gameDAO.getGame(1)).thenReturn(game);
        // 2. Act
        manageGame.endGame(1);
        // 3. Assert
        verify(gameDAO, times(1)).updateGame(game);
        verify(gameDAO, times(1)).getGame(1);
    }

    @Test
    public void testEndGameGameDoesNotExist() throws GameDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        // 2. Act
        Assertions.assertThrows(GameDoesNotExistException.class, ()-> manageGame.endGame(1));
        // 3. Assert
        verify(gameDAO, times(1)).getGame(1);
        verify(gameDAO, times(0)).updateGame(game);
    }

    @Test
    public void getAllOngoingGamesForUser() throws GameDoesNotExistException {
        // 1. Arrange
        // 2. Act
        when(gameDAO.getOngoingGamesForUser(user1)).thenReturn(new ArrayList<>());
        // 3. Assert
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