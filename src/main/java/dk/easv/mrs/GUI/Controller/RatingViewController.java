package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BE.Rating;
import dk.easv.mrs.GUI.Model.RatingModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class RatingViewController implements Initializable {
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtReview;
    @FXML
    private Slider sliderRating;
    @FXML
    private Label lblSelectedMovie;
    @FXML
    private TableView<Rating> tblRatings;
    @FXML
    private TableColumn<Rating, String> colUser;
    @FXML
    private TableColumn<Rating, String> colRating;
    @FXML
    private TableColumn<Rating, String> colReview;
    @FXML
    private Label lblRating;

    private RatingModel ratingModel;
    private Rating selectedRating;
    private Movie selectedMovie;

    public void initializeModel(Movie movie)  {
        this.selectedMovie = movie;
        try {
            ratingModel = new RatingModel(selectedMovie);
            tblRatings.setItems(ratingModel.getObservableMovies());
            lblSelectedMovie.setText("Selected Movie: " + selectedMovie);
        } catch (Exception e) {
            e.printStackTrace();
            displayError(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        sliderRating.setMin(1);
        sliderRating.setMax(10);
        sliderRating.setMajorTickUnit(1);
        sliderRating.setMinorTickCount(0);
        sliderRating.setSnapToTicks(true);
        sliderRating.setBlockIncrement(1);


        colUser.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().name())
        );

        colRating.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        String.valueOf(cellData.getValue().score())
                )
        );
        colReview.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().review()
                )
        );

        tblRatings.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    selectedRating = newValue;
                    txtUser.setText(selectedRating.name());
                    txtReview.setText(selectedRating.review());
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });

        sliderRating.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            lblRating.setText("Rating: " + newValue.intValue());
        });
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        if (t instanceof SQLException) {
            alert.setHeaderText("Database error: " + t.getMessage());
        } else if (t instanceof IOException) {
            alert.setHeaderText("File error: " + t.getMessage());
        } else {
            alert.setHeaderText(t.getMessage());
        }
        alert.showAndWait();
    }

    @FXML
    private void createRating(ActionEvent actionEvent) {
        if(!txtUser.getText().isEmpty() && !(txtReview.getText()).isEmpty()) {
            try {
                ratingModel.createRating(new Rating(
                        -1,
                        selectedMovie.id(),
                        -1,
                        txtUser.getText(),
                        (int) sliderRating.getValue(),
                        txtReview.getText()));
            } catch (Exception e) {
                displayError(e);
            }
        }
        else {
            displayError(new IllegalArgumentException("Please enter a valid review and or username."));
        }
    }

    @FXML
    private void deleteRating(ActionEvent actionEvent) {
        if(selectedRating != null) {
            try {
                ratingModel.deleteRating(selectedRating);
            } catch (Exception e) {
                displayError(e);
            }
        }
    }

    @FXML
    private void updateRating(ActionEvent actionEvent) {
        if(selectedRating != null) {
            try {
                Rating updatedRating = new Rating(selectedRating.id(), selectedMovie.id(), selectedRating.userId(), selectedRating.name(), (int) sliderRating.getValue(), txtReview.getText());
                ratingModel.updateRating(updatedRating);
            } catch (Exception e) {
                displayError(e);
            }
        }
    }
}
