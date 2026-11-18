package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.GUI.Main;
import dk.easv.mrs.GUI.Model.UserModel;
import dk.easv.mrs.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SignUpViewController {


    public TextField txtUsername;
    public TextField txtPassword;
    public TextField txtVerifyPassword;
    public Label lblIncorrect;

    private UserModel userModel;

    public SignUpViewController() {
        userModel = new UserModel();
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
    private void backClicked(ActionEvent actionEvent) throws Exception {
        Main.loadMenu();
    }

    public void signUpClicked(ActionEvent actionEvent) throws Exception {
        if(!txtUsername.getText().isEmpty() && Objects.equals(txtPassword.getText(), txtVerifyPassword.getText()) && !txtPassword.getText().isEmpty())
        {
            User user = new User(txtUsername.getText(), txtPassword.getText());
            try {
                if (userModel.register(user)) {
                    UserSession.getInstance().setCurrentUser(user);
                    Main.loadMovieView();
                } else {
                    lblIncorrect.setText("Invalid sign up credentials");
            } } catch(Exception e){
                displayError(e);
            }
        }
    }
}
