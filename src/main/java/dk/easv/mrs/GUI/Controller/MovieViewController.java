package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Main;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class MovieViewController implements Initializable {

    @FXML
    private TextField txtMovieSearch;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtYear;
    @FXML
    private TableView<Movie> tblMovies;
    @FXML
    private TableColumn<Movie, String> colTitle;
    @FXML
    private TableColumn<Movie, String> colYear;

    private MovieModel movieModel;
    private Movie selectedMovie;

    public MovieViewController()  {
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        colTitle.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().title())
        );

        colYear.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        String.valueOf(cellData.getValue().year())
                )
        );

        tblMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.scheduleMovieSearch(newValue);
            } catch (Exception e) {
                displayError(e);
            }
        });

        tblMovies.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    selectedMovie = newValue;
                    txtTitle.setText(selectedMovie.title());
                    txtYear.setText(String.valueOf(selectedMovie.year()));
                } catch (Exception e) {
                    displayError(e);
                }
            }
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
    private void createMovie(ActionEvent actionEvent) {
        if(!txtYear.getText().isEmpty() && (txtYear.getText().matches("[0-9]+"))) {
            try {
                movieModel.createMovie(new Movie(-1, txtTitle.getText(), parseInt(txtYear.getText())));
            } catch (Exception e) {
                displayError(e);
            }
        }
        else {
            displayError(new IllegalArgumentException("Please enter a valid year."));
        }
    }

    @FXML
    private void deleteMovie(ActionEvent actionEvent) {
        if(selectedMovie != null) {
            try {
                movieModel.deleteMovie(selectedMovie);
            } catch (Exception e) {
                displayError(e);
            }

        }
    }

    @FXML
    private void updateMovie(ActionEvent actionEvent) {
        if(selectedMovie != null) {
            try {
                Movie updatedMovie = new Movie(selectedMovie.id(), txtTitle.getText(), parseInt(txtYear.getText()));
                movieModel.updateMovie(updatedMovie);
                movieModel.scheduleMovieSearch(txtMovieSearch.getText());
            } catch (Exception e) {
                displayError(e);
            }
        }
    }

    @FXML
    private void openRating(ActionEvent actionEvent) throws Exception {
        Main.loadRating(selectedMovie);
    }
}
