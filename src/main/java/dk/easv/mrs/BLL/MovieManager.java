package dk.easv.mrs.BLL;
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.IMovieDataAccess;
import dk.easv.mrs.DAL.MovieDAO_DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MovieManager {
    private final IMovieDataAccess movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO_DB();
    }

    public List<Movie> getAllMovies() throws SQLException, IOException {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws SQLException, IOException {
        return movieDAO.searchMovies(query);
    }

    public void createMovie(Movie movie) throws SQLException, IOException {
        movieDAO.createMovie(movie);
    }

    public void deleteMovie(Movie movie) throws SQLException, IOException {
        movieDAO.deleteMovie(movie);
    }

    public void updateMovie(Movie movie) throws SQLException, IOException {
        movieDAO.updateMovie(movie);
    }
}
