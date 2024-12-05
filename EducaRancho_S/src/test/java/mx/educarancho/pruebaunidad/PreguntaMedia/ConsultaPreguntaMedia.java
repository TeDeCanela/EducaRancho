package mx.educarancho.pruebaunidad.PreguntaMedia;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.educarancho.logica.implementa.IPreguntaMedia;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaMediaDAO;
import mx.educarancho.logica.dominio.PreguntaMedia;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ConsultaPreguntaMedia {

    private static IPreguntaMedia PREGUNTA_MEDIA_DAO;
    private static PreguntaMedia preguntaParaPruebas;

    @BeforeEach
    public void configuracionGeneral() {
        PREGUNTA_MEDIA_DAO = new PreguntaMediaDAO();
        preguntaParaPruebas = new PreguntaMedia();
        preguntaParaPruebas.setNumero(1);
        preguntaParaPruebas.setPregunta("¿Cuál es la capital de Francia?");
        preguntaParaPruebas.setRespuesta1("Londres");
        preguntaParaPruebas.setRespuesta2("Berlín");
        preguntaParaPruebas.setRespuesta3("París");
        preguntaParaPruebas.setRespuesta4("Madrid");
        preguntaParaPruebas.setRespuesta5("Madrid");
        preguntaParaPruebas.setRespuesta6("Madrid");
        preguntaParaPruebas.setRespuestaCorrecta("París");
        try {
            PREGUNTA_MEDIA_DAO.insertarPreguntaMedia(preguntaParaPruebas);
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaMedia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
@AfterEach
    public void eliminarConfiguracionGeneral() {
        try {
            PREGUNTA_MEDIA_DAO.borrarPreguntaMedia(preguntaParaPruebas.getNumero());
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaMedia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testConsultarPreguntasFacilesExitoso() {
        ArrayList<PreguntaMedia> listaPreguntas = null;
        try {
            listaPreguntas = PREGUNTA_MEDIA_DAO.consultarPreguntasMedia();
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaMedia.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assertions.assertNotNull(listaPreguntas);
        Assertions.assertTrue(listaPreguntas.size() > 0);
        eliminarConfiguracionGeneral();
    }
}
