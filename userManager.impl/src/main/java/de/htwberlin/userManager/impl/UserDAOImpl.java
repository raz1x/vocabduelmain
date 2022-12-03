package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAO;
import de.htwberlin.userManager.export.UserNotFoundException;
import de.htwberlin.userManager.export.WrongPasswordException;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        try {
            em.persist(user);
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not save user");
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            em.remove(user);
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not delete user");
        }
    }

    @Override
    public void updateUser(User user) throws PersistenceException {
        try {
            em.merge(user);
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not update user");
        }
    }

    @Override
    public User getUser(int userId) throws UserNotFoundException {
        try {
            return em.find(User.class, userId);
        } catch (Exception e) {
            throw new UserNotFoundException("Could not find user with id " + userId);
        }
    }

    @Override
    public User getUserByName(String username) throws UserNotFoundException {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username);
        if (query.getResultList().size() > 0) {
            return (User) query.getResultList().get(0);
        } else {
            throw new UserNotFoundException("Could not find user with username " + username);
        }
    }

    @Override
    public User checkUserNameAndPassword(String username, String password) throws WrongPasswordException, PersistenceException {
        User user;
        try {
            user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not find user with username " + username);
        }
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new WrongPasswordException("Wrong password");
        }

    }
}

