
package mx.educarancho.pruebaunidad.PreguntaDificil;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaDificilDAO;
import mx.educarancho.logica.dominio.PreguntaDificil;
import mx.educarancho.logica.implementa.IPreguntaDificil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nmh14
 */
public class consultarPreguntasDificilConTemaTest {
     private static IPreguntaDificil PREGUNTA_DIFICIL_DAO;
    private static PreguntaDificil preguntaParaPruebas;

    @BeforeEach
    public void configuracionGeneral() {
        PREGUNTA_DIFICIL_DAO = new PreguntaDificilDAO();
        preguntaParaPruebas = new PreguntaDificil();
        preguntaParaPruebas.setNumero(1);
        preguntaParaPruebas.setPregunta("¿Cuántos niños tenemos en una escuela si hay 2 salones y en cada uno hay 30 niños?");
        preguntaParaPruebas.setRespuestNumerica("60");
        preguntaParaPruebas.setRespuestaUnidad("niños");
        preguntaParaPruebas.setTema("escuela");
        preguntaParaPruebas.setArchivoAyuda("/archivoDificilAyuda/pregunta1.pdf");
        try {
            PREGUNTA_DIFICIL_DAO.insertarPreguntaDificil(preguntaParaPruebas);
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaDificil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
@AfterEach
    public void eliminarConfiguracionGeneral() {
        try {
            PREGUNTA_DIFICIL_DAO.borrarPreguntaDificil(preguntaParaPruebas.getNumero());
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaDificil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testConsultarPreguntasFacilesExitoso() {
        ArrayList<PreguntaDificil> listaPreguntas = null;
        try {
            listaPreguntas = PREGUNTA_DIFICIL_DAO.consultarPreguntasDificilConTema("escuela");
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaDificil.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assertions.assertNotNull(listaPreguntas);
        eliminarConfiguracionGeneral();
    }

    @Test
    public void testConsultarPreguntasFacilesSinResultados() {
        eliminarConfiguracionGeneral();
        ArrayList<PreguntaDificil> listaPreguntas = new ArrayList<PreguntaDificil>();
        try {
            listaPreguntas = PREGUNTA_DIFICIL_DAO.consultarPreguntasDificilConTema("perros");
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(ConsultaPreguntaDificil.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assertions.assertTrue(listaPreguntas.isEmpty());
    }
}
