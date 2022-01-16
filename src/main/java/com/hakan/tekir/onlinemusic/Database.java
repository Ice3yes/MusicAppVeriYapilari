package com.hakan.tekir.onlinemusic;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;

public class Database {
    private static final String jbdcURL = "jdbc:mysql://localhost:3306/online-music";
    private static final String dbuser="root";
    private static final String dbpassword="asdfg12345";
    private static Connection connection;
    private static ResultSet resultSet;
    public static void register(User user){
        PreparedStatement psCheckUserExists;
        PreparedStatement psInsert;
        try {
            connection = DriverManager.getConnection(jbdcURL, dbuser, dbpassword);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1,user.getUsername());
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText("Başka bir kullanıcı adı girin!");
                alert.show();
            }
            else {
                psInsert = connection.prepareStatement("INSERT INTO users (name, lastname, mail, username, password) VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1,user.name);
                psInsert.setString(2,user.lastname);
                psInsert.setString(3,user.mail);
                psInsert.setString(4,user.username);
                psInsert.setString(5,user.password);
                psInsert.execute();
            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void register(Artist artist){
        PreparedStatement psCheckUserExists;
        PreparedStatement psInsert;
        try {
            connection = DriverManager.getConnection(jbdcURL, dbuser, dbpassword);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, artist.getUsername());
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("Başka bir kullanıcı adı girin!");
            }
            else {
                psInsert = connection.prepareStatement("INSERT INTO users (name, lastname, mail, username, password, is_artist) VALUES (?, ?, ?, ?, ?, ?)");
                psInsert.setString(1, artist.name);
                psInsert.setString(2, artist.lastname);
                psInsert.setString(3, artist.mail);
                psInsert.setString(4, artist.username);
                psInsert.setString(5, artist.password);
                psInsert.setBoolean(6,true);
                psInsert.execute();
            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User login(User user){
        PreparedStatement preparedStatement;
        try{
            connection = DriverManager.getConnection(jbdcURL, dbuser, dbpassword);
            preparedStatement = connection.prepareStatement("SELECT name, lastname, mail, password, is_artist FROM users WHERE username = ?");
            preparedStatement.setString(1,user.getUsername());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String retrievedpassword = resultSet.getString("password");
                if (retrievedpassword.equals(user.getPassword())) {
                    String retrievedName = resultSet.getString("name");
                    String retrievedLastname = resultSet.getString("lastname");
                    String retrievedMail = resultSet.getString("mail");
                    boolean retrievedBool = resultSet.getBoolean("is_artist");
                    connection.close();
                    if (retrievedBool){
                        return new Artist(retrievedName, retrievedLastname, retrievedMail, user.username, user.password);
                    }
                    return new User(retrievedName, retrievedLastname, retrievedMail, user.username, user.password);
                } else {
                    connection.close();
                    return null;
                }
            }
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
