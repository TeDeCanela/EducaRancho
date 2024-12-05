package mx.educarancho.pruebaunidad.PreguntaFacil;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaFacilDAO;
import mx.educarancho.logica.dominio.PreguntaFacil;
import mx.educarancho.logica.implementa.IPreguntaFacil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nmh14
 */
public class consultarPreguntasFacilesConTemaTest {
    private static IPreguntaFacil PREGUNTA_FACIL_DAO;
    private static PreguntaFacil preguntaParaPruebas;

    @BeforeEach
    public void configuracionGeneral() {
        PREGUNTA_FACIL_DAO = new PreguntaFacilDAO();
        preguntaParaPruebas = new PreguntaFacil();
        preguntaParaPruebas.setNumero(1);
        preguntaParaPruebas.setPregunta("¿Cuál es la capital de Francia?");
        preguntaParaPruebas.setRespuesta1("Londres");
        preguntaParaPruebas.setRespuesta2("Berlín");
        preguntaParaPruebas.setRespuesta3("París");
        preguntaParaPruebas.setRespuesta4("Madrid");
        preguntaParaPruebas.setTema("Paises");
        preguntaParaPruebas.setRespuestaCorrecta("París");
        try {
            PREGUNTA_FACIL_DAO.insertarPreguntaFacil(preguntaParaPruebas);
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaFacil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
@AfterEach
    public void eliminarConfiguracionGeneral() {
        try {
            PREGUNTA_FACIL_DAO.borrarPreguntaFacil(preguntaParaPruebas.getNumero());
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaFacil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testConsultarPreguntasFacilesExitoso() {
        ArrayList<PreguntaFacil> listaPreguntas = null;
        try {
            listaPreguntas = PREGUNTA_FACIL_DAO.consultarPreguntasFacilesConTema("Paises");
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaFacil.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assertions.assertNotNull(listaPreguntas);
        Assertions.assertTrue(listaPreguntas.size() > 0);
        eliminarConfiguracionGeneral();
    }

    @Test
    public void testConsultarPreguntasFacilesSinResultados() {
        eliminarConfiguracionGeneral();
        ArrayList<PreguntaFacil> listaPreguntas = new ArrayList<PreguntaFacil>();
        try {
            listaPreguntas = PREGUNTA_FACIL_DAO.consultarPreguntasFacilesConTema("Juegos");
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaFacil.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assertions.assertTrue(listaPreguntas.isEmpty());
    }

}
