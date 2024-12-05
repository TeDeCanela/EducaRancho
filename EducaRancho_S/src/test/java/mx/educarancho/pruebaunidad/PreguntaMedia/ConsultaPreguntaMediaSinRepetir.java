package mx.educarancho.pruebaunidad.PreguntaMedia;

import java.util.ArrayList;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaMediaDAO;
import mx.educarancho.logica.dominio.PreguntaMedia;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ConsultaPreguntaMediaSinRepetir {

     @Test
    public void testConsultarPreguntaMediaSinRepetirExitoso() throws ExcepcionDAO {
        ArrayList<PreguntaMedia> listaPreguntasFaciles = new ArrayList<>();
        listaPreguntasFaciles.add(crearPregunta(1, "¿Cuál es la capital de Francia?", "París"));
        listaPreguntasFaciles.add(crearPregunta(2, "¿Cuál es la capital de Alemania?", "Berlín"));
        
        ArrayList<Integer> listaPreguntasSeleccionadas = new ArrayList<>();
        listaPreguntasSeleccionadas.add(1);

        PreguntaMedia resultado = new PreguntaMediaDAO()
                .consultarPreguntaMediaSinRepetir(listaPreguntasFaciles, listaPreguntasSeleccionadas);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.getNumero());
        Assertions.assertEquals("¿Cuál es la capital de Alemania?", resultado.getPregunta());
    }

    @Test
    public void testConsultarPreguntaFacilSinRepetirSinDisponibles() {
        ArrayList<PreguntaMedia> listaPreguntasFaciles = new ArrayList<>();
        listaPreguntasFaciles.add(crearPregunta(1, "¿Cuál es la capital de Francia?", "París"));
        listaPreguntasFaciles.add(crearPregunta(2, "¿Cuál es la capital de Alemania?", "Berlín"));

        ArrayList<Integer> listaPreguntasSeleccionadas = new ArrayList<>();
        listaPreguntasSeleccionadas.add(1);
        listaPreguntasSeleccionadas.add(2);

        ExcepcionDAO excepcion = Assertions.assertThrows(
                ExcepcionDAO.class, 
                () -> new PreguntaMediaDAO()
                        .consultarPreguntaMediaSinRepetir(listaPreguntasFaciles, listaPreguntasSeleccionadas)
        );

        Assertions.assertEquals("No hay más preguntas disponibles. Todas ya fueron seleccionadas.", excepcion.getMessage());
    }

    private PreguntaMedia crearPregunta(int numero, String pregunta, String respuestaCorrecta) {
        PreguntaMedia preguntaMedia = new PreguntaMedia();
        preguntaMedia.setNumero(numero);
        preguntaMedia.setPregunta(pregunta);
        preguntaMedia.setRespuestaCorrecta(respuestaCorrecta);
        return preguntaMedia;
    }
}
