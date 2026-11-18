package dk.easv.mrs.BLL;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.DAL.IRatingDataAccess;
import dk.easv.mrs.DAL.IUserDataAccess;
import dk.easv.mrs.DAL.RatingDAO_DB;
import dk.easv.mrs.DAL.UserDAO_DB;

import java.io.IOException;
import java.sql.SQLException;

public class UserManager {

    private final IUserDataAccess userDAO;
    private final PasswordService passwordService;

    public UserManager() {
        userDAO = new UserDAO_DB();
        passwordService = new PasswordService();

    }

    public boolean login(User user) throws SQLException, IOException {
        String passwordHash = userDAO.getPasswordHash(user);
        return passwordService.verifyPassword(user.password(), passwordHash);
    }

    public boolean register(User user) throws SQLException, IOException {
        String passwordHash = passwordService.hashPassword(user.password());
        User userBuffer = new User(user.name(), passwordHash);
        return userDAO.createUser(userBuffer);
    }


}
