package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ManageUserImplTest {
    private static ManageUserImpl manageUser;

    @BeforeAll
    public static void init() {
        manageUser = new ManageUserImpl();
    }

    @Test
    public void testRegisterUser() {
        // Assert
        Assertions.assertThrowsExactly(UserAlreadyExistsException.class, () -> manageUser.registerUser("MaxMuster", "test"));
    }

    @Test
    public void testLoginUser() throws UserNotFoundException {
        // Arrange + Act
        User loggedInUser = manageUser.loginUser("MaxMuster", "test");
        // Assert
        Assertions.assertEquals(loggedInUser.getUserName(), "MaxMuster");
    }

    @Test
    public void testLogoutUser() throws UserNotFoundException {
        // Arrange + Act
        manageUser.logoutUser(1);
        // Assert
        // no assert needed
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        manageUser.deleteUser(1);
    }

    @Test
    public void testUpdateUserName() {
        Assertions.assertThrowsExactly(UserAlreadyExistsException.class, () -> manageUser.updateUserName(1, "test"));
    }

    @Test
    public void testUpdatePassword() throws UserNotFoundException {
        Assertions.assertEquals("newPass", manageUser.updatePassword(1, "newPass").getPassword());
    }

    @Test
    public void testGetById() throws UserNotFoundException {
        User testUser = manageUser.getById(1);
        Assertions.assertEquals("MaxMuster", testUser.getUserName());
        Assertions.assertEquals("test", testUser.getPassword());
    }

    @Test
    public void userExists() {
        Assertions.assertTrue(manageUser.userExists("MaxMuster"));
    }
}