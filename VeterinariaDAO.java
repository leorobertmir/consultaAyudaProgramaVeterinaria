package edu.teclemas.veterinaria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeterinariaDAO {

    private ConnectionDB connectionDB;

    public VeterinariaDAO() {
        connectionDB = new ConnectionDB();
    }

    public boolean saveVeterinaria(VeterinariaEntity entity) throws SQLException {
        String query = "INSERT INTO info_veterinario " +
                " (nombre, animal, raza, edad, fe_registro, usr_creacion, estado) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionDB.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getNombre());
            preparedStatement.setString(2, entity.getAnimal());
            preparedStatement.setString(3, entity.getRaza());
            preparedStatement.setInt(4, entity.getEdad());
            preparedStatement.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
            preparedStatement.setString(6, "wgaibor");
            preparedStatement.setString(7, "Activo");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar el registro: " + e.getMessage());
            return false;
        } finally {
            connectionDB.disconnect();
        }
        return true;
    }

    public List<VeterinariaEntity> getAllVeterinarias() throws SQLException {
        List<VeterinariaEntity> lstVeterinario = new ArrayList<>();
        String query = "SELECT * FROM info_veterinario " +
                "WHERE estado = ? " +
                "ORDER BY fe_registro DESC";
        try (Connection connection = connectionDB.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "Activo");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VeterinariaEntity entity = new VeterinariaEntity();
                entity.setNombre(resultSet.getString("nombre"));
                entity.setAnimal(resultSet.getString("animal"));
                entity.setRaza(resultSet.getString("raza"));
                entity.setEdad(resultSet.getInt("edad"));
                lstVeterinario.add(entity);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los registros: " + e.getMessage());
        } finally {
            connectionDB.disconnect();
        }
        return lstVeterinario;
    }

    public VeterinariaEntity getVeterinariaEntitybyNombreAnimalRazaEdad(VeterinariaEntity entity) throws SQLException {
        String query = "SELECT * FROM info_veterinario " +
                "WHERE nombre = ? AND animal = ? AND raza = ? AND edad = ?";
        try (Connection connection = connectionDB.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getNombre());
            preparedStatement.setString(2, entity.getAnimal());
            preparedStatement.setString(3, entity.getRaza());
            preparedStatement.setInt(4, entity.getEdad());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                VeterinariaEntity foundEntity = new VeterinariaEntity();
                foundEntity.setNombre(resultSet.getString("nombre"));
                foundEntity.setAnimal(resultSet.getString("animal"));
                foundEntity.setRaza(resultSet.getString("raza"));
                foundEntity.setEdad(resultSet.getInt("edad"));
                return foundEntity;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el registro: " + e.getMessage());
        } finally {
            connectionDB.disconnect();
        }
        return null;
    }
}

