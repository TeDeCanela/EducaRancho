package mx.educarancho.pruebaunidad.PreguntaDificil;

import java.util.ArrayList;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.concreta.PreguntaDificilDAO;
import mx.educarancho.logica.dominio.PreguntaDificil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ConsultaPreguntaDificilSinRepetir {

     @Test
    public void testConsultarPreguntaMediaSinRepetirExitoso() throws ExcepcionDAO {
        ArrayList<PreguntaDificil> listaPreguntasFaciles = new ArrayList<>();
        listaPreguntasFaciles.add(crearPregunta(1, "¿Cuánto es 4*5 naranjas?", "20", "naranjas"));
        listaPreguntasFaciles.add(crearPregunta(2, "¿Cuánto es 3*2 cometas", "6", "cometas"));
        
        ArrayList<Integer> listaPreguntasSeleccionadas = new ArrayList<>();
        listaPreguntasSeleccionadas.add(1);

        PreguntaDificil resultado = new PreguntaDificilDAO()
                .consultarPreguntaDificilSinRepetir(listaPreguntasFaciles, listaPreguntasSeleccionadas);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.getNumero());
        Assertions.assertEquals("¿Cuánto es 3*2 cometas", resultado.getPregunta());
    }

    @Test
    public void testConsultarPreguntaFacilSinRepetirSinDisponibles() {
        ArrayList<PreguntaDificil> listaPreguntasFaciles = new ArrayList<>();
        listaPreguntasFaciles.add(crearPregunta(1, "¿Cuánto es 4*5 naranjas?", "20", "naranjas"));
        listaPreguntasFaciles.add(crearPregunta(2, "¿Cuánto es 3*2 cometas", "6", "cometas"));

        ArrayList<Integer> listaPreguntasSeleccionadas = new ArrayList<>();
        listaPreguntasSeleccionadas.add(1);
        listaPreguntasSeleccionadas.add(2);

        ExcepcionDAO excepcion = Assertions.assertThrows(
                ExcepcionDAO.class, 
                () -> new PreguntaDificilDAO()
                        .consultarPreguntaDificilSinRepetir(listaPreguntasFaciles, listaPreguntasSeleccionadas)
        );

        Assertions.assertEquals("No hay más preguntas disponibles. Todas ya fueron seleccionadas.", excepcion.getMessage());
    }

    private PreguntaDificil crearPregunta(int numero, String pregunta, String respuestaCorrecta, String unidades) {
        PreguntaDificil preguntaDificil = new PreguntaDificil();
        preguntaDificil.setNumero(numero);
        preguntaDificil.setPregunta(pregunta);
        preguntaDificil.setRespuestNumerica(respuestaCorrecta);
        preguntaDificil.setRespuestaUnidad(unidades);
        return preguntaDificil;
    }
}
