package com.hakan.tekir.onlinemusic;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//Bu sınıf müzikleri tutmaktadır.
public class Music {

    private String name;
    private File musicFile;
    private File imageFile;
    private Artist artist;

    public String getName() {
        return name;
    }

    public File getMusicFile() {
        return musicFile;
    }

    public File getImageFile() {
        return imageFile;
    }

    protected Music(){

    }

    public Music(String name, Artist artist, File musicFile, File imageFile) {
        this.name = name;
        this.artist=artist;
        this.musicFile =musicFile;
        this.imageFile=imageFile;
        artist.addMusic(this);
    }

    public Artist getArtist() {
        return artist;
    }

    //Bu method müziği kaydetmek için kullanılmaktadır.
    public void write(){
        try {
            new File(System.getenv("APPDATA")+"/MusicApp/musics/"+artist.getName()+"/"+name).mkdirs();
            Files.copy(Path.of(musicFile.toURI()), Path.of(System.getenv("APPDATA")+"/MusicApp/musics/"+artist.getName()+"/"+name+"/music.mp3"));
            Files.copy(Path.of(imageFile.toURI()), Path.of(System.getenv("APPDATA")+"/MusicApp/musics/"+artist.getName()+"/"+name+"/image.png"));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText("Lütfen müzik ismini değiştirin!");
            alert.show();
        }
    }

    @Override
    public String toString() {
        return name+"\n"+artist.getName();
    }
}
