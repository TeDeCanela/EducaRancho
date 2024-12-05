package mx.educarancho.logica.concreta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import mx.educarancho.basededatos.AdministradorBaseDeDatos;
import mx.educarancho.logica.dominio.EstadosDAO;
import mx.educarancho.logica.dominio.PreguntaDificil;
import mx.educarancho.logica.implementa.IPreguntaDificil;

public class PreguntaDificilDAO implements IPreguntaDificil{

    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();
    
    @Override
    public ArrayList<PreguntaDificil> consultarPreguntasDificiles() throws ExcepcionDAO {
         Connection conexion = null;
        ArrayList<PreguntaDificil> listaPreguntas = new ArrayList<>();

        String consultaMySQL = "SELECT * FROM preguntadificil";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);

            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();

            while (respuestaConsulta.next()) {
                PreguntaDificil pregunta = new PreguntaDificil();
                pregunta.setNumero(respuestaConsulta.getInt("numero"));
                pregunta.setPregunta(respuestaConsulta.getString("pregunta"));
                pregunta.setRespuestNumerica(respuestaConsulta.getString("respuestaNumerica"));
                pregunta.setRespuestaUnidad(respuestaConsulta.getString("respuestaUnidad"));
                pregunta.setTema(respuestaConsulta.getString("tema"));
                pregunta.setArchivoAyuda(respuestaConsulta.getString("archivoAyuda"));

                listaPreguntas.add(pregunta);
            }

        } catch (SQLException ex) {
            throw new ExcepcionDAO("Contacte con el administrador o asegure tener los permisos necesarios "
                    + "para realizar esta operación en la base de datos", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }

        return listaPreguntas;
    }

    @Override
    public PreguntaDificil consultarPreguntaDificilSinRepetir(ArrayList<PreguntaDificil> listaPreguntasDificil, ArrayList<Integer> listaPreguntasSeleccionadas) throws ExcepcionDAO {
         ArrayList<PreguntaDificil> preguntasDisponibles = new ArrayList<>();

        for (PreguntaDificil pregunta : listaPreguntasDificil) {
            if (!listaPreguntasSeleccionadas.contains(pregunta.getNumero())) {
                preguntasDisponibles.add(pregunta);
            }
        }

        if (preguntasDisponibles.isEmpty()) {
            throw new ExcepcionDAO("No hay más preguntas disponibles. Todas ya fueron seleccionadas.", EstadosDAO.ERROR);
        }

        Random random = new Random();
        int indiceAleatorio = random.nextInt(preguntasDisponibles.size());
        PreguntaDificil preguntaSeleccionada = preguntasDisponibles.get(indiceAleatorio);

        return preguntaSeleccionada;
    }

    @Override
    public void insertarPreguntaDificil(PreguntaDificil nuevaPregunta) throws ExcepcionDAO {
        Connection conexion = null;
        String consultaMySQL = "INSERT INTO preguntadificil (numero, pregunta, respuestaNumerica, respuestaUnidad, tema, archivoAyuda)"
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoInsercion = conexion.prepareStatement(consultaMySQL);

            enunciadoInsercion.setInt(1, nuevaPregunta.getNumero());
            enunciadoInsercion.setString(2, nuevaPregunta.getPregunta());
            enunciadoInsercion.setString(3, nuevaPregunta.getRespuestNumerica());
            enunciadoInsercion.setString(4, nuevaPregunta.getRespuestaUnidad());
            enunciadoInsercion.setString(5, nuevaPregunta.getTema());
            enunciadoInsercion.setString(6, nuevaPregunta.getArchivoAyuda());

            int filasAfectadas = enunciadoInsercion.executeUpdate();

            if (filasAfectadas == 0) {
                throw new ExcepcionDAO("No se pudo insertar la nueva pregunta en la base de datos.", EstadosDAO.ERROR);
            }

        } catch (SQLException ex) {
            throw new ExcepcionDAO("Error al insertar la pregunta en la base de datos: " + ex.getMessage(), EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }

    @Override
    public void borrarPreguntaDificil(int numeroPregunta) throws ExcepcionDAO {
         Connection conexion = null;
        String consultaMySQL = "DELETE FROM preguntadificil WHERE numero = ?";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoBorrado = conexion.prepareStatement(consultaMySQL);

            enunciadoBorrado.setInt(1, numeroPregunta);

            int filasAfectadas = enunciadoBorrado.executeUpdate();

            if (filasAfectadas == 0) {
                throw new ExcepcionDAO("No se encontró una pregunta con el número especificado.", EstadosDAO.ERROR);
            }

        } catch (SQLException ex) {
            throw new ExcepcionDAO("" + ex.getMessage(), EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }
    
    @Override
    public ArrayList<PreguntaDificil> consultarPreguntasDificilConTema(String tema) throws ExcepcionDAO {
        Connection conexion = null;
        ArrayList<PreguntaDificil> listaPreguntas = new ArrayList<>();

        String consultaMySQL = "SELECT * FROM preguntafacil WHERE tema=?";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, tema);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();

            while (respuestaConsulta.next()) {
                PreguntaDificil pregunta = new PreguntaDificil();
                pregunta.setNumero(respuestaConsulta.getInt("numero"));
                pregunta.setPregunta(respuestaConsulta.getString("pregunta"));
                pregunta.setRespuestNumerica(respuestaConsulta.getString("respuestaNumerica"));
                pregunta.setRespuestaUnidad(respuestaConsulta.getString("respuestaUnidad"));
                pregunta.setTema(respuestaConsulta.getString("tema"));
                pregunta.setArchivoAyuda(respuestaConsulta.getString("archivoAyuda"));

                listaPreguntas.add(pregunta);
            }

        } catch (SQLException ex) {
            throw new ExcepcionDAO("Contacte con el administrador o asegure tener los permisos necesarios "
                    + "para realizar esta operación en la base de datos", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }

        return listaPreguntas;
    }
    
}
