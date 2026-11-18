package dk.easv.mrs.GUI;
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Controller.RatingViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/views/MovieView.fxml")));
        primaryStage.setTitle("MRS");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void loadRating(Movie movie) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/RatingView.fxml"));
        Parent root = fxmlLoader.load();
        RatingViewController controller = fxmlLoader.getController();
        controller.initializeModel(movie);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Rating");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
