package mx.educarancho.logica.concreta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import mx.educarancho.basededatos.AdministradorBaseDeDatos;
import mx.educarancho.logica.dominio.EstadosDAO;
import mx.educarancho.logica.dominio.PreguntaMedia;
import mx.educarancho.logica.implementa.IPreguntaMedia;

public class PreguntaMediaDAO implements IPreguntaMedia {

    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();
    
    @Override
    public ArrayList<PreguntaMedia> consultarPreguntasMedia() throws ExcepcionDAO {
         Connection conexion = null;
        ArrayList<PreguntaMedia> listaPreguntas = new ArrayList<>();

        String consultaMySQL = "SELECT * FROM preguntamedia";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);

            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();

            while (respuestaConsulta.next()) {
                PreguntaMedia pregunta = new PreguntaMedia();
                pregunta.setNumero(respuestaConsulta.getInt("numero"));
                pregunta.setPregunta(respuestaConsulta.getString("pregunta"));
                pregunta.setRespuesta1(respuestaConsulta.getString("respuesta1"));
                pregunta.setRespuesta2(respuestaConsulta.getString("respuesta2"));
                pregunta.setRespuesta3(respuestaConsulta.getString("respuesta3"));
                pregunta.setRespuesta4(respuestaConsulta.getString("respuesta4"));
                pregunta.setRespuesta5(respuestaConsulta.getString("respuesta5"));
                pregunta.setRespuesta6(respuestaConsulta.getString("respuesta6"));
                pregunta.setRespuestaCorrecta(respuestaConsulta.getString("respuestaCorrecta"));
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
    public PreguntaMedia consultarPreguntaMediaSinRepetir(ArrayList<PreguntaMedia> listaPreguntasMedia, ArrayList<Integer> listaPreguntasSeleccionadas) throws ExcepcionDAO {
        ArrayList<PreguntaMedia> preguntasDisponibles = new ArrayList<>();

        for (PreguntaMedia pregunta : listaPreguntasMedia) {
            if (!listaPreguntasSeleccionadas.contains(pregunta.getNumero())) {
                preguntasDisponibles.add(pregunta);
            }
        }

        if (preguntasDisponibles.isEmpty()) {
            throw new ExcepcionDAO("No hay más preguntas disponibles. Todas ya fueron seleccionadas.", EstadosDAO.ERROR);
        }

        Random random = new Random();
        int indiceAleatorio = random.nextInt(preguntasDisponibles.size());
        PreguntaMedia preguntaSeleccionada = preguntasDisponibles.get(indiceAleatorio);

        return preguntaSeleccionada;
    }

    @Override
    public void insertarPreguntaMedia(PreguntaMedia nuevaPregunta) throws ExcepcionDAO {
        Connection conexion = null;
        String consultaMySQL = "INSERT INTO preguntamedia (numero, pregunta, respuesta1, respuesta2, respuesta3, respuesta4, respuesta5, respuesta6, respuestaCorrecta, tema, archivoAyuda) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoInsercion = conexion.prepareStatement(consultaMySQL);

            enunciadoInsercion.setInt(1, nuevaPregunta.getNumero());
            enunciadoInsercion.setString(2, nuevaPregunta.getPregunta());
            enunciadoInsercion.setString(3, nuevaPregunta.getRespuesta1());
            enunciadoInsercion.setString(4, nuevaPregunta.getRespuesta2());
            enunciadoInsercion.setString(5, nuevaPregunta.getRespuesta3());
            enunciadoInsercion.setString(6, nuevaPregunta.getRespuesta4());
            enunciadoInsercion.setString(7, nuevaPregunta.getRespuesta5());
            enunciadoInsercion.setString(8, nuevaPregunta.getRespuesta6());
            enunciadoInsercion.setString(9, nuevaPregunta.getRespuestaCorrecta());
            enunciadoInsercion.setString(10, nuevaPregunta.getTema());
            enunciadoInsercion.setString(11, nuevaPregunta.getArchivoAyuda());

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
    public void borrarPreguntaMedia(int numeroPregunta) throws ExcepcionDAO {
         Connection conexion = null;
        String consultaMySQL = "DELETE FROM preguntamedia WHERE numero = ?";

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
    public ArrayList<PreguntaMedia> consultarPreguntasMediaConTema(String tema) throws ExcepcionDAO {
        Connection conexion = null;
        ArrayList<PreguntaMedia> listaPreguntas = new ArrayList<>();

        String consultaMySQL = "SELECT * FROM preguntamedia WHERE tema=?";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, tema);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();

            while (respuestaConsulta.next()) {
                PreguntaMedia pregunta = new PreguntaMedia();
                pregunta.setNumero(respuestaConsulta.getInt("numero"));
                pregunta.setPregunta(respuestaConsulta.getString("pregunta"));
                pregunta.setRespuesta1(respuestaConsulta.getString("respuesta1"));
                pregunta.setRespuesta2(respuestaConsulta.getString("respuesta2"));
                pregunta.setRespuesta3(respuestaConsulta.getString("respuesta3"));
                pregunta.setRespuesta4(respuestaConsulta.getString("respuesta4"));
                pregunta.setRespuesta4(respuestaConsulta.getString("respuesta5"));
                pregunta.setRespuesta4(respuestaConsulta.getString("respuesta6"));
                pregunta.setRespuestaCorrecta(respuestaConsulta.getString("respuestaCorrecta"));
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
