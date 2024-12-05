
package mx.educarancho.logica.concreta;

import mx.educarancho.logica.dominio.EstadosDAO;

/**
 * Excepción personalizada para manejar errores específicos relacionados con la capa de acceso a datos (DAO).
 * Esta excepción permite adjuntar un estado que indica la gravedad del error.
 */

public class ExcepcionDAO extends Exception{
    
    private final EstadosDAO estado;
    
     /**
     * Crea una nueva instancia de ExcepcionDAO con un mensaje de error y un estado específico.
     * 
     * @param mensaje el mensaje de error asociado a la excepción
     * @param estado el estado que indica la gravedad del error
     */

    public ExcepcionDAO(String mensaje, EstadosDAO estado) {
        super(mensaje);
        this.estado = estado;
    }
    
    /**
     * Obtiene el estado asociado a la excepción.
     * 
     * @return el estado que indica la gravedad del error
     */

    public EstadosDAO getEstado() {
        return estado;
    }
    
}
