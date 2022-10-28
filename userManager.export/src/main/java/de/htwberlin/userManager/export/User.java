package de.htwberlin.userManager.export;

public class User {
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public int userId;

    public String userName;

    public String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
