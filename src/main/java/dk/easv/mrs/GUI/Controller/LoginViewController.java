package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.GUI.Main;
import dk.easv.mrs.GUI.Model.UserModel;
import dk.easv.mrs.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginViewController {


    public TextField txtUsername;
    public TextField txtPassword;
    public Label lblIncorrect;

    private UserModel userModel;

    public LoginViewController() {
        userModel = new UserModel();
    }

    @FXML
    private void backClicked(ActionEvent actionEvent) throws Exception {
        Main.loadMenu();
    }

    public void loginClicked(ActionEvent actionEvent) throws Exception {
        User user = new User(txtUsername.getText(), txtPassword.getText());
        if(userModel.login(user)) {
            UserSession.getInstance().setCurrentUser(user);
            Main.loadMovieView();
        }
        else {
            lblIncorrect.setText("Credentials incorrect");
        }
    }
}
