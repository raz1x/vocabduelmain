package de.htwberlin.userManager.rest_server;

import de.htwberlin.userManager.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manageUser")
public class ManageUserRestAdapter implements ManageUserRest {

    @Autowired
    private ManageUser manageUser;

    @Override
    public User registerUser(String userName, String password) throws UserAlreadyExistsException {
        return manageUser.registerUser(userName, password);
    }

    @Override
    public User loginUser(String userName, String password) throws UserNotFoundException, WrongPasswordException {
        return manageUser.loginUser(userName, password);
    }

    @Override
    public void logoutUser(int userId) throws UserNotFoundException {
        manageUser.logoutUser(userId);
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        manageUser.deleteUser(userId);
    }

    @Override
    public User updateUserName(int userId, String userName) throws UserNotFoundException, UserAlreadyExistsException {
        return manageUser.updateUserName(userId, userName);
    }

    @Override
    public User updatePassword(int userId, String newPassword) throws UserNotFoundException {
        return manageUser.updatePassword(userId, newPassword);
    }

    @Override
    public User getByName(String userName) throws UserNotFoundException {
        return manageUser.getByName(userName);
    }

    @Override
    public User getById(int userId) throws UserNotFoundException {
        return manageUser.getById(userId);
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        return manageUser.getAllUsers();
    }

    @Override
    public List<User> getOpponents(int currentUserId) throws UserNotFoundException {
        return manageUser.getOpponents(currentUserId);
    }

    @Override
    public boolean userExists(String userName) {
        return manageUser.userExists(userName);
    }
}