package com.hakan.tekir.onlinemusic;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

//Bu sınıf oynatıcının Controller Class'ıdır
//Oynatıcı için gerekli komponentleri tutar
public class PlayerController {

    private final Image pause = new Image(new File("src/main/resources/com/hakan/tekir/onlinemusic/pause.png").toURI().toString());
    private final Image play = new Image(new File("src/main/resources/com/hakan/tekir/onlinemusic/play.png").toURI().toString());
    private MediaPlayer mediaPlayer;
    private boolean playPauseStatus=true;
    MyLoopQueue buttons = new MyLoopQueue();
    public static MyLinkedList<Music> musics=new MyLinkedList<>();

    @FXML
    private ImageView image;

    @FXML
    private Slider durationSlider;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ImageView playPauseImage;

    @FXML
    private VBox vBox;

    @FXML
    private Label currentTime;

    @FXML
    private Label totalTime;


    //Müzik eklemeyi sağlar ve buton oluşturur
    private void addMusic(Music music){
        ImageView imageView = new ImageView(music.getImageFile().toURI().toString());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        Button button=new Button(music.toString(),imageView);
        button.setPrefSize(185,50);
        button.setMnemonicParsing(false);
        button.setAlignment(Pos.CENTER);


        button.setOnAction(event -> {
            try{
                mediaPlayer.stop();
            } catch (NullPointerException ignored){

            }
            image.setImage(new Image(music.getImageFile().toURI().toString()));
            durationSlider.setValue(0);
            double volume = volumeSlider.getValue();
            mediaPlayer=new MediaPlayer(new Media(music.getMusicFile().toURI().toString()));
            mediaPlayer.setVolume(volume);
            sliderAction();
            mediaPlayer.play();
            playPauseImage.setImage(pause);
            playPauseStatus=false;
            buttons.enableAll();
            button.setDisable(true);
        });
        vBox.getChildren().add(button);
        buttons.add(button);
    }

    //Slider'ın ile ses ve süreyi birbirene bağlar
    private void sliderAction() {
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty());
        mediaPlayer.totalDurationProperty().addListener((observableValue, duration, t1) -> {
            durationSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            totalTime.setText(durationFormatter(mediaPlayer.getTotalDuration()));
        });
        durationSlider.valueChangingProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1){
                mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));
                currentTime.setText(durationFormatter(mediaPlayer.getCurrentTime()));
            }
        });
        mediaPlayer.currentTimeProperty().addListener((observableValue, duration, t1) -> {
            if(!durationSlider.isValueChanging()){
                durationSlider.setValue(t1.toSeconds());
                currentTime.setText(durationFormatter(mediaPlayer.getCurrentTime()));
            }
        });
    }

    //Süreyi anlayabileceğimiz formata getirir
    private String durationFormatter(Duration duration){
        return String.format("%.2s:%.2s",duration.toMinutes() < 10 ? "0" + duration.toMinutes() : duration.toMinutes(),(duration.toSeconds()%60 < 10 ? "0" + duration.toSeconds()%60 : duration.toSeconds()%60));
    }

    //Player açılmadan önce gerekli kodları çalıştırır
    public void initialize() {
        MyLinkedList<Music>.Node<Music> cursor=musics.root;
        while (cursor!=null){
            addMusic(cursor.value);
            cursor=cursor.next;
        }
        buttons.cursor.value.fire();
        onPlayPauseButtonClick();
    }


    //Durdur başlat butonunu çalıştırır
    @FXML
    private void onPlayPauseButtonClick(){
        if (playPauseStatus){ //Play
            if(mediaPlayer==null){
                buttons.cursor.value.fire();
            }
            else {
                mediaPlayer.play();
                playPauseImage.setImage(pause);
                playPauseStatus = false;
            }
        }
        else { //Pause
            mediaPlayer.pause();
            playPauseImage.setImage(play);
            playPauseStatus=true;
        }
    }

    //ileri butonunu çalıştırır
    @FXML
    private void onNextButtonClick(){
        buttons.cursor= buttons.cursor.next;
        buttons.cursor.value.fire();
        /*for(int i = 0; i< buttons.size()-1; i++){
            if(buttons.get(i).isDisable()){
                  buttons.get(i).setDisable(false);
                  buttons.get(i+1).fire();
                  break;
            }
        }*/
    }
    //Geri butonunu çalıştırır
    @FXML
    private void onPreviousButtonClick(){
        buttons.cursor= buttons.cursor.pre;
        buttons.cursor.value.fire();
        /*for(int i = 1; i< buttons.size(); i++){
            if(buttons.get(i).isDisable()){
                buttons.get(i).setDisable(false);
                buttons.get(i-1).fire();
                break;
            }
        }*/
    }

    //Dosya ismini alır
    private static String getFileName(File file) {
        String fileName = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fileName = "";
        }

        return fileName;

    }
}
