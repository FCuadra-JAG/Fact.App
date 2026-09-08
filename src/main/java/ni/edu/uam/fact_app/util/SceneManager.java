package ni.edu.uam.fact_app.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class SceneManager {

    private SceneManager() {
        // Clase de utilidades, no se instancia
    }


    public static void cargarVista(StackPane panelContenido, String nombreFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/ni/edu/uam/fact_app/ni.edu.uam/fxml/" + nombreFxml)
            );
            Parent vista = loader.load();
            panelContenido.getChildren().setAll(vista);
        } catch (IOException e) {
            mostrarError("No se pudo cargar la vista: " + nombreFxml);
            e.printStackTrace();
        }
    }

    private static void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}