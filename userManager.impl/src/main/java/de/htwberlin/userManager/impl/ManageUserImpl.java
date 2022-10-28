package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;

public class ManageUserImpl implements ManageUser {
    /**
     * Registers a new user
     * @param userName The username of the user
     * @param password The password of the user
     * @return The user object
     */
    public User registerUser(String userName, String password) {
        return new User(userName, password);
    }

    /**
     * Logs in a user
     * @param userName The username of the user
     * @param password The password of the user
     * @return The user object
     */
    public User loginUser(String userName, String password) {
        // dummy implementation
        return new User(userName, password);
    }

    /** Logs out a user
     * @param userId The id of the user
     */
    public void logoutUser(int userId) {
        // dummy implementation
        System.out.println("User " + userId + " logged out");
    }

    /**
     * Deletes a user
     * @param userId The id of the user
     */
    public void deleteUser(int userId) {
        // dummy implementation
        System.out.println("User " + userId + " deleted");
    }

    /**
     * Updates the username of a user
     * @param userId The id of the user
     * @param userName The new username
     */
    public void updateUserName(int userId, String userName) {
        // dummy implementation
        System.out.println("User " + userId + " changed username to " + userName);
    }

    /**
     * Updates the password of a user
     * @param userId The id of the user
     * @param newPassword  The new password
     */
    public void updatePassword(int userId, String newPassword) {
        // dummy implementation
        System.out.println("User " + userId + " changed password");
    }

    /**
     * Gets a user by id
     * @param userId The id of the user
     * @return The user object
     */
    public User getById(int userId) {
        // dummy implementation
        return new User("UserGetById", "test");
    }

    /**
     * Gets a user by username
     * @param userName The username of the user
     * @return The user object
     */
    public User getByUserName(String userName) {
        // dummy implementation
        return new User(userName, "test");
    }
}
