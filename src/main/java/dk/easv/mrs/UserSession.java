package dk.easv.mrs;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.BE.UserRole;

public class UserSession {

    private static UserSession instance;

    private User currentUser;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }

    public boolean isAdmin() {
        return UserSession.getInstance()
                .getCurrentUser()
                .role() == UserRole.ADMIN;
    }
}
