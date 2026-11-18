package dk.easv.mrs.GUI.Model;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.BLL.MovieManager;
import dk.easv.mrs.BLL.UserManager;

import java.io.IOException;
import java.sql.SQLException;

public class UserModel {

    private final UserManager userManager;

    public UserModel() {
        userManager = new UserManager();
    }

    public boolean login(User user) throws SQLException, IOException {
        return userManager.login(user);
    }

    public boolean register(User user) throws SQLException, IOException {
        return userManager.register(user);
    }
}
