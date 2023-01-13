package de.htwberlin.userManager.rest;

import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpStatusCodeException;

public class ManageUserRestAdapter extends ServerResource {

    @Autowired
    ManageUser manageUser;

    @Get("getUser")
    public User getUserById(Representation entity) {
        Form form = new Form(entity);
        int id = Integer.parseInt(form.getValues("userId"));
        try {
            return manageUser.getById(id);
        } catch (UserNotFoundException e) {
            throw new  {
            }
        }
    }
}
