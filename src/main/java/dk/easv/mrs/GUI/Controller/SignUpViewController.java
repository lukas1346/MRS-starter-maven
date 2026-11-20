package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.GUI.ErrorDisplay;
import dk.easv.mrs.GUI.Main;
import dk.easv.mrs.GUI.Model.UserModel;
import dk.easv.mrs.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

public class SignUpViewController {


    public TextField txtUsername;
    public TextField txtPassword;
    public TextField txtVerifyPassword;
    public Label lblIncorrect;

    private final UserModel userModel;

    public SignUpViewController() {
        userModel = new UserModel();
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
                    User signUpUser = new User(user.name(), userModel.getRole(user));
                    UserSession.getInstance().setCurrentUser(signUpUser);
                    Main.loadMovieView();
                } else {
                    lblIncorrect.setText("Invalid sign up credentials");
            } } catch(Exception e){
                ErrorDisplay.displayError(e);
            }
        }
    }
}
