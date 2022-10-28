package de.htwberlin.userManager.export;

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
    public User loginUser(String userName, String password) throws UserNotFoundException;

    /** Logs out a user
     * @param userId The id of the user
     */
    public void logoutUser(int userId) throws UserNotFoundException;

    /**
     * Deletes a user
     * @param userId The id of the user
     */
    public void deleteUser(int userId) throws UserNotFoundException;

    /**
     * Updates the username of a user
     * @param userId The id of the user
     * @param userName The new username
     * @return The user object
     */
    public User updateUserName(int userId, String userName) throws UserNotFoundException, UserAlreadyExistsException;

    /**
     * Updates the password of a user
     * @param userId The id of the user
     * @param newPassword  The new password
     * @return The user object
     */
    public User updatePassword(int userId, String newPassword) throws UserNotFoundException;

    /**
     * Gets a user by its id
     * @param userId The id of the user
     * @return The user object
     */
    public User getById(int userId) throws UserNotFoundException;

    /**
     * Check if userName already exists
     * @param userName The username of the user
     * @return true if userName already exists
     */
    public boolean userExists(String userName);
}

