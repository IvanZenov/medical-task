package com.elinext.dao;

import com.elinext.connection.ConnectionManager;
import com.elinext.model.Manipulation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManipulationDao implements GenericDao<Manipulation> {

    private static volatile ManipulationDao INSTANCE;
    public static ManipulationDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ManipulationDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ManipulationDao();
                }
            }
        }
        return INSTANCE;
    }
    private ManipulationDao() {}

    @Override
    public Manipulation create(Manipulation entity) {
        try(Connection connection = ConnectionManager.newConnection()) {
            String sql = "INSERT INTO manipulation (name, description) VALUES (?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Manipulation findById(Long id) {
        try(Connection connection = ConnectionManager.newConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM manipulation WHERE manipulation_id = ?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Manipulation(
                        resultSet.getLong("manipulation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Manipulation> findAll() {
        List<Manipulation> reservations = new ArrayList<>();
        try(Connection connection = ConnectionManager.newConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from manipulation;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                reservations.add(new Manipulation(
                        resultSet.getLong("manipulation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
