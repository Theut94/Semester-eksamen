package gui;

import javafx.scene.control.*;
import java.util.Optional;

public class AlertHandler {
    /**
     * Creates a new alert to ask the user for confirmation on their action
     * @param text - the text to be shown to the user
     * @return boolean depending on which button was clicked
     */
    public static boolean confirmationAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,text, ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES)
            return true;
        else return false;
    }

    /**
     * Creates a new alert to inform the user of something
     * @param text - the text to be shown to the user
     */
    public static void informationAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
        alert.show();
    }
}
