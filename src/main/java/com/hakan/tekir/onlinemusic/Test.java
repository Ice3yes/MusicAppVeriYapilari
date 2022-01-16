package com.hakan.tekir.onlinemusic;


public class Test {

    public static void main(String[] args) {
        User user = new User("name","lastname","mail","username","password");
        Artist artist = new Artist("name","lastname","mail","username","password");
        Database.register(user);
    }
}
