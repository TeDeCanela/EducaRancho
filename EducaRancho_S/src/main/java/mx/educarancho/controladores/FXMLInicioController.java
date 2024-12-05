package mx.educarancho.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.educarancho.logica.dominio.EstadosDAO;
import mx.educarancho.logica.dominio.MensajeAlerta;

public class FXMLInicioController implements Initializable {
    
    private String dificultadSeleccionada = "sin seleccionar";
    private String temaSeleccionado = "sin tema";
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    
    public void iniciarJuego(ActionEvent evento) throws IOException{
        
        if(dificultadSeleccionada.equals("sin seleccionar")){
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "No has seleccionado una difultad, ¿Qué tan dificiles quieres las preguntas?",
                    EstadosDAO.ADVERTENCIA));
        }else if(dificultadSeleccionada.equals("facil")){
            
            Node vistaAnterior = (Node) evento.getSource();
            Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
            FXMLLoader cargarVista = new FXMLLoader(getClass().getResource("/fxml/FXMLJuegoPrincipiantes.fxml"));
            Parent vistaRaiz = cargarVista.load();
            FXMLJuegoPrincipiantesController fXMLJuegoPrincipiantesInstancia = cargarVista.getController();
            fXMLJuegoPrincipiantesInstancia.obtenerInformarcion(temaSeleccionado, 0);
            vistaActual.setScene(new Scene(vistaRaiz));
            vistaActual.show();
           
        }else if(dificultadSeleccionada.equals("habil")){
            Node vistaAnterior = (Node) evento.getSource();
            Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
            FXMLLoader cargarVista = new FXMLLoader(getClass().getResource("/fxml/FXMLJuegoHabiles.fxml"));
            Parent vistaRaiz = cargarVista.load();
            FXMLJuegoHabilesController fXMLJuegoHabilesInstancia = cargarVista.getController();
            fXMLJuegoHabilesInstancia.obtenerInformarcion(temaSeleccionado, 0);
            vistaActual.setScene(new Scene(vistaRaiz));
            vistaActual.show();

        }else if(dificultadSeleccionada.equals("dificil")){
            
            Node vistaAnterior = (Node) evento.getSource();
            Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
            FXMLLoader cargarVista = new FXMLLoader(getClass().getResource("/fxml/FXMLJuegoExpertos.fxml"));
            Parent vistaRaiz = cargarVista.load();
            FXMLJuegoExpertosController fXMLJuegoExpertosInstancia = cargarVista.getController();
            fXMLJuegoExpertosInstancia.obtenerInformarcion(temaSeleccionado, 0);
            vistaActual.setScene(new Scene(vistaRaiz));
            vistaActual.show();
 
        }
    }
    
    public void cambiarDificultadFacil(ActionEvent evento){
        dificultadSeleccionada="facil";
    }
    public void cambiarDificultadHabiles(ActionEvent evento){
        dificultadSeleccionada="habil";
    }
    public void cambiarDificultadExpertos(ActionEvent evento){
        dificultadSeleccionada="dificil";
    }
    
    public void seleccionarNoTema(ActionEvent evento){
        temaSeleccionado="sin tema";
    }
    
    public void seleccionarAnimales(ActionEvent evento){
        temaSeleccionado="animales";
    }
    
    public void seleccionarDulces(ActionEvent evento){
        temaSeleccionado="dulces";
    }
    
    public void seleccionarJuegos(ActionEvent evento){
        temaSeleccionado="juegos";
    }
    
    public void seleccionarPaises(ActionEvent evento){
        temaSeleccionado="paises";
    }
    
    public void seleccionarComida(ActionEvent evento){
        temaSeleccionado="comida";
    }
    
    public void seleccionarFlores(ActionEvent evento){
        temaSeleccionado="flores";
    }
    
    public void seleccionarCaricatura(ActionEvent evento){
        temaSeleccionado="caricatura";
    }
    
}
