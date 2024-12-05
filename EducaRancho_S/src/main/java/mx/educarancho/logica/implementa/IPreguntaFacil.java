
package mx.educarancho.logica.implementa;

import java.util.ArrayList;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.dominio.PreguntaFacil;

public interface IPreguntaFacil {
    public ArrayList<PreguntaFacil> consultarPreguntasFaciles() throws ExcepcionDAO;
    public ArrayList<PreguntaFacil> consultarPreguntasFacilesConTema(String tema) throws ExcepcionDAO;
    public PreguntaFacil consultarPreguntaFacilSinRepetir(ArrayList<PreguntaFacil> listaPreguntasFaciles, ArrayList<Integer> listaPreguntasSeleccionadas) throws ExcepcionDAO;
    public void insertarPreguntaFacil(PreguntaFacil nuevaPregunta) throws ExcepcionDAO;
    public void borrarPreguntaFacil(int numeroPregunta) throws ExcepcionDAO;
    
}
