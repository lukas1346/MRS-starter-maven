package dk.easv.mrs.DAL;
import dk.easv.mrs.BE.Movie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IMovieDataAccess {

    List<Movie> getAllMovies() throws SQLException, IOException;

    Movie createMovie(Movie movie) throws SQLException, IOException;

    List<Movie> searchMovies(String query) throws SQLException, IOException;

    void updateMovie(Movie movie) throws SQLException, IOException;

    void deleteMovie(Movie movie) throws SQLException, IOException;
}
