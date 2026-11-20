package dk.easv.mrs.DAL;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.BE.UserRole;

import java.io.IOException;
import java.sql.SQLException;

public interface IUserDataAccess {

    String getPasswordHash(User user) throws IOException, SQLException;

    boolean createUser(User user) throws IOException, SQLException;

    UserRole getRole(User user) throws IOException, SQLException;
}
