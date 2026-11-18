package dk.easv.mrs.DAL;

import dk.easv.mrs.BE.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO_DB implements IUserDataAccess{


    @Override
    public String getPasswordHash(User user) throws IOException, SQLException {
        String sql = "SELECT PasswordHash FROM [User] WHERE Name = ?";
        try (Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.name());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return resultSet.getString("PasswordHash");
                }
                return null;
            }
        }
    }

    @Override
    public boolean createUser(User user) throws IOException, SQLException {
        String sql = "INSERT INTO [User] (Name, PasswordHash) VALUES(?, ?)";
        try(Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.name());
            preparedStatement.setString(2, user.password());

            preparedStatement.executeUpdate();

            return true;
        }
    }
}
