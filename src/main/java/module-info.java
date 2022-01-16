module com.hakan.tekir.onlinemusic {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;
    requires javafx.media;


    opens com.hakan.tekir.onlinemusic to javafx.fxml;
    exports com.hakan.tekir.onlinemusic;
}