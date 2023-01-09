package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.*;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) throws UserDAOPersistenceException {
        try {
            em.persist(user);
        } catch (PersistenceException e) {
            throw new UserDAOPersistenceException("Could not save user with id " + user.getUserId());
        }
    }

    @Override
    public void deleteUser(User user) throws UserDAOPersistenceException {
        try {
            em.remove(user);
        } catch (PersistenceException e) {
            throw new UserDAOPersistenceException("Could not delete user with id " + user.getUserId());
        }
    }

    @Override
    public void updateUser(User user) throws PersistenceException, UserDAOPersistenceException {
        try {
            em.merge(user);
        } catch (PersistenceException e) {
            throw new UserDAOPersistenceException("Could not update user with id " + user.getUserId());
        }
    }

    @Override
    public User getUser(int userId) throws UserNotFoundException, UserDAOPersistenceException {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                throw new UserNotFoundException("Could not find user with id " + userId);
            }
            return user;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Could not find user with id " + userId);
        } catch (PersistenceException e) {
            throw new UserDAOPersistenceException("Persistence Exception in getUser");
        }
    }

    @Override
    public User getUserByName(String username) throws UserNotFoundException {
        // TODO: Replace with NamedQuery
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username);
        if (query.getResultList().size() > 0) {
            return (User) query.getResultList().get(0);
        } else {
            throw new UserNotFoundException("Could not find user with username " + username);
        }
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        // TODO: Replace with NamedQuery
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        if (query.getResultList().size() > 0) {
            return query.getResultList();
        } else {
            throw new UserNotFoundException("Could not find any users!");
        }
    }

    @Override
    public List<User> getOpponents(int currentUserId) throws UserNotFoundException {
        // TODO: Replace with NamedQuery
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userId != :userId", User.class)
                .setParameter("userId", currentUserId);
        if (query.getResultList().size() > 0) {
            return query.getResultList();
        } else {
            throw new UserNotFoundException("Could not find any users!");
        }
    }

    @Override
    public boolean checkUserNameAndPassword(String username, String password) throws UserNotFoundException {
        User user;
        try {
            // TODO: Replace with NamedQuery
            user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new UserNotFoundException("Could not find user with username " + username);
        }
        return user.getPassword().equals(password);
    }


}

