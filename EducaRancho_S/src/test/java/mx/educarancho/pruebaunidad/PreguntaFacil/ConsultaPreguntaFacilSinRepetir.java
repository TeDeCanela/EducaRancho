package mx.educarancho.pruebaunidad.PreguntaFacil;

import java.util.ArrayList;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaFacilDAO;
import mx.educarancho.logica.dominio.PreguntaFacil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ConsultaPreguntaFacilSinRepetir {

     @Test
    public void testConsultarPreguntaFacilSinRepetirExitoso() throws ExcepcionDAO {
        ArrayList<PreguntaFacil> listaPreguntasFaciles = new ArrayList<>();
        listaPreguntasFaciles.add(crearPregunta(1, "¿Cuál es la capital de Francia?", "París"));
        listaPreguntasFaciles.add(crearPregunta(2, "¿Cuál es la capital de Alemania?", "Berlín"));
        
        ArrayList<Integer> listaPreguntasSeleccionadas = new ArrayList<>();
        listaPreguntasSeleccionadas.add(1);

        PreguntaFacil resultado = new PreguntaFacilDAO()
                .consultarPreguntaFacilSinRepetir(listaPreguntasFaciles, listaPreguntasSeleccionadas);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.getNumero());
        Assertions.assertEquals("¿Cuál es la capital de Alemania?", resultado.getPregunta());
    }

    @Test
    public void testConsultarPreguntaFacilSinRepetirSinDisponibles() {
        ArrayList<PreguntaFacil> listaPreguntasFaciles = new ArrayList<>();
        listaPreguntasFaciles.add(crearPregunta(1, "¿Cuál es la capital de Francia?", "París"));
        listaPreguntasFaciles.add(crearPregunta(2, "¿Cuál es la capital de Alemania?", "Berlín"));

        ArrayList<Integer> listaPreguntasSeleccionadas = new ArrayList<>();
        listaPreguntasSeleccionadas.add(1);
        listaPreguntasSeleccionadas.add(2);

        ExcepcionDAO excepcion = Assertions.assertThrows(
                ExcepcionDAO.class, 
                () -> new PreguntaFacilDAO()
                        .consultarPreguntaFacilSinRepetir(listaPreguntasFaciles, listaPreguntasSeleccionadas)
        );

        Assertions.assertEquals("No hay más preguntas disponibles. Todas ya fueron seleccionadas.", excepcion.getMessage());
    }

    private PreguntaFacil crearPregunta(int numero, String pregunta, String respuestaCorrecta) {
        PreguntaFacil preguntaFacil = new PreguntaFacil();
        preguntaFacil.setNumero(numero);
        preguntaFacil.setPregunta(pregunta);
        preguntaFacil.setRespuestaCorrecta(respuestaCorrecta);
        return preguntaFacil;
    }
}
