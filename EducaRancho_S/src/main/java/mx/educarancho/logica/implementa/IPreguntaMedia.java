package mx.educarancho.logica.implementa;

import java.util.ArrayList;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.dominio.PreguntaMedia;

public interface IPreguntaMedia {
    public ArrayList<PreguntaMedia> consultarPreguntasMedia() throws ExcepcionDAO;
    public PreguntaMedia consultarPreguntaMediaSinRepetir(ArrayList<PreguntaMedia> listaPreguntasFaciles, ArrayList<Integer> listaPreguntasSeleccionadas) throws ExcepcionDAO;
    public void insertarPreguntaMedia(PreguntaMedia nuevaPregunta) throws ExcepcionDAO;
    public void borrarPreguntaMedia(int numeroPregunta) throws ExcepcionDAO;
    public ArrayList<PreguntaMedia> consultarPreguntasMediaConTema(String tema) throws ExcepcionDAO;
}
