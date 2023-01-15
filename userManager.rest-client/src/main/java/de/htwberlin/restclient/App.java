package de.htwberlin.restclient;

import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.restserver.ManageUserService;


public class App {
    private ManageUserService service = new ManageUserClientAdapter();

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        try {
            System.out.println(service.createUser(new User("TestUser2", "TestPassword")).getUserName());

        } catch (UserDAOPersistenceException e) {
            throw new RuntimeException(e);
        }
    }
}
