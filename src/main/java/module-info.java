module com.kwizera.javaamalilabs03bankmgtsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.kwizera.javaamalilabs03bankmgtsystem to javafx.fxml;
    opens com.kwizera.javaamalilabs03bankmgtsystem.controllers to javafx.fxml;
    exports com.kwizera.javaamalilabs03bankmgtsystem;
}