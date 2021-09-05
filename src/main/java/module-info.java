module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;

    opens org.example to javafx.fxml;
    exports org.example;
}