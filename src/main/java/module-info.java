module ni.edu.uam.fact_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jdk.jfr;

    opens ni.edu.uam.fact_app to javafx.fxml;
    exports ni.edu.uam.fact_app;
}