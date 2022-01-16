package com.hakan.tekir.onlinemusic;

import java.io.File;
import java.util.ArrayList;

public class Artist extends User{

    private ArrayList<Music> musics = new ArrayList<>();

    public Artist(String name){
        this.name=name;
        new File(System.getenv("APPDATA")+"/MusicApp/musics/"+name).mkdirs();
    }

    public Artist(String name, String lastname, String mail, String username, String password) {
        super(name, lastname, mail, username, password);
        new File(System.getenv("APPDATA")+"/MusicApp/musics/"+name).mkdirs();
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void addMusic(Music music){
        musics.add(music);
    }
}
