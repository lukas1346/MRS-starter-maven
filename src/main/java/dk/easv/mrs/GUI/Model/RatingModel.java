package dk.easv.mrs.GUI.Model;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BE.Rating;
import dk.easv.mrs.BLL.RatingManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RatingModel {
    private final ObservableList<Rating> ratingsToBeViewed;
    private final RatingManager ratingManager;
    private final Movie selectedMovie;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> scheduledTask;

    public RatingModel(Movie selectedMovie) throws SQLException, IOException {
        this.selectedMovie = selectedMovie;
        ratingManager = new RatingManager();
        ratingsToBeViewed = FXCollections.observableArrayList();
        ratingsToBeViewed.addAll(ratingManager.getAllRatings(selectedMovie));
    }

    public ObservableList<Rating> getObservableMovies() {
        return ratingsToBeViewed;
    }

    private void searchRatings() throws SQLException, IOException {
        List<Rating> searchResults = ratingManager.getAllRatings(selectedMovie);
        ratingsToBeViewed.clear();
        ratingsToBeViewed.addAll(searchResults);
    }

    public void scheduleRatingsSearch() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
            System.out.println("Search interrupted");
        }

        scheduledTask = executor.schedule(() -> {
            try {
                searchRatings();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, 300, TimeUnit.MILLISECONDS);
    }

    public void createRating(Rating rating) throws SQLException, IOException {
        ratingManager.createRating(rating);
        searchRatings();
    }

    public void deleteRating(Rating selectedRating) throws SQLException, IOException {
        ratingManager.deleteRating(selectedRating);
        searchRatings();
    }

    public void updateRating(Rating rating) throws SQLException, IOException {
        ratingManager.updateRating(rating);
        searchRatings();
    }
}
