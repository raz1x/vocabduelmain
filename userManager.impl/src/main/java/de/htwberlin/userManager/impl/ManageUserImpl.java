package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@Component
public class ManageUserImpl implements ManageUser {

    @Autowired
    @Qualifier(value = "entityManager")
    EntityManager em;

    @Override
    public User registerUser(String userName, String password) throws UserAlreadyExistsException {
        User user;
        if(!userExists(userName)) {
            user = new User(userName, password);
            System.out.println("User " + userName + " registered");
            em.persist(user);
            return user;
        } else {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    @Override
    public User loginUser(String userName, String password) throws UserNotFoundException {
        // dummy implementation
        if(userExists(userName)) {
            return new User(userName, password);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void logoutUser(int userId) throws UserNotFoundException {
        // dummy implementation
        User user = getById(userId);
        user.setLoggedIn(false);
        System.out.println("User " + userId + " logged out");
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        User user = getById(userId);
        // remove user from DB
        System.out.println("User " + userId + " deleted");
    }

    @Override
    public User updateUserName(int userId, String newUserName) throws UserNotFoundException, UserAlreadyExistsException {
        User user = getById(userId);
        if (userExists(newUserName)) {
            throw new UserAlreadyExistsException("User already exists");
        }
        user.setUserName(newUserName);
        System.out.println("User " + userId + " changed username to " + newUserName);
        return user;
    }

    @Override
    public User updatePassword(int userId, String newPassword) throws UserNotFoundException {
        User user = getById(userId);
        user.setPassword(newPassword);
        // set in DB
        System.out.println("User " + userId + " changed password");
        return user;
    }

    @Override
    public User getById(int userId) throws UserNotFoundException {
        try {
            // get user from DB
            return new User("MaxMuster", "test");
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public boolean userExists(String userName) {
        User user;
        try{
            em.find(User.class, userName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
