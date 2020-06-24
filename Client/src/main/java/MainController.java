import domain.Clasament;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static java.lang.System.exit;

public class MainController extends UnicastRemoteObject implements IObserver, Serializable {
    private IServices server;
    private String username;
    private Stage stage;
    @FXML Button startBtn;
    @FXML
    Text waitingTxt;

    public MainController() throws RemoteException {
    }


    public void setService(IServices server) {
        this.server = server;
        waitingTxt.setTextAlignment(TextAlignment.CENTER);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize(){

    }

//    @FXML
//    public void logout() throws Exception {
//        try{
//            server.logout(username, this);
//            exit(0);
//        }
//        catch (Exception e){
//            alert(e.getMessage());
//        }
//    }

    @FXML
    public void start() throws IOException {
        server.startGame();
    }


    public void alert(String err){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message!");
        message.setContentText(err);
        message.showAndWait();
    }

    @Override
    public void enableStart() {
        startBtn.setDisable(false);
        waitingTxt.setText("Apasati start");

    }

    @Override
    public void disableStart() throws RemoteException {
        startBtn.setDisable(true);
    }

    @Override
    public void newRound(Integer id, String s, Integer points) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Stage primaryStage=new Stage();

                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/round.fxml"));
                Parent root= null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RoundController roundController = loader.getController();
                primaryStage.setScene(new Scene(root));
                primaryStage.setTitle("Player " + username);
                primaryStage.show();
                roundController.setService(server);
                roundController.setUsername(username);
                roundController.setStage(primaryStage);
                roundController.newRound(id, s, points);
                server.changeClient(username, roundController);
                stage.getScene().getWindow().hide();
            }
        });

    }

    @Override
    public void finalClasament(Clasament c) {
    }

}
