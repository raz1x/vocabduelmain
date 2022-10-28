package de.htwberlin.userManager.export;

public interface ManageUser {
    public User registerUser(String userName, String password) throws UserAlreadyExistsException;

    public User loginUser(String userName, String password) throws UserNotFoundException;

    public void logoutUser(int userId) throws UserNotFoundException;

    public void deleteUser(int userId) throws UserNotFoundException;

    public void updateUserName(int userId, String userName) throws UserNotFoundException;

    public void updatePassword(int userId, String newPassword);

    public User getById(int userId) throws UserNotFoundException;

    public User getByUserName(String userName) throws UserNotFoundException;

}

