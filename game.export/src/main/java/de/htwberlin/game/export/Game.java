package de.htwberlin.game.export;

public class Game {
    /**
     * The ID of the game.
     */
    private int gameId;

    /**
     * The ID of the first user.
     */
    private int user1Id;

    /**
     * The ID of the second user.
     */
    private int user2Id;

    /**
     * The Constructor for Game.
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     */
    public Game(int user1Id, int user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }
}
