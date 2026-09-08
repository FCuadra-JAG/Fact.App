package ni.edu.uam.fact_app.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FacturacionApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                FacturacionApplication.class.getResource("/ni/edu/uam/fact_app/ni.edu.uam/fxml/menu-principal.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Sistema de Facturación");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}