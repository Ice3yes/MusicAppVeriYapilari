package com.hakan.tekir.onlinemusic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


//Bu sınıf Giriş ekranının Controller Class'ıdır.
//Giriş için gerekli komponentleri tutmaktadır.
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        if (!usernameField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()) {
            User user = Database.login(new TempUser(usernameField.getText(), passwordField.getText()));
            if (user != null) {
                if (user.getClass()==Artist.class){
                    MainMenuController.artist= (Artist) user;
                }
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MusicApp.class.getResource("main-menu-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setScene(scene);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText("Lütfen bilgilerinizi doğru girdiğinzden emin olun!");
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText("Lütfen boşlukları doldurun!");
            alert.show();
        }
    }

    @FXML
    protected void onForgotPasswordButtonClick(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı");
        alert.setHeaderText("Şifremi unuttum kısmı geliştirme aşamasındadır.");
        alert.show();
    }

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MusicApp.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
}