package dk.easv.mrs.DAL;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BE.Rating;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RatingDAO_DB implements IRatingDataAccess {
    @Override
    public List<Rating> getAllRatings(Movie movie) throws SQLException, IOException {
        String sql = """
                SELECT Rating.RatingId, Rating.MovieId, Rating.UserId, [User].Name, Rating.Score, Rating.Review
                FROM Rating
                    JOIN [User]
                        ON Rating.UserId = [User].Id
                WHERE Rating.MovieId = ?
                """;
        List<Rating> ratings = new ArrayList<>();
        try (Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movie.id());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ratings.add(createRatingFromResult(resultSet));
                }
            }
        }
        ratings.sort(Comparator.comparingInt(Rating::id));
        return ratings;
    }

    @Override
    public Rating createRating(Rating rating) throws SQLException, IOException {
        String sql = """
                INSERT INTO dbo.Rating (MovieId, UserId, Score, Review)
                OUTPUT INSERTED.RatingId, INSERTED.UserId
                SELECT ?, Id, ?, ?
                FROM dbo.[User]
                WHERE Name = ?
                """;
        Rating createdRating = null;
        try(Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, rating.movieId());
            preparedStatement.setString(2, rating.name());
            preparedStatement.setInt(3, rating.score());
            preparedStatement.setString(4, rating.review());

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    createdRating = new Rating(resultSet.getInt("RatingId"), rating.movieId(), resultSet.getInt("UserId"), rating.name(), rating.score(), rating.review());
                }
            } catch (SQLException e) {
                throw new RuntimeException("Could not retrieve generated rating id.");
            }
        }
        return createdRating;
    }

    @Override
    public void deleteRating(Rating rating) throws SQLException, IOException {
        String sql = "DELETE FROM dbo.Rating WHERE RatingId = ?";
        try (Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, rating.id());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateRating(Rating rating) throws SQLException, IOException {
        String sql = "UPDATE dbo.Rating SET Score = ?, Review = ? WHERE RatingId = ?";
        try(Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, rating.score());
            preparedStatement.setString(2, rating.review());
            preparedStatement.setInt(3, rating.id());

            preparedStatement.executeUpdate();
        }

    }

    private Rating createRatingFromResult (ResultSet resultSet) throws SQLException {
        return new Rating (
                resultSet.getInt("Id"),
                resultSet.getInt("MovieId"),
                resultSet.getInt("UserId"),
                resultSet.getString("Name"),
                resultSet.getInt("Score"),
                resultSet.getString("Review")
        );
    }
}
