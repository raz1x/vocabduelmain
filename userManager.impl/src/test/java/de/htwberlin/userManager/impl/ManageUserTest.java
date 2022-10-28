package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

public class ManageUserTest {
    private static ManageUserImpl manageUser;

    @BeforeAll
    public static void init() {
        manageUser = new ManageUserImpl();
    }
    @Test
    public void testRegisterUser() {
        // Arrange + Act
        User newUser = manageUser.registerUser("MaxMuster", "test");
        // Assert
        assertEquals(newUser.getUserName(),"MaxMuster");
    }

    @Test
    public void testLoginUser() {
        // Arrange + Act
        User loggedInUser = manageUser.loginUser("MaxMuster", "test");
        // Assert
        assertEquals(loggedInUser.getUserName(),"MaxMuster");
    }

    @Test
    public void testLogoutUser() {
        // Arrange + Act
        manageUser.logoutUser(1);
        // Assert
        // no assert needed
    }
}
