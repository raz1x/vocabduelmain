package de.htwberlin.game.export;

public interface ManageGame {
    public Game createGame(int user1Id, int user2Id);

    public Game continueGame(int gameId);

    public Game endGame(int gameId);

    public Round createRound(int gameId, int round, int categoryId);
}
