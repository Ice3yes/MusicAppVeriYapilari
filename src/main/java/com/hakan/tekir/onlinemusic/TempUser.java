package com.hakan.tekir.onlinemusic;

//Bu sınıf geçici kullanıcılar için kullanılır
public class TempUser extends User{

    public TempUser(String username,String password){
        this.username=username;
        this.password=password;
    }
}
