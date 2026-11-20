package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.ErrorDisplay;
import dk.easv.mrs.GUI.Main;
import dk.easv.mrs.GUI.Model.MovieModel;
import dk.easv.mrs.UserSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class MovieViewController implements Initializable {

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
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
            ErrorDisplay.displayError(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        boolean admin = UserSession.getInstance().isAdmin();

        btnCreate.setDisable(!admin);
        btnUpdate.setDisable(!admin);
        btnDelete.setDisable(!admin);


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
                ErrorDisplay.displayError(e);
            }
        });

        tblMovies.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    selectedMovie = newValue;
                    txtTitle.setText(selectedMovie.title());
                    txtYear.setText(String.valueOf(selectedMovie.year()));
                } catch (Exception e) {
                    ErrorDisplay.displayError(e);
                }
            }
        });
    }

    @FXML
    private void createMovie(ActionEvent actionEvent) {
        if (!UserSession.getInstance().isAdmin()) {
            throw new SecurityException("You don't have permission to create movies.");
        }

        if(!txtYear.getText().isEmpty() && (txtYear.getText().matches("[0-9]+"))) {
            try {
                movieModel.createMovie(new Movie(-1, txtTitle.getText(), parseInt(txtYear.getText())));
            } catch (Exception e) {
                ErrorDisplay.displayError(e);
            }
        }
        else {
            ErrorDisplay.displayError(new IllegalArgumentException("Please enter a valid year."));
        }
    }

    @FXML
    private void deleteMovie(ActionEvent actionEvent) {
        if (!UserSession.getInstance().isAdmin()) {
            throw new SecurityException("You don't have permission to delete movies.");
        }

        if(selectedMovie != null) {
            try {
                movieModel.deleteMovie(selectedMovie);
            } catch (Exception e) {
                ErrorDisplay.displayError(e);
            }
        }
    }

    @FXML
    private void updateMovie(ActionEvent actionEvent) {
        if (!UserSession.getInstance().isAdmin()) {
            throw new SecurityException("You don't have permission to update movies.");
        }

        if(selectedMovie != null) {
            try {
                Movie updatedMovie = new Movie(selectedMovie.id(), txtTitle.getText(), parseInt(txtYear.getText()));
                movieModel.updateMovie(updatedMovie);
                selectedMovie = updatedMovie;
                movieModel.searchMovie(txtMovieSearch.getText());
                tblMovies.getSelectionModel().select(updatedMovie);
            } catch (Exception e) {
                ErrorDisplay.displayError(e);
            }
        }
    }

    @FXML
    private void openRating(ActionEvent actionEvent) throws Exception {
        Main.loadRating(selectedMovie);
    }

    @FXML
    private void loadMenu(ActionEvent actionEvent) throws Exception {
        UserSession.getInstance().clearSession();
        Main.loadMenu();
    }
}
