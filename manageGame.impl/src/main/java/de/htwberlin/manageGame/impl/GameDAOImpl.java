package de.htwberlin.manageGame.impl;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.userManager.export.User;
import jakarta.persistence.*;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class GameDAOImpl implements GameDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Game saveGame(Game game) throws GameDAOPersistenceException {
        try {
            em.persist(game);
            return game;
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save game with id " + game.getGameId());
        }
    }

    @Override
    public Game updateGame(Game game) throws GameDAOPersistenceException {
        try {
            return em.merge(game);
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not update game with id " + game.getGameId());
        }
    }

    @Override
    public Game getGame(int gameId) throws GameDoesNotExistException {
        try {
            return em.find(Game.class, gameId, LockModeType.OPTIMISTIC);
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception ex) {
            throw new GameDoesNotExistException("Could not find game with id " + gameId);
        }
    }

    @Override
    public List<Game> getOngoingGamesForUser(User user) throws GameDoesNotExistException {
        TypedQuery<Game> query = em.createNamedQuery("Game.getOngoingGamesForUser", Game.class)
                .setParameter("userId", user.getUserId())
                .setParameter("isOngoing", true)
                .setLockMode(LockModeType.OPTIMISTIC);
        if (query.getResultList().size() == 0) {
            throw new GameDoesNotExistException("Could not find any ongoing games for user with id " + user.getUserId());
        }
        return query.getResultList();
    }

    @Override
    public GameAnswer saveGameAnswer(GameAnswer gameAnswer) throws GameDAOPersistenceException {
        try {
            em.persist(gameAnswer);
            return gameAnswer;
        } catch (OptimisticLockException e) {
                throw e;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save game answer with id " + gameAnswer.getGameAnswerId());
        }
    }


    @Override
    public GameAnswer getGameAnswer(int gameAnswerId) throws GameAnswerDoesNotExistException {
        GameAnswer gameAnswer;
        gameAnswer = em.find(GameAnswer.class, gameAnswerId, LockModeType.OPTIMISTIC);
        if (gameAnswer == null) {
            throw new GameAnswerDoesNotExistException("Could not find game answer with id " + gameAnswerId);
        }
        return gameAnswer;
    }

    @Override
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException {
        List<GameAnswer> gameAnswers;
        TypedQuery<GameAnswer> query = em.createNamedQuery("GameAnswer.getByGameQuestionId", GameAnswer.class)
                .setParameter("gameQuestionId", gameQuestionId)
                .setLockMode(LockModeType.OPTIMISTIC);
        if (query.getResultList().size() == 0) {
            throw new GameAnswerDoesNotExistException("Could not find any game answers for game question with id " + gameQuestionId);
        }
        return query.getResultList();
    }

    @Override
    public GameQuestion saveGameQuestion(GameQuestion gameQuestion) throws GameDAOPersistenceException {
        try {
            em.persist(gameQuestion);
            return gameQuestion;
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save game question with id " + gameQuestion.getGameQuestionId());
        }
    }

    @Override
    public GameQuestion getGameQuestion(int gameQuestionId) throws GameQuestionDoesNotExistException {
        GameQuestion gameQuestion;
        gameQuestion = em.find(GameQuestion.class, gameQuestionId, LockModeType.OPTIMISTIC);
        if (gameQuestion == null) {
            throw new GameQuestionDoesNotExistException("Could not find game question with id " + gameQuestionId);
        }
        return gameQuestion;
    }

    @Override
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundNumber) throws GameDoesNotExistException, RoundDoesNotExistException, GameQuestionDoesNotExistException {
        Game game = em.find(Game.class, gameId);
        if (game == null) {
            throw new GameDoesNotExistException("Could not find game with id " + gameId);
        }
        Round round;
        try {
            round = em.createNamedQuery("Round.getRoundByGameAndRoundNumber", Round.class)
                    .setParameter("game", game)
                    .setParameter("roundNumber", roundNumber)
                    .setLockMode(LockModeType.OPTIMISTIC)
                    .getSingleResult();
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Could not find round with number " + roundNumber + " for game with id " + gameId);
        }
        TypedQuery<GameQuestion> gameQuestionQuery = em.createNamedQuery("GameQuestion.getByRound", GameQuestion.class)
                .setParameter("round", round)
                .setLockMode(LockModeType.OPTIMISTIC);
        if (gameQuestionQuery.getResultList().size() == 0) {
            throw new GameQuestionDoesNotExistException("Could not find any game questions for round with number " + roundNumber + " for game with id " + gameId);
        }
        return gameQuestionQuery.getResultList();
    }

    @Override
    public Round saveRound(Round round) throws GameDAOPersistenceException {
        try {
            em.persist(round);
            return round;
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save round with id " + round.getRoundId());
        }
    }

    @Override
    public Round updateRound(Round round) throws GameDAOPersistenceException {
        try {
            return em.merge(round);
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not update round with id " + round.getRoundId());
        }
    }

    @Override
    public Round getRound(int gameId, int roundNumber) throws RoundDoesNotExistException {
        try {
            Game game = em.find(Game.class, gameId);
            return em.createNamedQuery("Round.getRoundByGameAndRoundNumber", Round.class)
                    .setParameter("gameId", game)
                    .setParameter("roundNumber", roundNumber)
                    .setLockMode(LockModeType.OPTIMISTIC)
                    .getSingleResult();
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Could not find round number " + roundNumber + " for game with id " + gameId);
        }
    }

    @Override
    public Round getRoundById(int roundId) throws RoundDoesNotExistException {
        try {
            return em.find(Round.class, roundId, LockModeType.OPTIMISTIC);
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Could not find round with id " + roundId);
        }
    }

    @Override
    public int getNumberOfRounds(int gameId) throws GameDoesNotExistException {
        try {
            Game game = em.find(Game.class, gameId);
            return em.createNamedQuery("Round.getNumberOfRounds", Long.class)
                    .setParameter("game", game)
                    .setLockMode(LockModeType.OPTIMISTIC)
                    .getSingleResult().intValue();
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new GameDoesNotExistException("Could not find game with id " + gameId);
        }
    }

    @Override
    public Round getOngoingRound(int gameId) throws RoundDoesNotExistException {
        try {
            Game game = em.find(Game.class, gameId);
            return em.createNamedQuery("Round.getOngoingRound", Round.class)
                    .setParameter("game", game)
                    .setLockMode(LockModeType.OPTIMISTIC)
                    .getSingleResult();
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Could not find ongoing round for game with id " + gameId);
        }
    }

    @Override
    public RoundResult saveRoundResult(RoundResult roundResult) throws GameDAOPersistenceException {
        try {
            em.persist(roundResult);
            return roundResult;
        } catch (OptimisticLockException e) {
            throw e;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save round result with id " + roundResult.getRoundResultId());
        }
    }

    @Override
    public List<RoundResult> getCorrectRoundResults(Game game, User user) throws RoundResultDoesNotExistException {
         TypedQuery<RoundResult> roundResults = em.createNamedQuery("RoundResult.selectCorrectRoundResults", RoundResult.class)
                .setParameter("gameId", game.getGameId())
                .setParameter("user", user)
                 .setLockMode(LockModeType.OPTIMISTIC);
         if (roundResults.getResultList().size() == 0) {
             throw new RoundResultDoesNotExistException("Could not find any correct round results for game with id " + game.getGameId() + " and user with id " + user.getUserId());
         }
         return roundResults.getResultList();
    }
}
