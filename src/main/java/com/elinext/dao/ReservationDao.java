package com.elinext.dao;

import com.elinext.connection.ConnectionManager;
import com.elinext.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao implements GenericDao<Reservation> {

    private static volatile ReservationDao INSTANCE;
    private ReservationDao(){}
    public static ReservationDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ReservationDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReservationDao();
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public Reservation create(Reservation entity) {
        try(Connection connection = ConnectionManager.newConnection()) {
            String sql = "INSERT INTO reservation (start_date,end_date,active,room_id,user_id,manipulation_id) VALUES (?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, new Timestamp(entity.getStartDate().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(entity.getEndDate().getTime()));
            preparedStatement.setBoolean(3, entity.isActive());
            preparedStatement.setLong(4, entity.getRoomId());
            preparedStatement.setLong(5, entity.getUserId());
            preparedStatement.setLong(6, entity.getManipulationId());

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
    public Reservation findById(Long id) {
        try(Connection connection = ConnectionManager.newConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reservation WHERE reservation_id = ?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getBoolean("active"),
                        resultSet.getLong("room_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("manipulation_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        try(Connection connection = ConnectionManager.newConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from reservation;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                reservations.add(new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getBoolean("active"),
                        resultSet.getLong("room_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("manipulation_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


}
