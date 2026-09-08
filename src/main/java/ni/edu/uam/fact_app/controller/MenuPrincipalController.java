package ni.edu.uam.fact_app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import ni.edu.uam.fact_app.util.SceneManager;

public class MenuPrincipalController {

    @FXML
    private StackPane panelContenido;

    @FXML
    private void abrirProductos() {
        SceneManager.cargarVista(panelContenido, "producto-view.fxml");
    }

    @FXML
    private void abrirCargos() {
        SceneManager.cargarVista(panelContenido, "cargo-view.fxml");
    }

    @FXML
    private void abrirCategorias() {
        SceneManager.cargarVista(panelContenido, "categoria-view.fxml");
    }

    @FXML
    private void abrirEmpleados() {
        SceneManager.cargarVista(panelContenido, "empleado-view.fxml");
    }

    @FXML
    private void abrirAcercaDe() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Acerca de");
        alerta.setHeaderText(null);
        alerta.setContentText("Sistema de Facturación JavaFX - Semana 4 (JavaFX, Lombok, Git y GitHub).");
        alerta.showAndWait();
    }
}