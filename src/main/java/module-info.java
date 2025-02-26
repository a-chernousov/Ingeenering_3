module com.example.factorymethod {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.factorymethod to javafx.fxml;
    exports com.example.factorymethod;
    exports com.example.factorymethod.manager;
    opens com.example.factorymethod.manager to javafx.fxml;
}