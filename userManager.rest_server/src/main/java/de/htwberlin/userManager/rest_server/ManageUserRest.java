package de.htwberlin.userManager.rest_server;

import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserNotFoundException;
import de.htwberlin.userManager.export.WrongPasswordException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface ManageUserRest {
    @PostMapping("/registerUser/{userName}/{password}")
    User registerUser(@PathVariable("userName") String userName, @PathVariable("password") String password) throws UserAlreadyExistsException;

    @PostMapping("/loginUser/{userName}/{password}")
    User loginUser(@PathVariable("userName") String userName, @PathVariable("password") String password) throws UserNotFoundException, WrongPasswordException;

    @PostMapping("/logoutUser/{userId}")
    void logoutUser(@PathVariable("userId") int userId) throws UserNotFoundException;

    @PostMapping("/deleteUser/{userId}")
    void deleteUser(@PathVariable("userId") int userId) throws UserNotFoundException;

    @PostMapping("/updateUserName/{userId}/{userName}")
    User updateUserName(@PathVariable("userId") int userId, @PathVariable("userName") String userName) throws UserNotFoundException, UserAlreadyExistsException;

    @PostMapping("/updatePassword/{userId}/{newPassword}")
    User updatePassword(@PathVariable("userId") int userId, @PathVariable("newPassword") String newPassword) throws UserNotFoundException;

    @GetMapping("/getByName/{userName}")
    User getByName(@PathVariable("userName") String userName) throws UserNotFoundException;

    @GetMapping("/getById/{userId}")
    User getById(@PathVariable("userId") int userId) throws UserNotFoundException;

    @GetMapping("/getAllUsers")
    List<User> getAllUsers() throws UserNotFoundException;

    @GetMapping("/getOpponents/{currentUserId}")
    List<User> getOpponents(@PathVariable("currentUserId") int currentUserId) throws UserNotFoundException;

    @GetMapping("/userExists/{userName}")
    boolean userExists(@PathVariable("userName") String userName);
}
