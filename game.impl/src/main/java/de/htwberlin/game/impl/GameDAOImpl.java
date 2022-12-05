package de.htwberlin.game.impl;

import de.htwberlin.game.export.*;
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
    public Game saveGame(Game game) {
        try {
            em.persist(game);
            return game;
        } catch (Exception e) {
            throw new PersistenceException("Could not save game");
        }
    }

    @Override
    public Game updateGame(Game game) {
        try {
            return em.merge(game);
        } catch (Exception e) {
            throw new PersistenceException("Could not update game");
        }
    }

    @Override
    public void deleteGame(Game game) {
        try {
            em.remove(game);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete game");
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
    public GameAnswer saveGameAnswer(GameAnswer gameAnswer) {
        try {
            em.persist(gameAnswer);
            return gameAnswer;
        } catch (Exception e) {
            throw new PersistenceException("Could not save game answer");
        }
    }

    @Override
    public GameAnswer updateGameAnswer(GameAnswer gameAnswer) {
        try {
            return em.merge(gameAnswer);
        } catch (Exception e) {
            throw new PersistenceException("Could not update game answer");
        }
    }

    @Override
    public void deleteGameAnswer(GameAnswer gameAnswer) {
        try {
            em.remove(gameAnswer);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete game answer");
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
    public GameQuestion saveGameQuestion(GameQuestion gameQuestion) {
        try {
            em.persist(gameQuestion);
            return gameQuestion;
        } catch (Exception e) {
            throw new PersistenceException("Could not save game question");
        }
    }

    @Override
    public GameQuestion updateGameQuestion(GameQuestion gameQuestion) {
        try {
            return em.merge(gameQuestion);
        } catch (Exception e) {
            throw new PersistenceException("Could not update game question");
        }
    }

    @Override
    public void deleteGameQuestion(GameQuestion gameQuestion) {
        try {
            em.remove(gameQuestion);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete game question");
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
                round = em.createQuery("SELECT r FROM Round r WHERE r.gameId = :gameId AND r.roundNumber = :roundNumber", Round.class)
                        .setParameter("gameId", game.getGameId())
                        .setParameter("roundNumber", roundNumber)
                        .getSingleResult();
            } catch (Exception e) {
                throw new RoundDoesNotExistException("Could not find round with number " + roundNumber + " for game with id " + gameId);
            }
            return em.createQuery("SELECT gq FROM GameQuestion gq WHERE gq.gameId = :gameId AND gq.round = :round", GameQuestion.class)
                    .setParameter("gameId", game.getGameId())
                    .setParameter("round", round.getRoundNumber())
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenceException("Could not get game questions for round " + roundNumber + " for game with id " + gameId);
        }
    }

    @Override
    public Round saveRound(Round round) {
        try {
            em.persist(round);
            return round;
        } catch (Exception e) {
            throw new PersistenceException("Could not save round");
        }
    }

    @Override
    public Round updateRound(Round round) {
        try {
            return em.merge(round);
        } catch (Exception e) {
            throw new PersistenceException("Could not update round");
        }
    }

    @Override
    public void deleteRound(Round round) {
        try {
            em.remove(round);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete round");
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
    public RoundResult saveRoundResult(RoundResult roundResult) {
        try {
            em.persist(roundResult);
            return roundResult;
        } catch (Exception e) {
            throw new PersistenceException("Could not save round result");
        }
    }

    @Override
    public RoundResult updateRoundResult(RoundResult roundResult) {
        try {
            return em.merge(roundResult);
        } catch (Exception e) {
            throw new PersistenceException("Could not update round result");
        }
    }

    @Override
    public void deleteRoundResult(RoundResult roundResult) {
        try {
            em.remove(roundResult);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete round result");
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
}
