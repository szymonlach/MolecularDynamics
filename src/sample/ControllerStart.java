package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStart implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void wykres(ActionEvent actionEvent) {
        FXMLLoader loader = new
                FXMLLoader(getClass().getResource("fxml/sample.fxml"));
        try {
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dynamika molekularna - wykresy");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void animacja(ActionEvent actionEvent) {
        FXMLLoader loader = new
                FXMLLoader(getClass().getResource("fxml/animacja.fxml"));
        try {
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dynamika molekularna - animacja");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
