package dk.easv.mrs.DAL;
import dk.easv.mrs.BE.Movie;
import java.io.*;
import java.sql.*;
import java.util.*;

public class MovieDAO_File implements IMovieDataAccess {
    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    //@Override

    public List<Movie> getAllMovies() throws SQLException, IOException {
        String sql = "SELECT movieId, yearReleased, movieTitle FROM Movies";
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = new DBConnector().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                movies.add(createMovieFromResult(resultSet));
            }
        }
        movies.sort(Comparator.comparing(Movie::getTitle));
        return movies;
    }

    @Override
    public Movie createMovie(Movie movie) throws IOException, SQLException {
        String sql = "INSERT INTO Movies (yearReleased, movieTitle) VALUES(?, ?)";
        try(Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movie.getYear());
            preparedStatement.setString(2, movie.getTitle());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                movie.setId(resultSet.getInt("movieId"));
            }
        }
        return movie;
    }


    @Override
    public void deleteMovie(Movie movie) throws SQLException, IOException {
        String sql = "DELETE FROM Movies WHERE movieId = ?";
        try (Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movie.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Movie> searchMovies(String query) throws IOException, SQLException {
        if(!query.isEmpty()) {
            List<Movie> moviesFound = new ArrayList<>();
            String sql = "SELECT TOP 20 * FROM Movies WHERE movieTitle LIKE ? ORDER BY movieTitle";
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
    public void updateMovie(Movie movie) throws Exception {
        String sql = "UPDATE Movies SET yearReleased = ?, movieTitle = ? WHERE movieId = ?";
        try(Connection connection = new DBConnector().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, movie.getYear());
            preparedStatement.setString(2, movie.getTitle());
            preparedStatement.setInt(3, movie.getId());

            preparedStatement.executeUpdate();
        }
    }

    private Movie createMovieFromResult (ResultSet resultSet) throws SQLException {
        return new Movie (
                resultSet.getInt("movieId"),
                resultSet.getInt("yearReleased"),
                resultSet.getString("movieTitle")
        );
    }
}