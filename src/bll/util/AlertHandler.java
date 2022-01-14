package bll.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHandler
{

    public static boolean confirmationAlert(String text)
    {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,text, ButtonType.YES,ButtonType.NO);
    Optional<ButtonType> result = alert.showAndWait();
    if(result.get() == ButtonType.YES)
        return true;
    else return false;
    }

    public static void informationAlert(String text)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
        alert.showAndWait();
    }
}
