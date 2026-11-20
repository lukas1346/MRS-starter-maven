package dk.easv.mrs.GUI;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.SQLException;

public class ErrorDisplay {

    public static void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Something went wrong");
        alert.setHeaderText(getErrorMessage(t));

        alert.showAndWait();
    }

    private static String getErrorMessage(Throwable t) {
        if (t instanceof SQLException) {
            return "Database error: " + t.getMessage();
        } else if (t instanceof IOException) {
            return "File error: " + t.getMessage();
        } else {
            return "An unexpected error occurred: " + t.getMessage();
        }
    }
}
