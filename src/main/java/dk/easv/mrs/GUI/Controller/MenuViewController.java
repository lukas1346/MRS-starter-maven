package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.GUI.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuViewController {



    @FXML
    private void loginClicked(ActionEvent actionEvent) throws Exception {
        Main.loadLogin();
    }

    @FXML
    private void signUpClicked(ActionEvent actionEvent) throws Exception {
        Main.loadSignUp();
    }
}
