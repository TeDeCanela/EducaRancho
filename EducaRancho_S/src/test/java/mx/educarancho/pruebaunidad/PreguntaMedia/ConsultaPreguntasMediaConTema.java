/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.educarancho.pruebaunidad.PreguntaMedia;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaMediaDAO;
import mx.educarancho.logica.dominio.PreguntaMedia;
import mx.educarancho.logica.implementa.IPreguntaMedia;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nmh14
 */
public class ConsultaPreguntasMediaConTema {
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
        preguntaParaPruebas.setRespuesta5("Venecia");
        preguntaParaPruebas.setRespuesta6("Roma");
        preguntaParaPruebas.setRespuesta6("Cataluña");
        preguntaParaPruebas.setRespuestaCorrecta("París");
        preguntaParaPruebas.setTema("Paises");
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
            listaPreguntas = PREGUNTA_MEDIA_DAO.consultarPreguntasMediaConTema("Paises");
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaMedia.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assertions.assertNotNull(listaPreguntas);
        eliminarConfiguracionGeneral();
    }

    @Test
    public void testConsultarPreguntasFacilesSinResultados() {
        eliminarConfiguracionGeneral();
        ArrayList<PreguntaMedia> listaPreguntas = new ArrayList<PreguntaMedia>();
        try {
            listaPreguntas = PREGUNTA_MEDIA_DAO.consultarPreguntasMediaConTema("Goblos");
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaMedia.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assertions.assertTrue(listaPreguntas.isEmpty());
    }
}
