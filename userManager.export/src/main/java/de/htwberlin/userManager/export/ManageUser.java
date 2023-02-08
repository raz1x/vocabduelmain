package de.htwberlin.userManager.export;

import java.util.List;

public interface ManageUser {
    /**
     * Registers a new user
     * @param userName The username of the user
     * @param password The password of the user
     * @throws UserAlreadyExistsException If the user already exists.
     * @return The user object
     */
    User registerUser(String userName, String password) throws UserAlreadyExistsException;

    /**
     * Logs in a user
     * @param userName The username of the user
     * @param password The password of the user
     * @throws UserNotFoundException If the user is not found.
     * @return The user object
     */
    User loginUser(String userName, String password) throws UserNotFoundException, WrongPasswordException;

    /** Logs out a user
     * @param userId The id of the user
     * @throws UserNotFoundException If the user is not found.
     */
    void logoutUser(int userId) throws UserNotFoundException;

    /**
     * Deletes a user
     * @param userId The id of the user
     * @throws UserNotFoundException If the user is not found.
     */
    void deleteUser(int userId) throws UserNotFoundException;

    /**
     * Updates the username of a user
     * @param userId The id of the user
     * @param userName The new username
     * @throws UserNotFoundException If the user is not found.
     * @throws UserAlreadyExistsException If the user already exists.
     * @return The user object
     */
    User updateUserName(int userId, String userName) throws UserNotFoundException, UserAlreadyExistsException;

    /**
     * Updates the password of a user
     * @param userId The id of the user
     * @param newPassword  The new password
     * @throws UserNotFoundException If the user is not found.
     * @return The user object
     */
    User updatePassword(int userId, String newPassword) throws UserNotFoundException;

    /**
     * Gets a user by its username
     * @param userName The username of the user
     * @throws UserNotFoundException If the user is not found.
     * @return The user object
     */
    User getByName(String userName) throws UserNotFoundException;

    /**
     * Gets a user by its id
     * @param userId The id of the user
     * @throws UserNotFoundException If the user is not found.
     * @return The user object
     */
    User getById(int userId) throws UserNotFoundException;

    /**
     * Gets all users from the database
     * @throws UserNotFoundException If the user is not found.
     * @return A list of all users.
     */
    List<User> getAllUsers() throws UserNotFoundException;

    /**
     * Gets all potential opponents for a user
     * @param currentUserId The id of the current user
     * @throws UserNotFoundException If the user is not found.
     * @return A list of all opponents.
     */
    List<User> getOpponents(int currentUserId) throws UserNotFoundException;

    /**
     * Checks if a user with given username already exists.
     * @param userName The name of the user
     * @return If the user exists.
     */
    boolean userExists(String userName);
}

