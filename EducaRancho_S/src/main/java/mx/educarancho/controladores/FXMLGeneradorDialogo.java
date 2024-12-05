
package mx.educarancho.controladores;

import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import mx.educarancho.logica.dominio.MensajeAlerta;


public class FXMLGeneradorDialogo{


    public static final ButtonType BOTON_SI = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);

    public static final ButtonType BOTON_NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

    public static Optional<ButtonType> getDialogo(MensajeAlerta mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        if (null != mensaje.getTipoAlerta()) {
            switch (mensaje.getTipoAlerta()) {
                case EXITOSO:
                    break;
                case ADVERTENCIA:
                    alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setHeaderText("Advertencia");
                    break;
                case ERROR:
                case FATAL:
                    alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("Error");
                    break;
                default:
                    throw new IllegalArgumentException("El tipo de mensaje no es admitido en el objeto Alert");
            }
        }
        alerta.setTitle("Cuadro de diálogo");
        alerta.setContentText(mensaje.getContenido());
        return alerta.showAndWait();
    }

    public static Optional<ButtonType> getConfirmacionDialogo(String mensaje) {
        Alert alerta = new Alert(
                Alert.AlertType.CONFIRMATION,
                mensaje,
                BOTON_SI,
                BOTON_NO);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText("Selecciona la opción de tu preferencia");
        return alerta.showAndWait();
    }


    public static Optional<ButtonType> getDialogoDisenado(String mensaje, ObservableList<ButtonType> opciones) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText("Selecciona la opción de tu preferencia");
        alerta.setContentText(mensaje);
        opciones.add(new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE));
        alerta.getButtonTypes().setAll(opciones);
        return alerta.showAndWait();
    }

}
