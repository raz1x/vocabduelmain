package de.htwberlin.userManager.export;

import jakarta.persistence.*;

@Entity(name = "User")
@Table(name = "Users")
@NamedQuery(name = "User.getUserByName", query = "SELECT u FROM User u WHERE u.username = :username")
@NamedQuery(name = "User.getAllUsers", query = "SELECT u FROM User u")
@NamedQuery(name = "User.getOtherUsers", query = "SELECT u FROM User u WHERE u.userId != :userId")
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

    @Version
    @Column(name = "version")
    private int version;

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
