package de.htwberlin.userManager.export;

import java.util.List;

public interface UserDAO {
    /**
     * Saves a user in the database.
     * @param user The user to be saved.
     */
    public void saveUser(User user) throws UserDAOPersistenceException;

    /**
     * Deletes a user from the database.
     * @param user The user to be deleted.
     */
    public void deleteUser(User user) throws UserDAOPersistenceException;

    /**
     * Updates a user in the database.
     * @param user The user to be updated.
     */
    public void updateUser(User user) throws UserDAOPersistenceException;

    /**
     * Returns a user from the database.
     * @param userId The id of the user.
     * @return The user.
     */
    public User getUser(int userId) throws UserNotFoundException;

    /**
     * Returns a user from the database.
     * @param username The username of the user.
     * @return The user.
     */
    public User getUserByName(String username) throws UserNotFoundException;

    /**
     * Returns all users from the database.
     * @return A list of all users.
     */
    public List<User> getAllUsers() throws UserNotFoundException;

    /**
     * Returns all potential opponents from the database.
     * @return A list of all potential opponents.
     */
    public List<User> getOpponents(int currentUserId) throws UserNotFoundException;

    /**
     * Checks if the provided password matches the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user.
     */
    public boolean checkUserNameAndPassword(String username, String password) throws WrongPasswordException, UserNotFoundException;
}
