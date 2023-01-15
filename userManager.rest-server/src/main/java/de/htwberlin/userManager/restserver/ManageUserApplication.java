package de.htwberlin.userManager.restserver;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class ManageUserApplication extends Application {

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router();

        router.attach("/user", ManageUserRestAdapter.class);
        router.attach("/user/{id}", ManageUserRestAdapter.class);

        return router;
    }
}
