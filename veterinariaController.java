package edu.teclemas.veterinaria;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class veterinariaController {

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableColumn<VeterinariaEntity, String> tclAnimal;

    @FXML
    private TableColumn<VeterinariaEntity, Integer> tclEdad;

    @FXML
    private TableColumn<VeterinariaEntity, String> tclNombre;

    @FXML
    private TableColumn<VeterinariaEntity, String> tclRaza;

    @FXML
    private TableView<VeterinariaEntity> tclVeterinaria;

    @FXML
    private TextField txtAnimal;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtRaza;

    // Lista observable para almacenar los registros
    private ObservableList<VeterinariaEntity> veterinariaList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Configurar las columnas de la tabla
        tclNombre.setCellValueFactory(new PropertyValueFactory<VeterinariaEntity, String>("nombre"));
        tclAnimal.setCellValueFactory(new PropertyValueFactory<VeterinariaEntity, String>("animal"));
        tclRaza.setCellValueFactory(new PropertyValueFactory<VeterinariaEntity, String>("raza"));
        tclEdad.setCellValueFactory(new PropertyValueFactory<VeterinariaEntity, Integer>("edad"));

        // Cargar los datos iniciales desde la base de datos
        try {
            VeterinariaDAO dao = new VeterinariaDAO();
            veterinariaList.addAll(dao.getAllVeterinarias());
        } catch (SQLException e) {
            alertarFormulario(Alert.AlertType.ERROR, "Error", "Error de base de datos",
                    "No se pudo conectar a la base de datos: " + e.getMessage());
        }
        // Vincular la lista observable a la tabla
        tclVeterinaria.setItems(veterinariaList);
    }

    @FXML
    void agregar(ActionEvent event) {
        boolean esValidoFormulario = validarCamposVacio();
        if (esValidoFormulario) {
            return;
        }
        String nombre = txtNombre.getText();
        String raza = txtRaza.getText();
        String animal = txtAnimal.getText();
        int edad = Integer.valueOf(txtEdad.getText());

        VeterinariaEntity objetoVerinario = new VeterinariaEntity();
        objetoVerinario.setNombre(nombre);
        objetoVerinario.setAnimal(animal);
        objetoVerinario.setRaza(raza);
        objetoVerinario.setEdad(edad);

        veterinariaList.add(objetoVerinario);
        limpiarCajasTexto();
    }

    private boolean validarCamposVacio() {
        if (txtNombre.getText() == null || txtNombre.getText().isEmpty()) {
            // Mostrar mensaje de error con un alert
            alertarFormulario(Alert.AlertType.ERROR, "Error", "Campos vacíos",
                    "Por favor, ingresa el nombre.");
            return true;
        } else if (txtAnimal.getText() == null || txtAnimal.getText().isEmpty()) {
            alertarFormulario(Alert.AlertType.ERROR, "Error", "Campos vacíos",
                    "Por favor, ingresa el tipo de animal.");
            return true;
        } else if (txtRaza.getText() == null || txtRaza.getText().isEmpty()) {
            alertarFormulario(Alert.AlertType.ERROR, "Error", "Campos vacíos",
                    "Por favor, ingresa la raza del animal.");
            return true;
        } else if (txtEdad.getText() == null || txtEdad.getText().isEmpty()) {
            alertarFormulario(Alert.AlertType.ERROR, "Error", "Campos vacíos",
                    "Por favor, ingresa la edad del animal.");
            return true;
        }
        return false;
    }

    private void alertarFormulario(AlertType error, String titulo, String subtitulo, String contenido) {
        Alert alert = new Alert(error);
        alert.setTitle(titulo);
        alert.setHeaderText(subtitulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    void guardar(ActionEvent event) {
        for (VeterinariaEntity entity : veterinariaList) {
            try {
                VeterinariaDAO dao = new VeterinariaDAO();
                VeterinariaEntity veterinarioExiste = dao.getVeterinariaEntitybyNombreAnimalRazaEdad(entity);
                if (veterinarioExiste != null) {
                    continue; // Si ya existe, no lo guardamos de nuevo
                }
                boolean isSaved = dao.saveVeterinaria(entity);
                if (isSaved) {
                    alertarFormulario(Alert.AlertType.INFORMATION, "Éxito", "Registro guardado",
                            "El registro se ha guardado correctamente.");
                } else {
                    alertarFormulario(Alert.AlertType.ERROR, "Error", "No se pudo guardar el registro",
                            "Hubo un error al guardar el registro.");
                }
            } catch (SQLException e) {
                alertarFormulario(Alert.AlertType.ERROR, "Error", "Error de base de datos",
                        "No se pudo conectar a la base de datos: " + e.getMessage());
            }
        }
    }

    @FXML
    void limpiar(ActionEvent event) {
        limpiarCajasTexto();
    }

    private void limpiarCajasTexto() {
        txtNombre.clear();
        txtAnimal.clear();
        txtRaza.clear();
        txtEdad.clear();
    }

}