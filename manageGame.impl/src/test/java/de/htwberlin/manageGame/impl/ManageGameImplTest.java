package de.htwberlin.manageGame.impl;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.*;
import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.export.UserNotFoundException;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

// TODO: Implement
// Probleme mit Spring DI

@ExtendWith(MockitoExtension.class)
public class ManageGameImplTest {

    @Mock
    private ManageUser manageUser;

    @Mock
    private GameDAO gameDAO;

    @Mock
    private ManageVocab manageVocab;

    @InjectMocks
    private ManageGameImpl manageGame;

    private static User user1;
    private static User user2;
    private static Game game;
    private static List<Game> games;
    private static Category category;

    @BeforeAll
    public static void init() throws UserDAOPersistenceException, UserNotFoundException, GameDAOPersistenceException {
        user1 = new User("testuser1", "testpassword1");
        user1.setUserId(1);
        user2 = new User("testuser2", "testpassword2");
        user2.setUserId(2);
        game = new Game(1, 2);
        game.setGameId(1);
        games = new ArrayList<>();
        games.add(game);
        category = new Category("testcategory");
        category.setCategoryId(1);
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
    public void testCreateGame() throws UserNotFoundException, UserDoesNotExistException, GameDAOPersistenceException, UserDAOPersistenceException {
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
    public void testCreateGameUserNotFound() throws UserNotFoundException, UserDAOPersistenceException {
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
    public void getAllOngoingGamesForUser() throws GameDoesNotExistException, UserDoesNotExistException, UserNotFoundException, UserDAOPersistenceException {
        // 1. Arrange
        when(manageUser.getById(user1.getUserId())).thenReturn(user1);
        when(gameDAO.getOngoingGamesForUser(user1)).thenReturn(games);        // 2. Act
        List<Game> games = manageGame.getAllOngoingGamesForUser(user1.getUserId());
        // 3. Assert
        assert (games.size() == 1);
        verify(gameDAO, times(1)).getOngoingGamesForUser(user1);
        verify(manageUser, times(1)).getById(user1.getUserId());
    }

    @Test
    public void getAllOngoingGamesForUserGamesDoNotExist() throws UserNotFoundException, UserDAOPersistenceException, GameDoesNotExistException, UserDoesNotExistException {
        when(manageUser.getById(user1.getUserId())).thenReturn(user1);
        List<Game> emptyList = new ArrayList<>();
        when(gameDAO.getOngoingGamesForUser(user1)).thenReturn(emptyList);        // 2. Act
        List<Game> games = manageGame.getAllOngoingGamesForUser(user1.getUserId());
        // 3. Assert
        Assertions.assertThrows(GameDoesNotExistException.class, ()-> manageGame.getAllOngoingGamesForUser(user1.getUserId()));
        verify(gameDAO, times(1)).getOngoingGamesForUser(user1);
        verify(manageUser, times(1)).getById(user1.getUserId());
    }

    //TODO: geht nicht
    @Test
    public void getAllOngoingGamesForUserUserDoesNotExist() throws UserNotFoundException, UserDAOPersistenceException {
        // 1. Arrange
        // 2. Act
        Assertions.assertThrows(UserDoesNotExistException.class, ()-> manageGame.getAllOngoingGamesForUser(user1.getUserId()));
        // 3. Assert
        verify(manageUser, times(1)).getById(user1.getUserId());
    }

    @Test
    public void createRound() throws Exception {
        // 1. Arrange
        ManageGameImpl manageGameSpy = spy(ManageGameImpl.class);
        Round round = new Round(game, 1, category);
        List<GameQuestion> gameQuestions = new ArrayList<>();
        when(gameDAO.getGame(game.getGameId())).thenReturn(game);
        when(manageVocab.getCategory(1)).thenReturn(category);
        //when(manageGameSpy.generateQuestions(1, 1, round)).thenReturn(gameQuestions);
        lenient().doReturn(gameQuestions).when(manageGameSpy).generateQuestions(anyInt(), anyInt(), any(Round.class));
        // 2. Act
        manageGameSpy.createRound(game.getGameId(), 1, 1);
        // 3. Assert
        verify(gameDAO, times(1)).getGame(game.getGameId());
        verify(manageVocab, times(1)).getCategory(1);
        verify(gameDAO, times(1)).updateGame(game);
        verify(gameDAO, times(1)).saveRound(new Round(game, 1, category));
    }

    @Test
    public void updateRound() throws GameDAOPersistenceException, RoundDoesNotExistException {
        // 1. Arrange
        Round round = new Round(game, 1, category);
        when(gameDAO.updateRound(round)).thenReturn(round);
        // 2. Act
        Round testRound = manageGame.updateRound(round);
        // 3. Assert
        assert (testRound.getGame().getGameId() == 1);
        assert (testRound.getRoundNumber() == 1);
        assert (testRound.getCategory().getCategoryId() == 1);
        verify(gameDAO, times(1)).updateRound(round);
    }

    @Test
    public void endRound() throws RoundDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        Round round = new Round(game, 1, category);
        Round spyround = spy(round);
        when(gameDAO.getRoundById(anyInt())).thenReturn(spyround);
        // 2. Act
        manageGame.endRound(1);
        // 3. Assert
        verify(gameDAO, times(1)).updateRound(round);
        verify(spyround, times(1)).setOnGoing(false);
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