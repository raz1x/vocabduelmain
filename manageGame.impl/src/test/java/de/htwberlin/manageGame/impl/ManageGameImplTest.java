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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

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
    private static Round round;
    private static User user;

    @BeforeAll
    public static void init() {
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
        round = new Round(game, 1, category);
        round.setRoundId(1);
        user = new User("testuser", "testpassword");
        user.setUserId(1);
    }

    @AfterAll
    public static void tearDown() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void destroy() {
    }

    @Test
    public void testCreateGame() throws UserNotFoundException, GameDAOPersistenceException {
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
    public void testCreateGamePersistenceException() throws UserNotFoundException, GameDAOPersistenceException {
        // 1. Arrange
        when(manageUser.getById(1)).thenReturn(user1);
        when(manageUser.getById(2)).thenReturn(user2);
        when(gameDAO.saveGame(game)).thenThrow(GameDAOPersistenceException.class);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.createGame(1, 2));
        // 3. Assert
        verify(manageUser, times(1)).getById(1);
        verify(manageUser, times(1)).getById(2);
    }

    @Test
    public void testCreateGameUserNotFound() throws UserNotFoundException {
        // 1. Arrange
        when(manageUser.getById(1)).thenThrow(UserNotFoundException.class);
        // 2. Act & Assert
        Assertions.assertThrows(UserNotFoundException.class, () -> manageGame.createGame(user1.getUserId(), user2.getUserId()));
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
    public void testUpdateGamePersistenceException() throws GameDAOPersistenceException {
        // 1. Arrange
        when(gameDAO.updateGame(game)).thenThrow(GameDAOPersistenceException.class);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.updateGame(game));
        // 3. Assert
        verify(gameDAO, times(1)).updateGame(game);
    }

    @Test
    public void testUpdateGameGameDoesNotExist() throws GameDAOPersistenceException {
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
    public void testEndGamePersistenceException() throws GameDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        when(gameDAO.getGame(1)).thenReturn(game);
        when(gameDAO.updateGame(game)).thenThrow(GameDAOPersistenceException.class);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.endGame(1));
        // 3. Assert
        verify(gameDAO, times(1)).updateGame(game);
        verify(gameDAO, times(1)).getGame(1);
    }

    @Test
    public void testEndGameGameDoesNotExist() throws GameDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        when(gameDAO.getGame(1)).thenThrow(GameDoesNotExistException.class);
        // 2. Act
        Assertions.assertThrows(GameDoesNotExistException.class, ()-> manageGame.endGame(1));
        // 3. Assert
        verify(gameDAO, times(1)).getGame(1);
        verify(gameDAO, times(0)).updateGame(game);
    }

    @Test
    public void getAllOngoingGamesForUser() throws GameDoesNotExistException, UserNotFoundException {
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
    public void getAllOngoingGamesForUserGamesDoNotExist() throws UserNotFoundException, GameDoesNotExistException {
        // 1. Arrange
        when(manageUser.getById(user1.getUserId())).thenReturn(user1);
        List<Game> emptyList = new ArrayList<>();
        when(gameDAO.getOngoingGamesForUser(user1)).thenReturn(emptyList);
        // 2. Act & Assert
        Assertions.assertThrows(GameDoesNotExistException.class, ()-> manageGame.getAllOngoingGamesForUser(user1.getUserId()));
        verify(gameDAO, times(1)).getOngoingGamesForUser(user1);
        verify(manageUser, times(1)).getById(user1.getUserId());
    }

    @Test
    public void getAllOngoingGamesForUserUserNotFound() throws UserNotFoundException {
        // 1. Arrange
        when(manageUser.getById(user1.getUserId())).thenThrow(UserNotFoundException.class);
        // 2. Act
        Assertions.assertThrows(UserNotFoundException.class, ()-> manageGame.getAllOngoingGamesForUser(user1.getUserId()));
        // 3. Assert
        verify(manageUser, times(1)).getById(user1.getUserId());
    }

    // geht nicht, weil trotz spy manageGameImpl hier aufgerufen wird und kein Mock ist
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
        manageGame.createRound(game.getGameId(), 1, 1);
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
    public void updateRoundPersistenceException() throws GameDAOPersistenceException, RoundDoesNotExistException {
        // 1. Arrange
        Round round = new Round(game, 1, category);
        when(gameDAO.updateRound(round)).thenThrow(GameDAOPersistenceException.class);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.updateRound(round));
        // 3. Assert
        verify(gameDAO, times(1)).updateRound(round);
    }

    @Test
    public void endRound() throws RoundDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        Round spyround = spy(round);
        when(gameDAO.getRoundById(anyInt())).thenReturn(spyround);
        // 2. Act
        manageGame.endRound(1);
        // 3. Assert
        verify(gameDAO, times(1)).updateRound(spyround);
        verify(spyround, times(1)).setIsOngoing(false);
    }

    @Test
    public void endRoundPersistenceException() throws RoundDoesNotExistException, GameDAOPersistenceException {
        // 1. Arrange
        Round spyround = spy(round);
        when(gameDAO.getRoundById(anyInt())).thenReturn(spyround);
        doThrow(GameDAOPersistenceException.class).when(gameDAO).updateRound(spyround);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.endRound(1));
        // 3. Assert
        verify(gameDAO, times(1)).updateRound(spyround);
        verify(spyround, times(1)).setIsOngoing(false);
    }

    @Test
    public void generateQuestions() throws VocabListNotFoundException, CategoryNotFoundException, GameDAOPersistenceException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException {
        // 1. Arrange
        VocabList vocabList = new VocabList(category, "testList", "test", "test");
        vocabList.setVocabListId(1);
        Vocab vocab = new Vocab(vocabList, "test");
        doReturn(vocabList).when(manageVocab).getRandomVocabListFromCategory(anyInt());
        when(manageVocab.getRandomVocabFromVocabList(1)).thenReturn(vocab);
        // 2. Act
        List<GameQuestion> gameQuestions = manageGame.generateQuestions(1, 1, round);
        // 3. Assert
        verify(gameDAO, times(3)).saveGameQuestion(any(GameQuestion.class));
        verify(manageVocab, times(3)).getRandomVocabFromVocabList(1);
        assert (gameQuestions.size() == 3);
    }

    @Test
    public void generateQuestionsPersistenceExceptionWhileSavingGameQuestion() throws GameDAOPersistenceException, VocabNotFoundException, VocabListNotFoundException, CategoryNotFoundException {
        // 1. Arrange
        VocabList vocabList = new VocabList(category, "testList", "test", "test");
        vocabList.setVocabListId(1);
        Vocab vocab = new Vocab(vocabList, "test");
        doReturn(vocabList).when(manageVocab).getRandomVocabListFromCategory(anyInt());
        when(manageVocab.getRandomVocabFromVocabList(1)).thenReturn(vocab);
        doThrow(GameDAOPersistenceException.class).when(gameDAO).saveGameQuestion(any(GameQuestion.class));
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.generateQuestions(1, 1, round));
        // 3. Assert
        verify(gameDAO, times(1)).saveGameQuestion(any(GameQuestion.class));
        verify(manageVocab, times(1)).getRandomVocabFromVocabList(1);
    }

    @Test
    public void generateQuestionsPersistenceExceptionWhileSavingGameAnswer() throws GameDAOPersistenceException, VocabNotFoundException, VocabListNotFoundException, CategoryNotFoundException {
        // 1. Arrange
        VocabList vocabList = new VocabList(category, "testList", "test", "test");
        vocabList.setVocabListId(1);
        Vocab vocab = new Vocab(vocabList, "test");
        doReturn(vocabList).when(manageVocab).getRandomVocabListFromCategory(anyInt());
        when(manageVocab.getRandomVocabFromVocabList(1)).thenReturn(vocab);
        doThrow(GameDAOPersistenceException.class).when(gameDAO).saveGameAnswer(any(GameAnswer.class));
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.generateQuestions(1, 1, round));
        // 3. Assert
        verify(gameDAO, times(1)).saveGameQuestion(any(GameQuestion.class));
        verify(manageVocab, times(1)).getRandomVocabFromVocabList(1);
    }

    @Test
    public void generateAnswers() throws GameQuestionDoesNotExistException, TranslationNotFoundException, VocabNotFoundException, GameDAOPersistenceException {
        // 1. Arrange
        VocabList vocabList = new VocabList(category, "testList", "test", "test");
        Vocab vocab = new Vocab(vocabList, "test");
        List<Translation> translations = new ArrayList<>();
        translations.add(new Translation("test1"));
        translations.add(new Translation("test2"));
        translations.add(new Translation("test3"));
        Translation translation = new Translation("test");
        GameQuestion gameQuestion = new GameQuestion(round, vocab, translation, 1);
        when(gameDAO.getGameQuestion(anyInt())).thenReturn(gameQuestion);
        doReturn(translations).when(manageVocab).getPossibleTranslationsFromVocabId(anyInt(), anyInt());
        // 2. Act
        List<GameAnswer> gameAnswers = manageGame.generateAnswers(1);
        // 3. Assert
        verify(gameDAO, times(3)).saveGameAnswer(any(GameAnswer.class));
        assert (gameAnswers.size() == 3);
        assert (gameAnswers.get(0).getTranslation().getTranslation().equals("test1"));
    }

    @Test
    public void generateAnswersPersistenceException() throws GameQuestionDoesNotExistException, TranslationNotFoundException, VocabNotFoundException, GameDAOPersistenceException {
        // 1. Arrange
        VocabList vocabList = new VocabList(category, "testList", "test", "test");
        Vocab vocab = new Vocab(vocabList, "test");
        List<Translation> translations = new ArrayList<>();
        translations.add(new Translation("test1"));
        translations.add(new Translation("test2"));
        translations.add(new Translation("test3"));
        Translation translation = new Translation("test");
        GameQuestion gameQuestion = new GameQuestion(round, vocab, translation, 1);
        when(gameDAO.getGameQuestion(anyInt())).thenReturn(gameQuestion);
        doReturn(translations).when(manageVocab).getPossibleTranslationsFromVocabId(anyInt(), anyInt());
        doThrow(GameDAOPersistenceException.class).when(gameDAO).saveGameAnswer(any(GameAnswer.class));
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.generateAnswers(1));
        // 3. Assert
        verify(gameDAO, times(1)).saveGameAnswer(any(GameAnswer.class));
    }

    @Test
    public void getGameQuestionsForRound() throws RoundDoesNotExistException, GameDoesNotExistException, GameQuestionDoesNotExistException {
        // 1. Arrange
        List<GameQuestion> gameQuestions = new ArrayList<>();
        Set<Translation> translations = new HashSet<>();
        translations.add(new Translation("test"));
        Vocab vocab = new Vocab(new VocabList(category, "testList", "test", "test"), "test");
        vocab.setTranslations(translations);
        gameQuestions.add(new GameQuestion(round, vocab, new Translation("test"), 1));
        when(gameDAO.getGameQuestionsForRound(anyInt(), anyInt())).thenReturn(gameQuestions);
        // 2. Act
        List<GameQuestion> testGameQuestions = manageGame.getGameQuestionsForRound(1, 1);
        // 3. Assert
        assert (testGameQuestions.size() == 1);
        assert (testGameQuestions.get(0).getRound().getRoundNumber() == 1);
        assert (testGameQuestions.get(0).getRound().getGame().getGameId() == 1);
        verify(gameDAO, times(1)).getGameQuestionsForRound(1, 1);
    }

    @Test
    public void getGameAnswersForGameQuestion() throws GameAnswerDoesNotExistException {
        // 1. Arrange
        List<GameAnswer> gameAnswers = new ArrayList<>();
        Set<Translation> translations = new HashSet<>();
        translations.add(new Translation("test"));
        Vocab vocab = new Vocab(new VocabList(category, "testList", "test", "test"), "test");
        vocab.setTranslations(translations);
        GameAnswer gameAnswer = new GameAnswer(new GameQuestion(round, vocab, new Translation("test"), 1), new Translation("test"));
        gameAnswers.add(gameAnswer);
        when(gameDAO.getGameAnswersForGameQuestion(anyInt())).thenReturn(gameAnswers);
        // 2. Act
        List<GameAnswer> testGameAnswers = manageGame.getGameAnswersForGameQuestion(1);
        // 3. Assert
        assert (testGameAnswers.size() == 1);
        verify(gameDAO, times(1)).getGameAnswersForGameQuestion(1);
    }


    @Test
    public void lockInAnswer() throws GameAnswerDoesNotExistException, UserNotFoundException, GameDAOPersistenceException {
        // 1. Arrange
        GameAnswer gameAnswer = new GameAnswer(new GameQuestion(round, new Vocab(new VocabList(category, "testList", "test", "test"), "test"), new Translation("test"), 1), new Translation("test"));
        when(gameDAO.getGameAnswer(1)).thenReturn(gameAnswer);
        when(manageUser.getById(1)).thenReturn(user);
        // 2. Act
        manageGame.lockInAnswer(1, 1, false);
        // 3. Assert
        verify(gameDAO, times(1)).saveRoundResult(any(RoundResult.class));
    }

    @Test
    public void lockInAnswerPersistenceException() throws GameAnswerDoesNotExistException, UserNotFoundException, GameDAOPersistenceException {
        // 1. Arrange
        GameAnswer gameAnswer = new GameAnswer(new GameQuestion(round, new Vocab(new VocabList(category, "testList", "test", "test"), "test"), new Translation("test"), 1), new Translation("test"));
        when(gameDAO.getGameAnswer(1)).thenReturn(gameAnswer);
        when(manageUser.getById(1)).thenReturn(user);
        doThrow(GameDAOPersistenceException.class).when(gameDAO).saveRoundResult(any(RoundResult.class));
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, () -> manageGame.lockInAnswer(1, 1, false));
        // 3. Assert
        verify(gameDAO, times(1)).saveRoundResult(any(RoundResult.class));
    }

    @Test
    public void getScoreForUser() throws GameDoesNotExistException, UserNotFoundException, RoundResultDoesNotExistException {
        // 1. Arrange
        GameAnswer gameAnswer = new GameAnswer(new GameQuestion(round, new Vocab(new VocabList(category, "testList", "test", "test"), "test"), new Translation("test"), 1), new Translation("test"));
        List<RoundResult> roundResults = new ArrayList<>();
        roundResults.add(new RoundResult(gameAnswer, user, false));
        when(manageUser.getById(1)).thenReturn(user);
        when(gameDAO.getGame(1)).thenReturn(game);
        doReturn(roundResults).when(gameDAO).getCorrectRoundResults(game, user);
        // 2. Act
        int score = manageGame.getScoreForUser(1, game.getGameId());
        // 3. Assert
        assert (score == 1);
        assert (!roundResults.get(0).isCorrect());
    }

    @Test
    public void getLatestRoundForGame() throws GameDoesNotExistException {
        // 1. Arrange
        when(gameDAO.getGame(1)).thenReturn(game);
        doReturn(1).when(gameDAO).getNumberOfRounds(game.getGameId());
        // 2. Act
        int roundNumber = manageGame.getLatestRoundForGame(game.getGameId());
        // 3. Assert
        assert (roundNumber == 1);
        verify(gameDAO, times(1)).getNumberOfRounds(game.getGameId());
    }

    @Test
    public void getOngoingRoundForGame() throws RoundDoesNotExistException {
        // 1. Arrange
        when(gameDAO.getOngoingRound(1)).thenReturn(round);
        // 2. Act
        Round round = manageGame.getOngoingRoundForGame(1);
        // 3. Assert
        assert (round.getRoundNumber() == 1);
        assert (round.getGame().getGameId() == 1);
        verify(gameDAO, times(1)).getOngoingRound(1);
    }
}