module com.example.javaprojectpart1oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.javaprojectpart1oop to javafx.fxml;
    exports application;
    exports application.Test;
}