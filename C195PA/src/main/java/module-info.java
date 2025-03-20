module wgu.c195pa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens wgu.c195pa to javafx.fxml;
    exports wgu.c195pa;

    opens wgu.c195pa.helper to javafx.fxml;
    exports wgu.c195pa.helper;

    opens wgu.c195pa.controller to javafx.fxml;
    exports wgu.c195pa.controller;

    opens wgu.c195pa.model to javafx.fxml;
    exports wgu.c195pa.model;

}