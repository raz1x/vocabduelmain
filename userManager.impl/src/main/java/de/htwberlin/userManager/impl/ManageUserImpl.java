package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserNotFoundException;

public class ManageUserImpl implements ManageUser {
    public User registerUser(String userName, String password) throws UserAlreadyExistsException {
        if(getByUserName(userName) != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        return new User(userName, password);
    }

    public User loginUser(String userName, String password) throws UserNotFoundException {
        // dummy implementation
        User user = getByUserName(userName);
        return new User(userName, password);
    }

    public void logoutUser(int userId) {
        // dummy implementation
        System.out.println("User " + userId + " logged out");
    }

    public void deleteUser(int userId) {
        // dummy implementation
        System.out.println("User " + userId + " deleted");
    }

    public void updateUserName(int userId, String userName) {
        // dummy implementation
        System.out.println("User " + userId + " changed username to " + userName);
    }

    public void updatePassword(int userId, String newPassword) {
        // dummy implementation
        System.out.println("User " + userId + " changed password");
    }

    public User getById(int userId) throws UserNotFoundException {
        // dummy implementation
        return new User("UserGetById", "test");
    }

    public User getByUserName(String userName) {
        // dummy implementation
        return new User(userName, "test");
    }
}
