package de.htwberlin.game.impl;

import de.htwberlin.game.export.*;
import de.htwberlin.userManager.export.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

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
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save game with id " + game.getGameId());
        }
    }

    @Override
    public Game updateGame(Game game) throws GameDAOPersistenceException {
        try {
            return em.merge(game);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not update game with id " + game.getGameId());
        }
    }

    @Override
    public void deleteGame(Game game) throws GameDAOPersistenceException {
        try {
            em.remove(game);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not delete game with id " + game.getGameId());
        }
    }

    @Override
    public Game getGame(int gameId) throws GameDoesNotExistException {
        try {
            return em.find(Game.class, gameId);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Could not find game with id " + gameId);
        }
    }

    @Override
    public List<Game> getOngoingGamesForUser(User user) throws GameDoesNotExistException {
        try {
            return em.createQuery("SELECT g FROM Game g WHERE g.currentUser = :userId AND g.isOngoing = :isOngoing", Game.class)
                    .setParameter("userId", user.getUserId())
                    .setParameter("isOngoing", true)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new GameDoesNotExistException("Could not find games for user " + user.getUserName());
        }
    }

    @Override
    public GameAnswer saveGameAnswer(GameAnswer gameAnswer) throws GameDAOPersistenceException {
        try {
            em.persist(gameAnswer);
            return gameAnswer;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save game answer with id " + gameAnswer.getGameAnswerId());
        }
    }

    @Override
    public GameAnswer updateGameAnswer(GameAnswer gameAnswer) throws GameDAOPersistenceException {
        try {
            return em.merge(gameAnswer);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not update game answer with id " + gameAnswer.getGameAnswerId());
        }
    }

    @Override
    public void deleteGameAnswer(GameAnswer gameAnswer) throws GameDAOPersistenceException {
        try {
            em.remove(gameAnswer);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not delete game answer with id " + gameAnswer.getGameAnswerId());
        }
    }

    @Override
    public GameAnswer getGameAnswer(int gameAnswerId) throws GameAnswerDoesNotExistException {
        try {
            return em.find(GameAnswer.class, gameAnswerId);
        } catch (Exception e) {
            throw new GameAnswerDoesNotExistException("Could not find game answer with id " + gameAnswerId);
        }
    }

    @Override
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException {
        try {
            return em.createQuery("SELECT ga FROM GameAnswer ga WHERE ga.gameQuestion.gameQuestionId = :gameQuestionId", GameAnswer.class)
                    .setParameter("gameQuestionId", gameQuestionId)
                    .getResultList();
        } catch (Exception e) {
            throw new GameAnswerDoesNotExistException("Could not find game answers for game question " + gameQuestionId);
        }
    }

    @Override
    public GameQuestion saveGameQuestion(GameQuestion gameQuestion) throws GameDAOPersistenceException {
        try {
            em.persist(gameQuestion);
            return gameQuestion;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save game question with id " + gameQuestion.getGameQuestionId());
        }
    }

    @Override
    public GameQuestion updateGameQuestion(GameQuestion gameQuestion) throws GameDAOPersistenceException {
        try {
            return em.merge(gameQuestion);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not update game question with id " + gameQuestion.getGameQuestionId());
        }
    }

    @Override
    public void deleteGameQuestion(GameQuestion gameQuestion) throws GameDAOPersistenceException {
        try {
            em.remove(gameQuestion);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not delete game question with id " + gameQuestion.getGameQuestionId());
        }
    }

    @Override
    public GameQuestion getGameQuestion(int gameQuestionId) throws GameQuestionDoesNotExistException {
        try {
            return em.find(GameQuestion.class, gameQuestionId);
        } catch (Exception e) {
            throw new GameQuestionDoesNotExistException("Could not find game question with id " + gameQuestionId);
        }
    }

    @Override
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundNumber) throws GameDoesNotExistException, RoundDoesNotExistException {
        try {
            Game game = em.find(Game.class, gameId);
            if (game == null) {
                throw new GameDoesNotExistException("Could not find game with id " + gameId);
            }
            Round round;
            try {
                round = em.createQuery("SELECT r FROM Round r WHERE r.game = :game AND r.roundNumber = :roundNumber", Round.class)
                        .setParameter("game", game)
                        .setParameter("roundNumber", roundNumber)
                        .getSingleResult();
            } catch (Exception e) {
                throw new RoundDoesNotExistException("Could not find round with number " + roundNumber + " for game with id " + gameId);
            }
            return em.createQuery("SELECT gq FROM GameQuestion gq WHERE gq.round = :round", GameQuestion.class)
                    .setParameter("round", round)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenceException("Could not get game questions for round " + roundNumber + " for game with id " + gameId);
        }
    }

    @Override
    public Round saveRound(Round round) throws GameDAOPersistenceException {
        try {
            em.persist(round);
            return round;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save round with id " + round.getRoundId());
        }
    }

    @Override
    public Round updateRound(Round round) throws GameDAOPersistenceException {
        try {
            return em.merge(round);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not update round with id " + round.getRoundId());
        }
    }

    @Override
    public void deleteRound(Round round) throws GameDAOPersistenceException {
        try {
            em.remove(round);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not delete round with id " + round.getRoundId());
        }
    }

    @Override
    public Round getRound(int gameId, int roundNumber) throws RoundDoesNotExistException {
        try {
            Game game = em.find(Game.class, gameId);
            return em.createQuery("SELECT r FROM Round r WHERE r.game = :game AND r.roundNumber = :roundNumber", Round.class)
                    .setParameter("gameId", game)
                    .setParameter("roundNumber", roundNumber)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Could not find round number " + roundNumber + " for game with id " + gameId);
        }
    }

    @Override
    public Round getRoundById(int roundId) throws RoundDoesNotExistException {
        try {
            return em.find(Round.class, roundId);
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Could not find round with id " + roundId);
        }
    }

    @Override
    public int getNumberOfRounds(int gameId) throws GameDoesNotExistException {
        try {
            Game game = em.find(Game.class, gameId);
            return em.createQuery("SELECT COUNT(r) FROM Round r WHERE r.game = :game", Long.class)
                    .setParameter("game", game)
                    .getSingleResult().intValue();
        } catch (Exception e) {
            throw new GameDoesNotExistException("Could not find game with id " + gameId);
        }
    }

    @Override
    public Round getOngoingRound(int gameId) throws RoundDoesNotExistException {
        try {
            Game game = em.find(Game.class, gameId);
            return em.createQuery("SELECT r FROM Round r WHERE r.game = :game AND r.isOnGoing = true", Round.class)
                    .setParameter("game", game)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Could not find ongoing round for game with id " + gameId);
        }
    }

    @Override
    public RoundResult saveRoundResult(RoundResult roundResult) throws GameDAOPersistenceException {
        try {
            em.persist(roundResult);
            return roundResult;
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not save round result with id " + roundResult.getRoundResultId());
        }
    }

    @Override
    public RoundResult updateRoundResult(RoundResult roundResult) throws GameDAOPersistenceException {
        try {
            return em.merge(roundResult);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not update round result with id " + roundResult.getRoundResultId());
        }
    }

    @Override
    public void deleteRoundResult(RoundResult roundResult) throws GameDAOPersistenceException {
        try {
            em.remove(roundResult);
        } catch (Exception e) {
            throw new GameDAOPersistenceException("Could not delete round result with id " + roundResult.getRoundResultId());
        }
    }

    @Override
    public RoundResult getRoundResult(int roundResultId) throws RoundResultDoesNotExistException {
        try {
            return em.find(RoundResult.class, roundResultId);
        } catch (Exception e) {
            throw new RoundResultDoesNotExistException("Could not find round result with id " + roundResultId);
        }
    }

    @Override
    public List<RoundResult> getCorrectRoundResults(Game game, User user) throws RoundResultDoesNotExistException {
        try {
            return em.createQuery("SELECT rr FROM RoundResult rr JOIN rr.chosenAnswer ga " +
                            "JOIN ga.gameQuestion gq JOIN gq.game g WHERE g.gameId = :gameId AND rr.user = :user AND rr.isCorrect = true", RoundResult.class)
                    .setParameter("gameId", game.getGameId())
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RoundResultDoesNotExistException("Could not find round results for game with id " + game.getGameId() + " and user with id " + user.getUserId());
        }
    }
}
