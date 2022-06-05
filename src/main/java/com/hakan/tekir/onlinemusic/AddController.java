package com.hakan.tekir.onlinemusic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

//Bu sınıf müzik eklemek için kullanılmaktadır
public class AddController {

    public static Artist artist;
    private String name;
    private File musicFile;
    private File imageFile;
    private int page=0;

    @FXML
    private Label label;

    @FXML
    private TextField textField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button fileButton;

    @FXML
    private void onFileButtonClick(ActionEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if(page==1) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter("PNG dosyası (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(png);
            File oldImageFile=imageFile;
            imageFile = fileChooser.showOpenDialog(stage);
            if(imageFile==null){
                imageFile=oldImageFile;
            }
            textField.setText(imageFile.getName());
            imageView.setImage(new Image(imageFile.toURI().toString()));
        }
        else if(page==2){
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter mp3 = new FileChooser.ExtensionFilter("MP3 dosyası (*.mp3)", "*.mp3");
            fileChooser.getExtensionFilters().add(mp3);
            File oldMusicFile=musicFile;
            musicFile = fileChooser.showOpenDialog(stage);
            if(musicFile==null){
                musicFile=oldMusicFile;
            }
            else textField.setText(musicFile.getName());
        }
    }

    @FXML
    private void onNextButtonClick(ActionEvent event) {
        if (page==0) {
            if(textField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText("Lütfen müzik ismini girin!");
                alert.show();
            }
            else{
                name=textField.getText();
                setupPage1();
            }
        }
        else if(page==1){
            setupPage2();
        }
        else if(page==2){
            if(musicFile==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText("Lütfen dosya seçin!");
                alert.show();
            }
            else {
                Music music = new Music(name, artist, musicFile, imageFile);
                music.write();
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bilgi");
                alert.setHeaderText("Müzik başarıyla eklenmiştir.");
                alert.show();
            }
        }
    }

    @FXML
    private void onPreviousButtonClick(){
        if (page==1){
            setupPage0();
        }
        else if (page==2){
            setupPage1();
        }
    }

    private void setupPage0(){
        page=0;
        label.setText("Müzik ismi");
        textField.setText(name);
        fileButton.setVisible(false);
        imageView.setVisible(false);
        previousButton.setDisable(true);
        textField.setEditable(true);
    }

    private void setupPage1(){
        page=1;
        label.setText("Lütfen resim dosyasını girin.");
        nextButton.setText("İleri");
        imageView.setImage(new Image(imageFile.toURI().toString()));
        textField.setText(imageFile.getName());
        imageView.setVisible(true);
        textField.setEditable(false);
        fileButton.setVisible(true);
        previousButton.setDisable(false);
    }

    private void setupPage2(){
        page=2;
        label.setText("Lütfen müzik dosyasını girin.");
        if(musicFile==null) textField.setText("");
        else textField.setText(musicFile.getName());
        nextButton.setText("Müziği ekle");
        imageView.setVisible(false);
    }

    public void initialize(){
        imageFile= new File("src/main/resources/com/hakan/tekir/onlinemusic/music.png");
    }
}
