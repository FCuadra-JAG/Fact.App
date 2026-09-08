package ni.edu.uam.fact_app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import ni.edu.uam.fact_app.model.Cargo;
import ni.edu.uam.fact_app.model.Empleado;
import ni.edu.uam.fact_app.util.Datos;

import java.time.LocalDate;

public class EmpleadoController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private ComboBox<Cargo> cboCargo;
    @FXML private DatePicker dpFechaContratacion;
    @FXML private CheckBox chkActivo;

    @FXML private TableView<Empleado> tblEmpleados;
    @FXML private TableColumn<Empleado, Integer> colId;
    @FXML private TableColumn<Empleado, String> colNombres;
    @FXML private TableColumn<Empleado, String> colApellidos;
    @FXML private TableColumn<Empleado, Cargo> colCargo;
    @FXML private TableColumn<Empleado, LocalDate> colFecha;
    @FXML private TableColumn<Empleado, Boolean> colActivo;

    private final ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();
    private int contadorId = 1;

    @FXML
    public void initialize() {
        cboCargo.setItems(Datos.LISTA_CARGOS);
        configurarVisualizacionCargo();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        colCargo.setCellFactory(columna -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Cargo cargo, boolean vacio) {
                super.updateItem(cargo, vacio);
                setText(vacio || cargo == null ? null : cargo.getNombre());
            }
        });

        tblEmpleados.setItems(listaEmpleados);

        contadorId = listaEmpleados.stream().mapToInt(Empleado::getId).max().orElse(0) + 1;

        tblEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {
            if (seleccionado != null) {
                cargarEnFormulario(seleccionado);
            }
        });
    }

    private void configurarVisualizacionCargo() {
        StringConverter<Cargo> convertidor = new StringConverter<>() {
            @Override
            public String toString(Cargo cargo) {
                return cargo == null ? "" : cargo.getNombre();
            }

            @Override
            public Cargo fromString(String texto) {
                return null;
            }
        };
        cboCargo.setConverter(convertidor);
        cboCargo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Cargo cargo, boolean vacio) {
                super.updateItem(cargo, vacio);
                setText(vacio || cargo == null ? null : cargo.getNombre());
            }
        });
    }

    @FXML
    private void onGuardar() {
        if (!validarFormulario()) {
            return;
        }

        Empleado empleado = new Empleado();
        empleado.setId(contadorId++);
        empleado.setNombres(txtNombres.getText().trim());
        empleado.setApellidos(txtApellidos.getText().trim());
        empleado.setCargo(cboCargo.getValue());
        empleado.setFechaContratacion(dpFechaContratacion.getValue());
        empleado.setActivo(chkActivo.isSelected());

        listaEmpleados.add(empleado);
        onLimpiar();
    }

    @FXML
    private void onModificar() {
        Empleado seleccionado = tblEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un empleado de la tabla para modificar.");
            return;
        }
        if (!validarFormulario()) {
            return;
        }

        seleccionado.setNombres(txtNombres.getText().trim());
        seleccionado.setApellidos(txtApellidos.getText().trim());
        seleccionado.setCargo(cboCargo.getValue());
        seleccionado.setFechaContratacion(dpFechaContratacion.getValue());
        seleccionado.setActivo(chkActivo.isSelected());

        tblEmpleados.refresh();
        onLimpiar();
    }

    @FXML
    private void onEliminar() {
        Empleado seleccionado = tblEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un empleado de la tabla para eliminar.");
            return;
        }
        listaEmpleados.remove(seleccionado);
        onLimpiar();
    }

    @FXML
    private void onLimpiar() {
        txtId.clear();
        txtNombres.clear();
        txtApellidos.clear();
        cboCargo.setValue(null);
        dpFechaContratacion.setValue(null);
        chkActivo.setSelected(false);
        tblEmpleados.getSelectionModel().clearSelection();
    }

    private void cargarEnFormulario(Empleado empleado) {
        txtId.setText(String.valueOf(empleado.getId()));
        txtNombres.setText(empleado.getNombres());
        txtApellidos.setText(empleado.getApellidos());
        cboCargo.setValue(empleado.getCargo());
        dpFechaContratacion.setValue(empleado.getFechaContratacion());
        chkActivo.setSelected(empleado.isActivo());
    }

    private boolean validarFormulario() {
        if (txtNombres.getText() == null || txtNombres.getText().trim().isEmpty()) {
            mostrarAlerta("Los nombres son obligatorios.");
            return false;
        }
        if (txtApellidos.getText() == null || txtApellidos.getText().trim().isEmpty()) {
            mostrarAlerta("Los apellidos son obligatorios.");
            return false;
        }
        if (cboCargo.getValue() == null) {
            mostrarAlerta("Selecciona un cargo.");
            return false;
        }
        if (dpFechaContratacion.getValue() == null) {
            mostrarAlerta("Selecciona la fecha de contratación.");
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