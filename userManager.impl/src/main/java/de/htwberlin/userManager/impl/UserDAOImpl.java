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
        } catch (OptimisticLockException e ) {
            throw e;
        } catch (PersistenceException e) {
            throw new UserDAOPersistenceException("Could not save user with id " + user.getUserId());
        }
    }

    @Override
    public void deleteUser(User user) throws UserDAOPersistenceException {
        try {
            em.remove(user);
        } catch (OptimisticLockException e ) {
            throw e;
        } catch (PersistenceException e) {
            throw new UserDAOPersistenceException("Could not delete user with id " + user.getUserId());
        }
    }

    @Override
    public void updateUser(User user) throws PersistenceException, UserDAOPersistenceException {
        try {
            em.merge(user);
        } catch (OptimisticLockException e ) {
            throw e;
        } catch (PersistenceException e) {
            throw new UserDAOPersistenceException("Could not update user with id " + user.getUserId());
        }
    }

    @Override
    public User getUser(int userId) throws UserNotFoundException, UserDAOPersistenceException {
        try {
            User user = em.find(User.class, userId, LockModeType.OPTIMISTIC);
            if (user == null) {
                throw new UserNotFoundException("Could not find user with id " + userId);
            }
            return user;

        } catch (OptimisticLockException e ) {
            throw e;
        } catch (PersistenceException e) {
            throw new UserDAOPersistenceException("Persistence Exception while getting User with id " + userId);
        }
    }

    @Override
    public User getUserByName(String username) throws UserNotFoundException {
        TypedQuery<User> query = em.createNamedQuery("User.getUserByName", User.class)
                .setParameter("username", username)
                .setLockMode(LockModeType.OPTIMISTIC);
        if (query.getResultList().size() > 0) {
            return (User) query.getResultList().get(0);
        } else {
            throw new UserNotFoundException("Could not find user with username " + username);
        }
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        TypedQuery<User> query = em.createNamedQuery("User.getAllUsers", User.class).setLockMode(LockModeType.OPTIMISTIC);
        if (query.getResultList().size() > 0) {
            return query.getResultList();
        } else {
            throw new UserNotFoundException("Could not find any users!");
        }
    }

    @Override
    public List<User> getOpponents(int currentUserId) throws UserNotFoundException {
        TypedQuery<User> query = em.createNamedQuery("User.getOtherUsers", User.class)
                .setParameter("userId", currentUserId)
                .setLockMode(LockModeType.OPTIMISTIC);
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
            user = em.createNamedQuery("User.getUserByName", User.class)
                    .setParameter("username", username)
                    .setLockMode(LockModeType.OPTIMISTIC)
                    .getSingleResult();
        } catch (OptimisticLockException e ) {
            throw e;
        } catch (Exception e) {
            throw new UserNotFoundException("Could not find user with username " + username);
        }
        return user.getPassword().equals(password);
    }


}

