package ni.edu.uam.fact_app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.fact_app.model.Cargo;
import ni.edu.uam.fact_app.util.Datos;

public class CargoController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;

    @FXML private TableView<Cargo> tblCargos;
    @FXML private TableColumn<Cargo, Integer> colId;
    @FXML private TableColumn<Cargo, String> colNombre;
    @FXML private TableColumn<Cargo, String> colDescripcion;

    private final ObservableList<Cargo> listaCargos = Datos.LISTA_CARGOS;
    private int contadorId = 1;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tblCargos.setItems(listaCargos);

        contadorId = listaCargos.stream().mapToInt(Cargo::getId).max().orElse(0) + 1;

        tblCargos.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {
            if (seleccionado != null) {
                cargarEnFormulario(seleccionado);
            }
        });
    }

    @FXML
    private void onGuardar() {
        if (!validarFormulario()) {
            return;
        }

        Cargo cargo = new Cargo();
        cargo.setId(contadorId++);
        cargo.setNombre(txtNombre.getText().trim());
        cargo.setDescripcion(txtDescripcion.getText().trim());

        listaCargos.add(cargo);
        onLimpiar();
    }

    @FXML
    private void onModificar() {
        Cargo seleccionado = tblCargos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un cargo de la tabla para modificar.");
            return;
        }
        if (!validarFormulario()) {
            return;
        }

        seleccionado.setNombre(txtNombre.getText().trim());
        seleccionado.setDescripcion(txtDescripcion.getText().trim());

        tblCargos.refresh();
        onLimpiar();
    }

    @FXML
    private void onEliminar() {
        Cargo seleccionado = tblCargos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un cargo de la tabla para eliminar.");
            return;
        }
        listaCargos.remove(seleccionado);
        onLimpiar();
    }

    @FXML
    private void onLimpiar() {
        txtId.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        tblCargos.getSelectionModel().clearSelection();
    }

    private void cargarEnFormulario(Cargo cargo) {
        txtId.setText(String.valueOf(cargo.getId()));
        txtNombre.setText(cargo.getNombre());
        txtDescripcion.setText(cargo.getDescripcion());
    }

    private boolean validarFormulario() {
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("El nombre del cargo es obligatorio.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}