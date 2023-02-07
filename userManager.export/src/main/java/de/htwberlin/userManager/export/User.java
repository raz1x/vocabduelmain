package de.htwberlin.userManager.export;

import jakarta.persistence.*;

@Entity(name = "User")
@Table(name = "Users")
public class User {
    /**
     * Id of the user.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private int userId;

    /**
     * Username of the user.
     */
    @Column(name = "username")
    private String username;

    /**
     * Password of the user.
     */
    @Column(name = "password")
    private String password;

    /**
     * Shows if the user is logged in.
     */
    @Column(name = "loggedIn")
    private boolean loggedIn;

    /**
     * Default constructor for User.
     */
    public User() {
    }

    /**
     * Constructor for User.
     * @param username Username of the user.
     * @param password Password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = true;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
