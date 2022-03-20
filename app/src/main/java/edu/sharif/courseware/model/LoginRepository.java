package edu.sharif.courseware.model;

public class LoginRepository {

    private static LoginRepository instance;
    private String username = null;

    public static LoginRepository getInstance() {
        if (instance == null)
            instance = new LoginRepository();
        return instance;
    }

    public boolean isLoggedIn() {
        return this.username != null;
    }

    public void logIn(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void logOut() {
        this.username = null;
    }

}
