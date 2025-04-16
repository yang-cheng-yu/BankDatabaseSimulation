module org.example.bankdatabasesimulation {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.bankdatabasesimulation to javafx.fxml;
    exports org.example.bankdatabasesimulation;
}