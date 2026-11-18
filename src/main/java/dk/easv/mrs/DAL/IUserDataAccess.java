package dk.easv.mrs.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.mrs.BE.Rating;
import dk.easv.mrs.BE.User;

import java.io.IOException;
import java.sql.SQLException;

public interface IUserDataAccess {

    String getPasswordHash(User user) throws IOException, SQLException;

    boolean createUser(User user) throws IOException, SQLException;

}
