package com.hakan.tekir.onlinemusic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField mailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private RadioButton artist;


    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MusicApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
    @FXML
    protected void onRegisterButtonClick(ActionEvent event){
        if(!nameField.getText().trim().isEmpty()&&!lastnameField.getText().trim().isEmpty()&&!mailField.getText().trim().isEmpty()&&!usernameField.getText().trim().isEmpty()&&!passwordField.getText().trim().isEmpty()){
            if(artist.isSelected()){
                Database.register(new Artist(nameField.getText(), lastnameField.getText(), mailField.getText(), usernameField.getText(), passwordField.getText()));
            }
            else {
                Database.register(new User(nameField.getText(), lastnameField.getText(), mailField.getText(), usernameField.getText(), passwordField.getText()));
            }
            try {
                onLoginButtonClick(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText("Lütfen boşlukları doldurun!");
            alert.show();
        }
    }
}
