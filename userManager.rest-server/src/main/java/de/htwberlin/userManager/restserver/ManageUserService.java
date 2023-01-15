package de.htwberlin.userManager.restserver;

import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;


public interface ManageUserService {

    @Get("text")
    public User getUser(String id);

    @Put("json")
    public User createUser(User user) throws UserDAOPersistenceException;

    @Post("json")
    public void updateUser(User user) throws UserDAOPersistenceException;

    @Delete("text")
    public void deleteUser() throws UserNotFoundException, UserDAOPersistenceException;
}