package dk.easv.mrs.DAL;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BE.Rating;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IRatingDataAccess {

    List<Rating> getAllRatings(Movie movie) throws SQLException, IOException;

    Rating createRating(Rating rating) throws SQLException, IOException;

    void updateRating(Rating rating) throws SQLException, IOException;

    void deleteRating(Rating rating) throws SQLException, IOException;
}
