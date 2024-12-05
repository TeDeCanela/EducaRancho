package mx.educarancho.basededatos;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.educarancho.logica.concreta.ExcepcionDAO;
import mx.educarancho.logica.dominio.EstadosDAO;

public class AdministradorBaseDeDatos {

    private Connection conexion;

    private static final String BASEDEDATOS_NOMBRE = "mysql.basededatos.nombre";
    private static final String BASEDEDATOS_USUARIO = "mysql.basededatos.usuario";
    private static final String BASEDEDATOS_CONTRASENA = "mysql.basededatos.contrasena";

    public Connection obtenerConexion() throws ExcepcionDAO {
        Connection nuevaConexion = null;
        Properties propiedades = new AdministradorBaseDeDatos().getPropiedadesArchivo();

        if (propiedades != null) {
            try {
                nuevaConexion = DriverManager.getConnection(
                        propiedades.getProperty(BASEDEDATOS_NOMBRE),
                        propiedades.getProperty(BASEDEDATOS_USUARIO),
                        propiedades.getProperty(BASEDEDATOS_CONTRASENA));

                if (nuevaConexion == null) {
                    throw new ExcepcionDAO("Lo sentimos, no fue posible conectarse con la base de datos del sistema",
                            EstadosDAO.FATAL);
                }
            } catch (SQLException ex) {
                manejarCredencialesIncorrectas(ex.getMessage());
            }
            return nuevaConexion;
        } else {
            throw new ExcepcionDAO("No fue posible encontrar las credenciales de la base de datos", EstadosDAO.FATAL);
        }
    }

    public AdministradorBaseDeDatos() {
    }

    public void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException exception) {
                Logger.getLogger(AdministradorBaseDeDatos.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
    }

    public Connection getInstancia() throws ExcepcionDAO {
        if (conexion == null) {
            throw new ExcepcionDAO("Lo sentimos, no fue posible conectarse con la base de datos del sistema",
                    EstadosDAO.FATAL);
        } else {
            return conexion;
        }
    }

    private Properties getPropiedadesArchivo() throws ExcepcionDAO {
    Properties propiedades = new Properties();
    try (InputStream archivo = getClass().getClassLoader().getResourceAsStream("basededatos.properties")) {
        if (archivo == null) {
            throw new ExcepcionDAO("No se encontró el archivo basededatos.properties", EstadosDAO.ERROR);
        }
        propiedades.load(archivo);
    } catch (IOException e) {
        throw new ExcepcionDAO("Error al cargar el archivo de propiedades", EstadosDAO.ERROR);
    }
    return propiedades;
}

    private void manejarCredencialesIncorrectas(String mensaje) throws ExcepcionDAO {
        Properties propiedades = new AdministradorBaseDeDatos().getPropiedadesArchivo();
        if (mensaje.equals("Access denied for user '"+propiedades.getProperty(BASEDEDATOS_USUARIO)+"'@'localhost' (using password: YES)")) {
            throw new ExcepcionDAO("Las credenciales de la base de datos son incorrectas", EstadosDAO.ERROR);
        }else if(mensaje.equals("Access denied for user '"+propiedades.getProperty(BASEDEDATOS_USUARIO)+"'@'localhost' (using password: YES)")){
            throw new ExcepcionDAO("Las credenciales de la base de datos son incorrectas", EstadosDAO.ERROR);
        } else if(mensaje.equals("Access denied for user '"+propiedades.getProperty(BASEDEDATOS_USUARIO)+"'@'localhost' to database 'coil_vic'")){
            throw new ExcepcionDAO("Asegurese de tener el permiso para acceder a la base de datos", EstadosDAO.ERROR);
        } else {
            throw new ExcepcionDAO(mensaje,
                    EstadosDAO.ERROR);          
        }
    }
}

