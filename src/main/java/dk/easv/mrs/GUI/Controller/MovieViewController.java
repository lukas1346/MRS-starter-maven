package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public TextField txtTitle;
    public TextField txtYear;
    public TableView<Movie> tblMovies;
    public TableColumn<Movie, Integer> colId;
    public TableColumn<Movie, String> colTitle;
    public TableColumn<Movie, String> colYear;
    private MovieModel movieModel;
    private Movie selectedMovie;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colTitle.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        colYear.setCellValueFactory(
                new PropertyValueFactory<>("year")
        );


        tblMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.scheduleMovieSearch(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });


        tblMovies.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                selectedMovie = newValue; // Set the selected song
                txtTitle.setText(selectedMovie.getTitle());
                txtYear.setText(String.valueOf(selectedMovie.getYear()));
                System.out.println("Selected Movie: " + selectedMovie);
            }
        });
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void createMovie(ActionEvent actionEvent) throws Exception {
        if(!txtYear.getText().isEmpty() && (txtYear.getText().matches("[0-9]+"))) {
            movieModel.createMovie(new Movie(-1, parseInt(txtYear.getText()), txtTitle.getText()));
        }
        else {
            displayError(new IllegalArgumentException("Please enter a valid year."));
        }
    }

    public void deleteMovie(ActionEvent actionEvent) throws Exception {
        if(selectedMovie != null) {
            movieModel.deleteMovie(selectedMovie);
        }
    }

    public void updateMovie(ActionEvent actionEvent) throws Exception {
        if(selectedMovie != null) {
            selectedMovie.setTitle(txtTitle.getText());
            selectedMovie.setYear(parseInt(txtYear.getText()));
            movieModel.updateMovie(selectedMovie);
            movieModel.scheduleMovieSearch(txtMovieSearch.getText());
        }
    }
}
