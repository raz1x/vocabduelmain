package de.htwberlin.userManager.export;

public interface UserDAO {
    /**
     * Saves a user in the database.
     * @param user The user to be saved.
     */
    public void saveUser(User user);

    /**
     * Deletes a user from the database.
     * @param user The user to be deleted.
     */
    public void deleteUser(User user);

    /**
     * Updates a user in the database.
     * @param user The user to be updated.
     */
    public void updateUser(User user);

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
     * Returns a user from the database.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user.
     */
    public User checkUserNameAndPassword(String username, String password) throws WrongPasswordException;
}
