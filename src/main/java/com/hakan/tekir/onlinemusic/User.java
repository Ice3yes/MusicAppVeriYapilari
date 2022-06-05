package com.hakan.tekir.onlinemusic;

//Bu sınıf kullanıcılar için oluşturulmuştur
//Kullanıcı bilgilerini tutar
public class User {
    protected String name;
    protected String lastname;
    protected String mail;
    protected String username;
    protected String password;

    protected User() {
    }

    public User(String name, String lastname, String mail, String username, String password) {
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
