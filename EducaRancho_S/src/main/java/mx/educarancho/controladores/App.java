package mx.educarancho.controladores;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class App extends Application {

    private static Scene escena;
    private static Stage vistaPrimaria;

    @Override
    public void start(Stage stage) throws IOException {
        escena = new Scene(loadFXML("/fxml/FXMLInicio"), 640, 480);
        stage.setScene(escena);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        escena.setRoot(loadFXML(fxml));
    }
   
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void cambioDeVista(String url) throws IOException {
        App.setRoot(url);
    }
    
    public static void cambiarVista(Stage vista, String rutaFXML) throws IOException {
        FXMLLoader cargadorArchivo = new FXMLLoader(App.class.getResource(rutaFXML));
        Parent vistaRaiz = cargadorArchivo.load();
        vista.setScene(new Scene(vistaRaiz));
        vista.show();
    }
    
    public static void eventoCambiarVista(ActionEvent evento, String rutaFXML) throws IOException {
        Node vistaAnterior = (Node) evento.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorArchivo = new FXMLLoader(App.class.getResource(rutaFXML));
        Parent vistaRaiz = cargadorArchivo.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
    }
}