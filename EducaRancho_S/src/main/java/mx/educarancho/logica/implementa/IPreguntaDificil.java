package mx.educarancho.logica.implementa;

import java.util.ArrayList;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.dominio.PreguntaDificil;

public interface IPreguntaDificil {
 public ArrayList<PreguntaDificil> consultarPreguntasDificiles() throws ExcepcionDAO;
    public PreguntaDificil consultarPreguntaDificilSinRepetir(ArrayList<PreguntaDificil> listaPreguntasDificil, ArrayList<Integer> listaPreguntasSeleccionadas) throws ExcepcionDAO;
    public void insertarPreguntaDificil(PreguntaDificil nuevaPregunta) throws ExcepcionDAO;
    public void borrarPreguntaDificil(int numeroPregunta) throws ExcepcionDAO;
    public ArrayList<PreguntaDificil> consultarPreguntasDificilConTema(String tema) throws ExcepcionDAO;
}
