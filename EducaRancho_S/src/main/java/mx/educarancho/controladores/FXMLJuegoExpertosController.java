package mx.educarancho.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaDificilDAO;
import mx.educarancho.logica.dominio.EstadosDAO;
import mx.educarancho.logica.dominio.MensajeAlerta;
import mx.educarancho.logica.dominio.PreguntaDificil;

public class FXMLJuegoExpertosController implements Initializable {

    @FXML
    Label lblPregunta;
    @FXML
    TextField txtNumero;
    @FXML
    TextField txtUnidades;

    private ArrayList<PreguntaDificil> listaPreguntasDificiles;
    private ArrayList<Integer> listaPreguntasSeleccionadas = new ArrayList<>();
    ;
    private PreguntaDificilDAO preguntaDAO = new PreguntaDificilDAO();
    private String respuestaCorrectaNumerica;
    private String respuestaCorrectaUnidad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void obtenerInformarcion(String tema, int tiempo) throws IOException {
        try {
            if (tema.equals("sin tema")) {
                listaPreguntasDificiles = preguntaDAO.consultarPreguntasDificiles();
                irSiguientePregunta(null);
            } else {
                listaPreguntasDificiles = preguntaDAO.consultarPreguntasDificilConTema(tema);
                irSiguientePregunta(null);
            }
        } catch (ExcepcionDAO ex) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    ex.getMessage(),
                    EstadosDAO.ADVERTENCIA));
        }
    }

    @FXML
    public void irSiguientePregunta(ActionEvent evento) throws IOException {
        try {

            PreguntaDificil pregunta = preguntaDAO.consultarPreguntaDificilSinRepetir(listaPreguntasDificiles, listaPreguntasSeleccionadas);
            listaPreguntasSeleccionadas.add(pregunta.getNumero());
            actualizarPregunta(pregunta);
            respuestaCorrectaNumerica = pregunta.getRespuestNumerica();
            respuestaCorrectaUnidad = pregunta.getRespuestaUnidad();
            

        } catch (ExcepcionDAO ex) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    ex.getMessage(),
                    EstadosDAO.EXITOSO));
            Node vistaAnterior = (Node) evento.getSource();
            Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
            FXMLLoader cargarVista = new FXMLLoader(getClass().getResource("/fxml/FXMLInicio.fxml"));
            Parent vistaRaiz = cargarVista.load();

            vistaActual.setScene(new Scene(vistaRaiz));
            vistaActual.show();
            listaPreguntasSeleccionadas.clear();

        }
    }
    
    private void actualizarPregunta(PreguntaDificil pregunta) {
        lblPregunta.setText(pregunta.getPregunta());
        txtNumero.clear();
        txtUnidades.clear();
    }

    @FXML
    public void regresar(ActionEvent evento) throws IOException {
        Node vistaAnterior = (Node) evento.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargarVista = new FXMLLoader(getClass().getResource("/fxml/FXMLInicio.fxml"));
        Parent vistaRaiz = cargarVista.load();

        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
        listaPreguntasSeleccionadas.clear();
    }

    @FXML
    public void calificar(ActionEvent evento) {
        String respuestaIngresadaNumero = txtNumero.getText();
        String respuestaIngresadaUnidad = txtUnidades.getText();

        if (respuestaIngresadaNumero.equals(respuestaCorrectaNumerica) && respuestaIngresadaUnidad.equalsIgnoreCase(respuestaCorrectaUnidad)) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "¡Respuesta correcta, muy bien!",
                    EstadosDAO.EXITOSO));
        } else {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Respuesta incorrecta. La respuesta correcta es: "
                    + respuestaCorrectaNumerica + " " + respuestaCorrectaUnidad,
                    EstadosDAO.EXITOSO));
        }
    }

    @FXML
    public void reintentar(ActionEvent evento) throws IOException {
        txtNumero.clear();
        txtUnidades.clear();
    }

    @FXML
    public void solicitarAyuda(ActionEvent evento) throws IOException {
        App.eventoCambiarVista(evento, "/mx/fxml/FXMLInicio.fxml");
    }

}
