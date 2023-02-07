package de.htwberlin.userManager.impl;

import de.htwberlin.userManager.export.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManageUserImplTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private ManageUserImpl manageUser;

    @Test
    void registerUser() throws UserAlreadyExistsException, UserNotFoundException, UserDAOPersistenceException {
        // 1. Arrange
        when(userDAO.getUserByName("test")).thenThrow(UserNotFoundException.class);
        // 2. Act
        manageUser.registerUser("test", "test");
        // 3. Assert
        verify(userDAO, times(1)).getUserByName("test");
        verify(userDAO, times(1)).saveUser(any());
    }

    @Test
    void loginUser() throws UserNotFoundException, WrongPasswordException, UserDAOPersistenceException {
        // 1. Arrange
        User expected = new User("test", "test");
        when(userDAO.getUserByName("test")).thenReturn(expected);
        when(userDAO.checkUserNameAndPassword("test", "test")).thenReturn(true);
        // 2. Act
        User user = manageUser.loginUser("test", "test");
        // 3. Assert
        Assertions.assertEquals(expected, user);
        verify(userDAO, times(1)).getUserByName("test");
        verify(userDAO, times(1)).checkUserNameAndPassword("test", "test");
    }

    @Test
    void loginUserWrongPassword() throws UserNotFoundException, WrongPasswordException, UserDAOPersistenceException {
        // 1. Arrange
        when(userDAO.checkUserNameAndPassword("test", "test")).thenReturn(false);
        // 2. Act
        // 3. Assert
        Assertions.assertThrows(WrongPasswordException.class, ()-> manageUser.loginUser("test", "test"));
    }

    @Test
    void logoutUser() throws UserNotFoundException, UserDAOPersistenceException {
        // 1. Arrange
        User user = new User("test", "test");
        User spyUser = spy(user);
        when(userDAO.getUser(1)).thenReturn(spyUser);
        // 2. Act
        manageUser.logoutUser(1);
        // 3. Assert
        verify(spyUser, times(1)).setLoggedIn(false);
        verify(userDAO, times(1)).getUser(1);
        verify(userDAO, times(1)).updateUser(spyUser);
    }

    @Test
    void deleteUser() throws UserNotFoundException, UserDAOPersistenceException {
        // 1. Arrange
        User user = new User("test", "test");
        when(userDAO.getUser(1)).thenReturn(user);
        // 2. Act
        manageUser.deleteUser(1);
        // 3. Assert
        verify(userDAO, times(1)).getUser(1);
        verify(userDAO, times(1)).deleteUser(user);
    }

    @Test
    void updateUserName() throws UserNotFoundException, UserAlreadyExistsException, UserDAOPersistenceException {
        // 1. Arrange
        User user = new User("test", "test");
        User spyUser = spy(user);
        when(userDAO.getUser(1)).thenReturn(spyUser);
        when(userDAO.getUserByName("test2")).thenThrow(UserNotFoundException.class);
        // 2. Act
        User testUser = manageUser.updateUserName(1, "test2");
        // 3. Assert
        Assertions.assertEquals("test2", testUser.getUsername());
        verify(userDAO, times(1)).getUser(1);
        verify(userDAO, times(1)).getUserByName("test2");
        verify(spyUser, times(1)).setUsername("test2");
    }

    @Test
    void updatePassword() throws UserNotFoundException, UserDAOPersistenceException {
        // 1. Arrange
        User user = new User("test", "test");
        User spyUser = spy(user);
        when(userDAO.getUser(1)).thenReturn(spyUser);
        // 2. Act
        User testUser = manageUser.updatePassword(1, "test2");
        // 3. Assert
        Assertions.assertEquals("test2", testUser.getPassword());
        verify(userDAO, times(1)).getUser(1);
        verify(spyUser, times(1)).setPassword("test2");
    }

    @Test
    void getByName() throws UserNotFoundException {
        // 1. Arrange
        User user = new User("test", "test");
        when(userDAO.getUserByName("test")).thenReturn(user);
        // 2. Act
        User testUser = manageUser.getByName("test");
        // 3. Assert
        Assertions.assertEquals(user, testUser);
        verify(userDAO, times(1)).getUserByName("test");
    }

    @Test
    void getById() throws UserNotFoundException, UserDAOPersistenceException {
        // 1. Arrange
        User user = new User("test", "test");
        when(userDAO.getUser(1)).thenReturn(user);
        // 2. Act
        User testUser = manageUser.getById(1);
        // 3. Assert
        Assertions.assertEquals(user, testUser);
        verify(userDAO, times(1)).getUser(1);
    }

    @Test
    void getAllUsers() throws UserNotFoundException {
        // 1. Arrange
        List<User> users = new ArrayList<>();
        users.add(new User("test", "test"));
        when(userDAO.getAllUsers()).thenReturn(users);
        // 2. Act
        List<User> testUsers = manageUser.getAllUsers();
        // 3. Assert
        Assertions.assertEquals(users, testUsers);
        verify(userDAO, times(1)).getAllUsers();
    }

    @Test
    void getOpponents() throws UserNotFoundException {
        // 1. Arrange
        List<User> users = new ArrayList<>();
        users.add(new User("test", "test"));
        when(userDAO.getOpponents(1)).thenReturn(users);
        // 2. Act
        List<User> testUsers = manageUser.getOpponents(1);
        // 3. Assert
        Assertions.assertEquals(users, testUsers);
        verify(userDAO, times(1)).getOpponents(1);
    }
}