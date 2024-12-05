package mx.educarancho.logica.concreta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import mx.educarancho.basededatos.AdministradorBaseDeDatos;
import mx.educarancho.logica.implementa.IPreguntaFacil;
import mx.educarancho.logica.dominio.EstadosDAO;
import mx.educarancho.logica.dominio.PreguntaFacil;

public class PreguntaFacilDAO implements IPreguntaFacil {

    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

    @Override
    public ArrayList<PreguntaFacil> consultarPreguntasFaciles() throws ExcepcionDAO {

        Connection conexion = null;
        ArrayList<PreguntaFacil> listaPreguntas = new ArrayList<>();

        String consultaMySQL = "SELECT * FROM preguntafacil";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);

            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();

            while (respuestaConsulta.next()) {
                PreguntaFacil pregunta = new PreguntaFacil();
                pregunta.setNumero(respuestaConsulta.getInt("numero"));
                pregunta.setPregunta(respuestaConsulta.getString("pregunta"));
                pregunta.setRespuesta1(respuestaConsulta.getString("respuesta1"));
                pregunta.setRespuesta2(respuestaConsulta.getString("respuesta2"));
                pregunta.setRespuesta3(respuestaConsulta.getString("respuesta3"));
                pregunta.setRespuesta4(respuestaConsulta.getString("respuesta4"));
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

    public PreguntaFacil consultarPreguntaFacilSinRepetir(ArrayList<PreguntaFacil> listaPreguntasFaciles, ArrayList<Integer> listaPreguntasSeleccionadas) throws ExcepcionDAO {

        ArrayList<PreguntaFacil> preguntasDisponibles = new ArrayList<>();

        for (PreguntaFacil pregunta : listaPreguntasFaciles) {
            if (!listaPreguntasSeleccionadas.contains(pregunta.getNumero())) {
                preguntasDisponibles.add(pregunta);
            }
        }

        if (preguntasDisponibles.isEmpty()) {
            throw new ExcepcionDAO("No hay más preguntas disponibles. Todas ya fueron seleccionadas.", EstadosDAO.ERROR);
        }

        Random random = new Random();
        int indiceAleatorio = random.nextInt(preguntasDisponibles.size());
        PreguntaFacil preguntaSeleccionada = preguntasDisponibles.get(indiceAleatorio);

        return preguntaSeleccionada;
    }

    public void insertarPreguntaFacil(PreguntaFacil nuevaPregunta) throws ExcepcionDAO {

        Connection conexion = null;
        String consultaMySQL = "INSERT INTO preguntafacil (numero, pregunta, respuesta1, respuesta2, respuesta3, respuesta4, respuestaCorrecta, tema, archivoAyuda) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoInsercion = conexion.prepareStatement(consultaMySQL);

            enunciadoInsercion.setInt(1, nuevaPregunta.getNumero());
            enunciadoInsercion.setString(2, nuevaPregunta.getPregunta());
            enunciadoInsercion.setString(3, nuevaPregunta.getRespuesta1());
            enunciadoInsercion.setString(4, nuevaPregunta.getRespuesta2());
            enunciadoInsercion.setString(5, nuevaPregunta.getRespuesta3());
            enunciadoInsercion.setString(6, nuevaPregunta.getRespuesta4());
            enunciadoInsercion.setString(7, nuevaPregunta.getRespuestaCorrecta());
            enunciadoInsercion.setString(8, nuevaPregunta.getTema());
            enunciadoInsercion.setString(9, nuevaPregunta.getArchivoAyuda());

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

    public void borrarPreguntaFacil(int numeroPregunta) throws ExcepcionDAO {

        Connection conexion = null;
        String consultaMySQL = "DELETE FROM preguntafacil WHERE numero = ?";

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
    public ArrayList<PreguntaFacil> consultarPreguntasFacilesConTema(String tema) throws ExcepcionDAO {
        Connection conexion = null;
        ArrayList<PreguntaFacil> listaPreguntas = new ArrayList<>();

        String consultaMySQL = "SELECT * FROM preguntafacil WHERE tema=?";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, tema);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();

            while (respuestaConsulta.next()) {
                PreguntaFacil pregunta = new PreguntaFacil();
                pregunta.setNumero(respuestaConsulta.getInt("numero"));
                pregunta.setPregunta(respuestaConsulta.getString("pregunta"));
                pregunta.setRespuesta1(respuestaConsulta.getString("respuesta1"));
                pregunta.setRespuesta2(respuestaConsulta.getString("respuesta2"));
                pregunta.setRespuesta3(respuestaConsulta.getString("respuesta3"));
                pregunta.setRespuesta4(respuestaConsulta.getString("respuesta4"));
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
