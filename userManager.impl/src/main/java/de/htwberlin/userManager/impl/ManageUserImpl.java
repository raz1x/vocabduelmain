package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
public class ManageUserImpl implements ManageUser {

    @Autowired
    UserDAO userDAO;

    @Override
    public User registerUser(String userName, String password) throws UserAlreadyExistsException {
        User user;
        if(!userExists(userName)) {
            user = new User(userName, password);
            System.out.println("User " + userName + " registered!");
            try {
                userDAO.saveUser(user);
            } catch (UserDAOPersistenceException e) {
                throw new UserAlreadyExistsException("Could not register user " + userName);
            }
            return user;
        } else {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    @Override
    public User loginUser(String userName, String password) throws UserNotFoundException, WrongPasswordException, UserDAOPersistenceException {
        try {
            if (userDAO.checkUserNameAndPassword(userName, password)) {
                User user = userDAO.getUserByName(userName);
                user.setLoggedIn(true);
                userDAO.updateUser(user);
                return user;
            } else {
                throw new WrongPasswordException("Wrong password!");
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found!");
        } catch (UserDAOPersistenceException e) {
            throw new UserDAOPersistenceException("Could not login user " + userName);
        }
    }

    @Override
    public void logoutUser(int userId) throws UserNotFoundException, UserDAOPersistenceException {
        User user;
        try {
            user = userDAO.getUser(userId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found!");
        }
        user.setLoggedIn(false);
        try {
            userDAO.updateUser(user);
        } catch (UserDAOPersistenceException e) {
            throw new UserDAOPersistenceException("User could not be logged out!");
        }
        System.out.println("User " + userId + " logged out.");
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException, UserDAOPersistenceException {
        User user;
        try {
            user = userDAO.getUser(userId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found!");
        }
        try {
            userDAO.deleteUser(user);
        } catch (UserDAOPersistenceException e) {
            throw new UserDAOPersistenceException("User could not be deleted!");
        }
        System.out.println("User " + userId + " deleted.");
    }

    @Override
    public User updateUserName(int userId, String newUserName) throws UserNotFoundException, UserAlreadyExistsException, UserDAOPersistenceException {
        User user;
        try {
            user = userDAO.getUser(userId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found!");
        }
        if (userExists(newUserName)) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        user.setUserName(newUserName);
        try {
            userDAO.updateUser(user);
        } catch (UserDAOPersistenceException e) {
            throw new UserDAOPersistenceException("User could not be updated!");
        }
        System.out.println("User " + userId + " changed username to " + newUserName);
        return user;
    }

    @Override
    public User updatePassword(int userId, String newPassword) throws UserNotFoundException, UserDAOPersistenceException {
        User user;
        try {
            user = userDAO.getUser(userId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found!");
        }
        user.setPassword(newPassword);
        try {
            userDAO.updateUser(user);
        } catch (UserDAOPersistenceException e) {
            throw new UserDAOPersistenceException("User could not be updated!");
        }
        System.out.println("Successfully changed password of user " + userId);
        return user;
    }

    @Override
    public User getByName(String userName) throws UserNotFoundException {
        try {
            return userDAO.getUserByName(userName);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User getById(int userId) throws UserNotFoundException {
        try {
            return userDAO.getUser(userId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        try {
            return userDAO.getAllUsers();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("No users found!");
        }
    }

    @Override
    public List<User> getOpponents(int currentUserId) throws UserNotFoundException {
        try {
            return userDAO.getOpponents(currentUserId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("No opponents found!");
        }
    }

    @Override
    public boolean userExists(String userName) {
        try {
            userDAO.getUserByName(userName);
            return true;
        } catch(UserNotFoundException e) {
            return false;
        }



    }
}
