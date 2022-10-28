package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ManageUserTest {
    private static ManageUserImpl manageUser;

    @BeforeAll
    public static void setManageUser() {
        manageUser = new ManageUserImpl();
    }

    @Test
    public void testRegisterUser() {
        // Arrange + Act
        User newUser = manageUser.registerUser("MaxMuster", "test");
        // Assert
        Assertions.assertEquals(newUser.getUserName(),"MaxMuster");
    }

    @Test
    public void testLoginUser() {
        // Arrange + Act
        User loggedInUser = manageUser.loginUser("MaxMuster", "test");
        // Assert
        Assertions.assertEquals(loggedInUser.getUserName(),"MaxMuster");
    }

    @Test
    public void testLogoutUser() {
        // Arrange + Act
        manageUser.logoutUser(1);
        // Assert
        // no assert needed
    }
}
