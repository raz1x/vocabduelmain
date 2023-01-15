package de.htwberlin.restclient;

import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.export.UserNotFoundException;
import de.htwberlin.userManager.restserver.ManageUserService;
import org.restlet.resource.ClientResource;

public class ManageUserClientAdapter implements ManageUserService {

    private static final String BASE_URI = "http://localhost:8182";

    private ManageUserService clientResource = ClientResource.create(BASE_URI + "/user", ManageUserService.class);

    @Override
    public User getUser(String id) {
        return clientResource.getUser(id);
    }

    @Override
    public User createUser(User user) throws UserDAOPersistenceException {
        return clientResource.createUser(user);
    }

    @Override
    public void updateUser(User user) throws UserDAOPersistenceException {
        clientResource.updateUser(user);
    }

    @Override
    public void deleteUser() throws UserNotFoundException, UserDAOPersistenceException {
        clientResource.deleteUser();
    }
}
