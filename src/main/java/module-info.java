module ni.edu.uam.fact_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens ni.edu.uam.fact_app to javafx.graphics;
    opens ni.edu.uam.fact_app.application to javafx.fxml, javafx.graphics;
    opens ni.edu.uam.fact_app.controller to javafx.fxml;
    opens ni.edu.uam.fact_app.model to javafx.base;

    exports ni.edu.uam.fact_app;
    exports ni.edu.uam.fact_app.application;
    exports ni.edu.uam.fact_app.model;
}