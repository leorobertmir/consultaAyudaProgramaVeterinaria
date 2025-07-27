module edu.teclemas.veterinaria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens edu.teclemas.veterinaria to javafx.fxml, java.sql;

    exports edu.teclemas.veterinaria;
}
