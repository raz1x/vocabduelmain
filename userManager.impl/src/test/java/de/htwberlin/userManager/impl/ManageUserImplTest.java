package de.htwberlin.userManager.impl;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// TODO: Implement
// Probleme mit Spring DI

@ExtendWith(MockitoExtension.class)
public class ManageUserImplTest extends TestCase {

    @InjectMocks
    private ManageUserImpl manageUser;

    @Mock
    private UserDAO userDAO;

    @Test
    public void testRegisterUser() {

    }

    public void testLoginUser() {
    }

    public void testLogoutUser() {
    }

    public void testDeleteUser() {
    }

    public void testUpdateUserName() {
    }

    public void testUpdatePassword() {
    }

    public void testGetByName() {
    }

    public void testGetById() {
    }

    public void testGetAllUsers() {
    }

    public void testGetOpponents() {
    }

    public void testUserExists() {
    }
}