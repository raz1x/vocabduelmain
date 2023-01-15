package de.htwberlin.userManager.restserver;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;

public class RestletLauncher {
    public static void main(String[] args) {
        launchApplication();
    }

    private static void launchApplication() {
        try {
            Component component = new Component();

            component.getServers().add(Protocol.HTTP, 8182);

            Application application = new ManageUserApplication();

            component.getDefaultHost().attachDefault(application);

            component.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
