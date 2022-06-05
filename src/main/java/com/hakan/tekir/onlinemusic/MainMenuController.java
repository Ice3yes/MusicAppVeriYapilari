package com.hakan.tekir.onlinemusic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


//Bu sınıf ana menünün Controller Class'ıdır.
//Ana menünün kompenentlerini tutmaktadır
public class MainMenuController {

    public static Artist artist;
    private MyQueue<Music> musicQueue = new MyQueue<>();

    @FXML
    private VBox vBox0;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    public void initialize(){
        if (artist!=null){
            Button button = new Button("Müzik ekle");
            button.setPrefWidth(150);
            button.setOnAction(event -> {
                AddController.artist=artist;
                FXMLLoader fxmlLoader = new FXMLLoader(MusicApp.class.getResource("add-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 300, 300);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage= new Stage();
                stage.getIcons().add(new Image("file:src/main/resources/com/hakan/tekir/onlinemusic/music.png"));
                stage.setResizable(false);
                stage.setTitle("Müzik ekle");
                stage.setScene(scene);
                stage.show();
            });
            vBox1.getChildren().add(2,button);
        }
        File folder = new File(System.getenv("APPDATA")+"/MusicApp/musics");
        File[] listOfArtists = folder.listFiles();
        if (listOfArtists != null) {
            MyStack<File> myStack = new MyStack<>();
            for (File artist : listOfArtists) {
                myStack.push(artist);
            }
            while (!myStack.isEmpty()) {
                File newArtist = myStack.pop();
                String artistName= newArtist.getName();
                Artist artistObject=new Artist(artistName);
                File[] listOfMusics = newArtist.listFiles();
                if (listOfMusics != null) {
                    for (File music : listOfMusics) {
                        String musicName= music.getName();
                        File musicFile=new File(music+"/music.mp3");
                        File imageFile=new File(music+"/image.png");
                        Music musicObject = new Music(musicName,artistObject,musicFile,imageFile);
                        createButtonForMusics(musicObject);
                    }
                }
            }
        }
    }

    private Button createButton(Music music){
        ImageView imageView = new ImageView(music.getImageFile().toURI().toString());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        Button button=new Button(music.toString(),imageView);
        button.setPrefSize(185,50);
        button.setMnemonicParsing(false);
        button.setAlignment(Pos.CENTER);
        return button;
    }

    private void createButtonForMusics(Music music){
        Button button = createButton(music);
        button.setOnAction(event -> {
            musicQueue.add(music);
            createButtonForPlaylist(music);
            vBox0.getChildren().remove(button);
        });
        vBox0.getChildren().add(button);
    }

    private void createButtonForPlaylist(Music music){
        Button button = createButton(music);
        button.setOnAction(event -> {
            musicQueue.remove();
            createButtonForMusics(music);
            vBox2.getChildren().remove(button);
        });
        vBox2.getChildren().add(button);
    }

    @FXML
    public void onOpenPlayerButtonClick(ActionEvent event) throws IOException {
        if(musicQueue.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText("Lütfen oynatma listesine müzik ekleyin!");
            alert.show();
        }
        else {
            MyQueue<Music>.Node<Music> cursor=musicQueue.root;
            while (cursor!=null){
                PlayerController.musics.add(cursor.value);
                cursor=cursor.next;
            }
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MusicApp.class.getResource("player-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
        }
    }

    @FXML
    public void onLogoutButtonClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MusicApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }

    @FXML
    public void onQuitButtonClick(){
        Platform.exit();
    }
}
