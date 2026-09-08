package ni.edu.uam.fact_app.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.fact_app.model.Cargo;
import ni.edu.uam.fact_app.model.Categoria;

/**
 * Listas en memoria compartidas entre los distintos formularios/controllers,
 * para que por ejemplo el combo de "Cargo" en Empleado vea los cargos
 * creados desde el formulario de Cargo.
 */
public class Datos {

    private Datos() {
    }

    public static final ObservableList<Cargo> LISTA_CARGOS = FXCollections.observableArrayList();
    public static final ObservableList<Categoria> LISTA_CATEGORIAS = FXCollections.observableArrayList();
}