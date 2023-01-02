package de.htwberlin.userManager.export;

import java.util.List;

public interface ManageUser {
    /**
     * Registers a new user
     * @param userName The username of the user
     * @param password The password of the user
     * @return The user object
     */
    public User registerUser(String userName, String password) throws UserAlreadyExistsException;

    /**
     * Logs in a user
     * @param userName The username of the user
     * @param password The password of the user
     * @return The user object
     */
    public User loginUser(String userName, String password) throws UserNotFoundException, WrongPasswordException, UserDAOPersistenceException;

    /** Logs out a user
     * @param userId The id of the user
     */
    public void logoutUser(int userId) throws UserNotFoundException, UserDAOPersistenceException;

    /**
     * Deletes a user
     * @param userId The id of the user
     */
    public void deleteUser(int userId) throws UserNotFoundException, UserDAOPersistenceException;

    /**
     * Updates the username of a user
     * @param userId The id of the user
     * @param userName The new username
     * @return The user object
     */
    public User updateUserName(int userId, String userName) throws UserNotFoundException, UserAlreadyExistsException, UserDAOPersistenceException;

    /**
     * Updates the password of a user
     * @param userId The id of the user
     * @param newPassword  The new password
     * @return The user object
     */
    public User updatePassword(int userId, String newPassword) throws UserNotFoundException, UserDAOPersistenceException;

    /**
     * Gets a user by its username
     * @param userName The username of the user
     * @return The user object
     */
    public User getByName(String userName) throws UserNotFoundException;

    /**
     * Gets a user by its id
     * @param userId The id of the user
     * @return The user object
     */
    public User getById(int userId) throws UserNotFoundException, UserDAOPersistenceException;

    /**
     * Gets all users from the database
     * @return A list of all users.
     */
    public List<User> getAllUsers() throws UserNotFoundException;

    /**
     * Gets all potential opponents for a user
     * @param currentUserId The id of the current user
     * @return A list of all opponents.
     */
    public List<User> getOpponents(int currentUserId) throws UserNotFoundException;

    /**
     * Check if userName already exists
     * @param userName The username of the user
     * @return true if userName already exists
     */
    public boolean userExists(String userName);
}

