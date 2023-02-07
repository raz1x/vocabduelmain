package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.*;

import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {OptimisticLockException.class, RuntimeException.class})
public class ManageUserImpl implements ManageUser {

    @Autowired
    UserDAO userDAO;

    @Override
    public User registerUser(String userName, String password) throws UserAlreadyExistsException {
        User user;
        try {
            user = userDAO.getUserByName(userName);
        } catch (UserNotFoundException e) {
            user = new User(userName, password);
            try {
                userDAO.saveUser(user);
            } catch (UserDAOPersistenceException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return user;
    }

    @Override
    public User loginUser(String userName, String password) throws UserNotFoundException, WrongPasswordException {
        try {
            if (userDAO.checkUserNameAndPassword(userName, password)) {
                User user = userDAO.getUserByName(userName);
                user.setLoggedIn(true);
                userDAO.updateUser(user);
                return user;
            } else {
                throw new WrongPasswordException("Wrong password!");
            }
        } catch (UserDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void logoutUser(int userId) throws UserNotFoundException {
        User user;
        try {
            user = userDAO.getUser(userId);
            user.setLoggedIn(false);
            userDAO.updateUser(user);
        } catch (UserDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        User user = null;
        try {
            user = userDAO.getUser(userId);
            userDAO.deleteUser(user);
        } catch (UserDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User updateUserName(int userId, String newUserName) throws UserNotFoundException {
        User user = null;
        try {
            user = userDAO.getUser(userId);
        } catch (UserDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            userDAO.getUserByName(newUserName);
        } catch (UserNotFoundException e){
            user.setUsername(newUserName);
            try {
                userDAO.updateUser(user);
            } catch (UserDAOPersistenceException ex) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return user;
    }

    @Override
    public User updatePassword(int userId, String newPassword) throws UserNotFoundException {
        User user = null;
        try {
            user = userDAO.getUser(userId);
            user.setPassword(newPassword);
            userDAO.updateUser(user);
        } catch (UserDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }

    @Override
    public User getByName(String userName) throws UserNotFoundException {
        return userDAO.getUserByName(userName);
    }

    @Override
    public User getById(int userId) throws UserNotFoundException {
        try {
            return userDAO.getUser(userId);
        } catch (UserDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        return userDAO.getAllUsers();
    }

    @Override
    public List<User> getOpponents(int currentUserId) throws UserNotFoundException {
        return userDAO.getOpponents(currentUserId);
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
