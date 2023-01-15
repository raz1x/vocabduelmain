package de.htwberlin.userManager.restserver;

import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.export.UserNotFoundException;
import de.htwberlin.userManager.impl.UserDAO;
import de.htwberlin.userManager.impl.UserDAOImpl;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class ManageUserRestAdapter extends ServerResource implements ManageUserService {

    // ohne DI
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        // für DEMO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vocabDuelPU");
        userDAO.setEntityManager(emf.createEntityManager());
    }
    /*
    // with url parameter
    @Override
    public User getUser() throws UserNotFoundException, UserDAOPersistenceException {
        int id;
        try {
            id = getAttribute("id");
        } catch (ResourceException e) {
            throw new UserNotFoundException("User not found");
        }
        return userDAO.getUser(id);
    } */

    // with text
    @Override
    public User getUser(String id) {
        User user = null;
        try {
            user = userDAO.getUser(Integer.parseInt(id));
        } catch (UserNotFoundException | UserDAOPersistenceException e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND, "User not found");
        } catch (NumberFormatException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Bad request, try to use a number");
        }
        return user;
    }

    //json
    @Override
    public User createUser(User user) throws UserDAOPersistenceException {
        try {
            userDAO.getUserByName(user.getUserName());
            setStatus(Status.CLIENT_ERROR_CONFLICT, "User already exists");
        } catch (UserNotFoundException e) {
            userDAO.saveUser(user);
        }
        return user;
    }

    //json
    @Override
    public void updateUser(User user) throws UserDAOPersistenceException {
        userDAO.updateUser(user);
    }

    //query params
    @Override
    public void deleteUser() throws UserNotFoundException, UserDAOPersistenceException {
        String id = getQueryValue("id");
        User user = userDAO.getUser(Integer.parseInt(id));
        userDAO.deleteUser(user);
    }
}
