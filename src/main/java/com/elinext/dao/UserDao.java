package com.elinext.dao;

import com.elinext.connection.ConnectionManager;
import com.elinext.enums.Gender;
import com.elinext.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements GenericDao<User>{

    private static volatile UserDao INSTANCE;
    private UserDao() {}

    public static UserDao getInstance() {
        if (INSTANCE == null) {
            synchronized (UserDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public User create(User entity) {
        try(Connection connection = ConnectionManager.newConnection()) {
            String sql = "INSERT INTO users (first_name, second_name, phone_number, gender, role)  VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,entity.getFirstName());
            preparedStatement.setString(2,entity.getSecondName());
            preparedStatement.setString(3,entity.getPhoneNumber());
            preparedStatement.setString(4,entity.getGender().toString());
            preparedStatement.setString(5,entity.getRole());

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
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> rooms = new ArrayList<>();
        try(Connection connection = ConnectionManager.newConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                rooms.add(new User(resultSet.getLong("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getString("phone_number"),
                        Gender.valueOf(resultSet.getString("gender")),
                        resultSet.getString("role")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
