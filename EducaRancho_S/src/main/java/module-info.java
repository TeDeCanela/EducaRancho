module mx.educarancho_s {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires javafx.graphics;


    opens mx.educarancho.controladores to javafx.fxml;
    exports mx.educarancho.controladores;
    exports mx.educarancho.logica.dominio;
    exports mx.educarancho.logica.concreta;
    requires mysql.connector.java;
    requires javafx.graphicsEmpty;
}
