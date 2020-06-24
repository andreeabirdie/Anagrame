import domain.Clasament;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class FinalController extends UnicastRemoteObject implements IObserver, Serializable {
    private IServices server;
    private String username;
    private Stage stage;
    @FXML
    Text firstPlace;
    @FXML
    Text secondPlace;
    @FXML
    Text thirdPlace;

    public FinalController() throws RemoteException {
    }


    public void setService(IServices server) {
        this.server = server;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {

    }

    @FXML
    public void logout() throws Exception {
        try {
            server.logout(username, this);
            exit(0);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }


    public void alert(String err) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message!");
        message.setContentText(err);
        message.showAndWait();
    }

    @Override
    public void enableStart() {
    }

    @Override
    public void disableStart() throws RemoteException {
    }

    @Override
    public void newRound(Integer id, String letter, Integer points) {
    }

    @Override
    public void finalClasament(Clasament clasament) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String first = "1. " + clasament.getPlayer1() + ": "+ clasament.getPoints1() + " pct";
                String second = "2. " + clasament.getPlayer2() + ": "+ clasament.getPoints2() + " pct";
                String third = "3. " + clasament.getPlayer3() + ": "+ clasament.getPoints3() + " pct";
                int i = 1;
                firstPlace.setText(first);
                secondPlace.setText(second);
                thirdPlace.setText(third);
            }

        });
    }
}