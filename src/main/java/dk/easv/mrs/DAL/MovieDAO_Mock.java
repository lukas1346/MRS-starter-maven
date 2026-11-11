package dk.easv.mrs.DAL;
import dk.easv.mrs.BE.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_Mock implements IMovieDataAccess {

    private List<Movie> allMovies;

    public MovieDAO_Mock()
    {
        allMovies = new ArrayList<>();
        allMovies.add(new Movie(1, 1991,"Terminator 2"));
        allMovies.add(new Movie(2, 2001,"Harry Potter and the Sorcerer´s Stone"));
        allMovies.add(new Movie(3, 2010, "Inception"));
    }

    @Override
    public List<Movie> getAllMovies() {
        return allMovies;
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        return null;
    }

    @Override
    public List<Movie> searchMovies(String query) throws Exception {
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }

}
