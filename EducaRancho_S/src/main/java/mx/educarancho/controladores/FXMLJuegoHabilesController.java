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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaMediaDAO;
import mx.educarancho.logica.dominio.EstadosDAO;
import mx.educarancho.logica.dominio.MensajeAlerta;
import mx.educarancho.logica.dominio.PreguntaMedia;

public class FXMLJuegoHabilesController implements Initializable {

    @FXML
    Label lblPregunta;
    @FXML
    RadioButton rbtnRespuesta1;
    @FXML
    RadioButton rbtnRespuesta2;
    @FXML
    RadioButton rbtnRespuesta3;
    @FXML
    RadioButton rbtnRespuesta4;
    @FXML
    RadioButton rbtnRespuesta5;
    @FXML
    RadioButton rbtnRespuesta6;
    @FXML
    ToggleGroup grupoRespuestas;

    private ArrayList<PreguntaMedia> listaPreguntasMedia;
    private ArrayList<Integer> listaPreguntasSeleccionadas = new ArrayList<>();
    private String respuestaCorrecta;
    private PreguntaMediaDAO preguntaDAO = new PreguntaMediaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grupoRespuestas = new ToggleGroup();
        rbtnRespuesta1.setToggleGroup(grupoRespuestas);
        rbtnRespuesta2.setToggleGroup(grupoRespuestas);
        rbtnRespuesta3.setToggleGroup(grupoRespuestas);
        rbtnRespuesta4.setToggleGroup(grupoRespuestas);
        rbtnRespuesta5.setToggleGroup(grupoRespuestas);
        rbtnRespuesta6.setToggleGroup(grupoRespuestas);

        grupoRespuestas.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedButton = (RadioButton) newValue;
                validarRespuesta(selectedButton.getText());
            }
        });
    }

    public void obtenerInformarcion(String tema, int tiempo) throws IOException {
        try {
            if (tema.equals("sin tema")) {
                listaPreguntasMedia = preguntaDAO.consultarPreguntasMedia();
                irSiguientePregunta(null);
            } else {
                listaPreguntasMedia = preguntaDAO.consultarPreguntasMediaConTema(tema);
                irSiguientePregunta(null);
            }
        } catch (ExcepcionDAO ex) {
            lblPregunta.setText("Error al cargar las preguntas.");
        }
    }

    @FXML
    public void irSiguientePregunta(ActionEvent evento) throws IOException {
        try {

            PreguntaMedia pregunta = preguntaDAO.consultarPreguntaMediaSinRepetir(listaPreguntasMedia, listaPreguntasSeleccionadas);
            listaPreguntasSeleccionadas.add(pregunta.getNumero()); 
            actualizarPregunta(pregunta);
            respuestaCorrecta = pregunta.getRespuestaCorrecta();
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

    private void actualizarPregunta(PreguntaMedia pregunta) {
        lblPregunta.setText(pregunta.getPregunta());
        rbtnRespuesta1.setText(pregunta.getRespuesta1());
        rbtnRespuesta2.setText(pregunta.getRespuesta2());
        rbtnRespuesta3.setText(pregunta.getRespuesta3());
        rbtnRespuesta4.setText(pregunta.getRespuesta4());
        rbtnRespuesta5.setText(pregunta.getRespuesta5());
        rbtnRespuesta6.setText(pregunta.getRespuesta6());
        grupoRespuestas.selectToggle(null);
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

    private void validarRespuesta(String respuestaSeleccionada) {
        if (respuestaSeleccionada.equals(respuestaCorrecta)) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "¡Respuesta correcta, muy bien!",
                    EstadosDAO.EXITOSO));
        } else {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Respuesta incorrecta. La respuesta correcta era: " + respuestaCorrecta,
                    EstadosDAO.EXITOSO));
        }
    }

    @FXML
    public void reintentar(ActionEvent evento) throws IOException {
        grupoRespuestas.selectToggle(null);
    }

    @FXML
    public void solicitarAyuda(ActionEvent evento) throws IOException {
        App.eventoCambiarVista(evento, "/mx/fxml/FXMLInicio.fxml");
    }
}
