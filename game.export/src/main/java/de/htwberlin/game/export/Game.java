package de.htwberlin.game.export;

import jakarta.persistence.*;

@Entity
@Table(name = "Game")
public class Game {
    /**
     * The ID of the game.
     */
    @Id @GeneratedValue
    @Column(name = "gameId")
    private int gameId;

    /**
     * The ID of the first user.
     */
    @Column(name = "user1Id")
    private int user1Id;

    /**
     * The ID of the second user.
     */
    @Column(name = "user2Id")
    private int user2Id;

    /**
     * The ID of the current user.
     */
    @Column(name = "currentUser")
    private int currentUser;

    /**
     * If the game is finished.
     */
    @Column(name = "isOngoing")
    private boolean isOngoing;

    /**
     * Default constructor for Game.
     */
    public Game() {

    }
    /**
     * The Constructor for Game.
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     */
    public Game(int user1Id, int user2Id) {
        this.gameId = 0;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.currentUser = user1Id;
        this.isOngoing = true;
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

    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

    public boolean getIsOngoing() {
        return isOngoing;
    }

    public void setIsOngoing(boolean isOngoing) {
        this.isOngoing = isOngoing;
    }
}
