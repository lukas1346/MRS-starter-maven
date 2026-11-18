package dk.easv.mrs.BLL;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BE.Rating;
import dk.easv.mrs.DAL.IMovieDataAccess;
import dk.easv.mrs.DAL.IRatingDataAccess;
import dk.easv.mrs.DAL.MovieDAO_DB;
import dk.easv.mrs.DAL.RatingDAO_DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RatingManager {
    private final IRatingDataAccess ratingDAO;

    public RatingManager() {
        ratingDAO = new RatingDAO_DB();
    }

    public List<Rating> getAllRatings(Movie movie) throws SQLException, IOException {
        return ratingDAO.getAllRatings(movie);
    }

    public void createRating(Rating rating) throws SQLException, IOException {
        ratingDAO.createRating(rating);
    }

    public void deleteRating(Rating rating) throws SQLException, IOException {
        ratingDAO.deleteRating(rating);
    }

    public void updateRating(Rating rating) throws SQLException, IOException {
        ratingDAO.updateRating(rating);
    }

}
