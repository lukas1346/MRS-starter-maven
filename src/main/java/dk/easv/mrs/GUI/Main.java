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

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/views/MenuView.fxml")));
        stage.setTitle("MRS");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
        primaryStage = stage;
    }

    public static void loadRating(Movie movie) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/RatingView.fxml"));
        Parent root = fxmlLoader.load();
        RatingViewController controller = fxmlLoader.getController();
        controller.initializeModel(movie);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Rating");
        stage.centerOnScreen();
        stage.show();
    }

    public static void loadLogin() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/LoginView.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.show();
    }

    public static void loadSignUp() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/SignUpView.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.setTitle("Sign Up");
        stage.centerOnScreen();
        stage.show();
    }

    public static void loadMovieView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/MovieView.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.setTitle("MRS");
        stage.centerOnScreen();
        stage.show();
    }


    public static void loadMenu() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/MenuView.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.setTitle("MRS");
        stage.centerOnScreen();
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
