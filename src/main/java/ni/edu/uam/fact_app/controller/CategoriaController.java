package ni.edu.uam.fact_app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.fact_app.model.Categoria;
import ni.edu.uam.fact_app.util.Datos;

public class CategoriaController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private CheckBox chkActiva;

    @FXML private TableView<Categoria> tblCategorias;
    @FXML private TableColumn<Categoria, Integer> colId;
    @FXML private TableColumn<Categoria, String> colNombre;
    @FXML private TableColumn<Categoria, Boolean> colActiva;

    private final ObservableList<Categoria> listaCategorias = Datos.LISTA_CATEGORIAS;
    private int contadorId = 1;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colActiva.setCellValueFactory(new PropertyValueFactory<>("activa"));

        tblCategorias.setItems(listaCategorias);

        contadorId = listaCategorias.stream().mapToInt(Categoria::getId).max().orElse(0) + 1;

        tblCategorias.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            if (seleccionada != null) {
                cargarEnFormulario(seleccionada);
            }
        });
    }

    @FXML
    private void onGuardar() {
        if (!validarFormulario()) {
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setId(contadorId++);
        categoria.setNombre(txtNombre.getText().trim());
        categoria.setActiva(chkActiva.isSelected());

        listaCategorias.add(categoria);
        onLimpiar();
    }

    @FXML
    private void onModificar() {
        Categoria seleccionada = tblCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una categoría de la tabla para modificar.");
            return;
        }
        if (!validarFormulario()) {
            return;
        }

        seleccionada.setNombre(txtNombre.getText().trim());
        seleccionada.setActiva(chkActiva.isSelected());

        tblCategorias.refresh();
        onLimpiar();
    }

    @FXML
    private void onEliminar() {
        Categoria seleccionada = tblCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una categoría de la tabla para eliminar.");
            return;
        }
        listaCategorias.remove(seleccionada);
        onLimpiar();
    }

    @FXML
    private void onLimpiar() {
        txtId.clear();
        txtNombre.clear();
        chkActiva.setSelected(false);
        tblCategorias.getSelectionModel().clearSelection();
    }

    private void cargarEnFormulario(Categoria categoria) {
        txtId.setText(String.valueOf(categoria.getId()));
        txtNombre.setText(categoria.getNombre());
        chkActiva.setSelected(categoria.isActiva());
    }

    private boolean validarFormulario() {
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("El nombre de la categoría es obligatorio.");
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