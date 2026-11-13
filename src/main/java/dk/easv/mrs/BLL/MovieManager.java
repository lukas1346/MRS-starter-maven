package dk.easv.mrs.BLL;
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.IMovieDataAccess;
import dk.easv.mrs.DAL.MovieDAO_File;
import java.util.List;

public class MovieManager {
    private final IMovieDataAccess movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO_File();
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws Exception {
        return movieDAO.searchMovies(query);
    }

    public void createMovie(Movie movie) throws Exception {
        movieDAO.createMovie(movie);
    }

    public void deleteMovie(Movie movie) throws Exception {
        movieDAO.deleteMovie(movie);
    }

    public void updateMovie(Movie movie) throws Exception {
        movieDAO.updateMovie(movie);
    }
}
