import domain.Clasament;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RoundController extends UnicastRemoteObject implements IObserver, Serializable {
    private IServices server;
    private String username;
    private Stage stage;
    private Integer gameID;
    @FXML Text ltrsTxt;
    @FXML Text pointsTxt;
    @FXML TextField wordField;

    public RoundController() throws RemoteException {
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

    @Override
    public void enableStart() throws RemoteException {
    }

    @Override
    public void disableStart() throws RemoteException {

    }

    @Override
    public void newRound(Integer id, String letter, Integer points) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                ltrsTxt.setText(letter);
                pointsTxt.setText(points.toString() + " pct");

                wordField.setText("");
            }
        });
        this.gameID = id;
    }

    @Override
    public void finalClasament(Clasament clasament) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Stage primaryStage=new Stage();

                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/final.fxml"));
                Parent root= null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FinalController finalController = loader.getController();
                primaryStage.setScene(new Scene(root));
                primaryStage.setTitle("Player " + username);
                primaryStage.show();
                finalController.setService(server);
                finalController.setUsername(username);
                finalController.setStage(primaryStage);
                finalController.finalClasament(clasament);
                server.changeClient(username, finalController);
                stage.getScene().getWindow().hide();
            }
        });
    }

    public void sendResponse(ActionEvent ae){
        String word = wordField.getText().toLowerCase();

        server.sendResponse(username, gameID, word);
    }
}