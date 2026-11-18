package dk.easv.mrs.DAL;
import dk.easv.mrs.BE.Movie;
import java.io.*;
import java.sql.*;
import java.util.*;

public class MovieDAO_DB implements IMovieDataAccess {
    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    //@Override

    @Override
    public List<Movie> getAllMovies() throws SQLException, IOException {
        String sql = "SELECT Id, Year, Title FROM dbo.Movie";
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = new DBConnector().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                movies.add(createMovieFromResult(resultSet));
            }
        }
        movies.sort(Comparator.comparing(Movie::title));
        return movies;
    }

    @Override
    public Movie createMovie(Movie movie) throws SQLException, IOException {
        String sql = "INSERT INTO dbo.Movie (Year, Title) VALUES(?, ?)";
        Movie createdMovie = null;
        try(Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movie.year());
            preparedStatement.setString(2, movie.title());

            preparedStatement.executeUpdate();


            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    createdMovie = new Movie(resultSet.getInt("Id"), movie.title(), movie.year());
                }
            } catch (SQLException e) {
                throw new RuntimeException("Could not retrieve generated movie id.");
            }
        }
        return createdMovie;
    }


    @Override
    public void deleteMovie(Movie movie) throws SQLException, IOException {
        String sql = "DELETE FROM dbo.Movie WHERE Id = ?";
        try (Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movie.id());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Movie> searchMovies(String query) throws SQLException, IOException {
        if(!query.isEmpty()) {
            List<Movie> moviesFound = new ArrayList<>();
            String sql = "SELECT TOP 20 * FROM dbo.Movie WHERE Title LIKE ? ORDER BY Title";
            try (Connection connection = new DBConnector().getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "%" + query + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        moviesFound.add(createMovieFromResult(resultSet));
                    }
                }
            }
            return moviesFound;
        }
        else {
            return getAllMovies();
        }
    }



    @Override
    public void updateMovie(Movie movie) throws SQLException, IOException {
        String sql = "UPDATE dbo.Movie SET Title = ?, Year = ? WHERE Id = ?";
        try(Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, movie.title());
            preparedStatement.setInt(2, movie.year());
            preparedStatement.setInt(3, movie.id());

            preparedStatement.executeUpdate();
        }

    }

    private Movie createMovieFromResult (ResultSet resultSet) throws SQLException {
        return new Movie (
                resultSet.getInt("Id"),
                resultSet.getString("Title"),
                resultSet.getInt("Year")
        );
    }
}