package de.htwberlin.userManager.rest_server;

import de.htwberlin.userManager.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manageUser")
public class ManageUserRestAdapter {

    @Autowired
    private ManageUser manageUser;

    @PostMapping("/registerUser/{userName}/{password}")
    public User registerUser(@PathVariable("userName") String userName, @PathVariable("password") String password) throws UserAlreadyExistsException {
        return manageUser.registerUser(userName, password);
    }

    @PostMapping("/loginUser/{userName}/{password}")
    public User loginUser(@PathVariable("userName") String userName, @PathVariable("password") String password) throws UserNotFoundException, WrongPasswordException {
        return manageUser.loginUser(userName, password);
    }

    @PostMapping("/logoutUser/{userId}")
    public void logoutUser(@PathVariable int userId) throws UserNotFoundException {
        manageUser.logoutUser(userId);
    }

    @PostMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable int userId) throws UserNotFoundException {
        manageUser.deleteUser(userId);
    }

    @PostMapping("/updateUserName/{userId}/{userName}")
    public User updateUserName(@PathVariable("userId") int userId, @PathVariable("userName") String userName) throws UserNotFoundException, UserAlreadyExistsException {
        return manageUser.updateUserName(userId, userName);
    }

    @PostMapping("/updatePassword/{userId}/{newPassword}")
    public User updatePassword(@PathVariable("userId") int userId, @PathVariable("newPassword") String newPassword) throws UserNotFoundException {
        return manageUser.updatePassword(userId, newPassword);
    }

    @GetMapping("/getByName/{userName}")
    public User getByName(@PathVariable String userName) throws UserNotFoundException {
        return manageUser.getByName(userName);
    }

    @GetMapping("/getById/{userId}")
    public User getById(@PathVariable int userId) throws UserNotFoundException {
        return manageUser.getById(userId);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() throws UserNotFoundException {
        return manageUser.getAllUsers();
    }

    @GetMapping("/getOpponents/{currentUserId}")
    public List<User> getOpponents(@PathVariable int currentUserId) throws UserNotFoundException {
        return manageUser.getOpponents(currentUserId);
    }

    @GetMapping("/userExists/{userName}")
    public boolean userExists(@PathVariable String userName) {
        return manageUser.userExists(userName);
    }
}