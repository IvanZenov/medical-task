package com.elinext.service;

import com.elinext.connection.ConnectionManager;
import com.elinext.dao.ReservationDao;
import com.elinext.dto.ReservationDto;
import com.elinext.exceptions.ProblemWithReservationException;
import com.elinext.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private ReservationDao reservationDao;
    private static volatile ReservationService INSTANCE;
    private ReservationService(){
        reservationDao = ReservationDao.getInstance();
    }
    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            synchronized (ReservationService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReservationService();
                }
            }
        }
        return INSTANCE;
    }

    public List<ReservationDto> findAll () {
        List<ReservationDto> reservations = new ArrayList<>();
        try(Connection connection = ConnectionManager.newConnection()) {
            String sql = "SELECT rs.reservation_id, rs.start_date,rs.end_date,rs.active,r.room_number,r.type,rs.user_id,CONCAT_WS(' ',u.first_name,u.second_name) AS username,m.name " +
                    "FROM reservation AS rs " +
                    "JOIN room AS r ON rs.room_id = r.room_id " +
                    "JOIN users AS u ON u.user_id = rs.user_id " +
                    "JOIN manipulation AS m ON m.manipulation_id = rs.manipulation_id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                reservations.add(new ReservationDto(
                        resultSet.getLong("reservation_id"),
                        resultSet.getTimestamp("start_date"),
                        resultSet.getTimestamp("end_date"),
                        resultSet.getBoolean("active"),
                        resultSet.getInt("room_number"),
                        resultSet.getString("type"),
                        resultSet.getString("username"),
                        resultSet.getLong("user_id"),
                        resultSet.getString("name")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    //Two 'same methods: getRoomReservation/gerUserReservation' for possible future use
    public List<Reservation> getRoomReservation (Long roomId, Date startDate, Date endDate) {
        List<Reservation> reservations = new ArrayList<>();
        try(Connection connection = ConnectionManager.newConnection()) {
            String sql = "SELECT * FROM reservation as r where (? between r.start_date and r.end_date or ? between r.start_date and r.end_date) AND r.room_id = ? AND active = true";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
            preparedStatement.setLong(3, roomId);

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

    public List<Reservation> getUserReservation (Long userId, Date startDate, Date endDate) {
        List<Reservation> reservations = new ArrayList<>();
        try(Connection connection = ConnectionManager.newConnection()) {
            String sql = "SELECT * FROM reservation as r where (? between r.start_date and r.end_date or ? between r.start_date and r.end_date) AND r.user_id = ? AND active = true ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
            preparedStatement.setLong(3, userId);

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

    public void add (Date start, Date end, Long roomId, Long userId, Long manipulationId) {
        List<Reservation> roomReservation = getRoomReservation(roomId,start,end);
        List<Reservation> userReservation = getUserReservation(userId,start,end);

        if (roomReservation.isEmpty()) {
            if (userReservation.isEmpty()){
                reservationDao.create(new Reservation(start,end,true,roomId,userId,manipulationId));
            }
            else {
               throw new ProblemWithReservationException("You already have a reservation for this time");
            }
        }
        else {
            throw new ProblemWithReservationException("This room is already booked for this time");
        }
    }

    public void cancelReservation (Long reservationId) {
        try(Connection connection = ConnectionManager.newConnection()) {
            String sql = "update reservation set active=? where reservation_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1,false);
            preparedStatement.setLong(2,reservationId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateActiveStatusWithCurrentTime() {
        try (Connection connection = ConnectionManager.newConnection()){
            String sql = "UPDATE reservation " +
                    "SET active = false WHERE reservation_id IN (" +
                    "  SELECT reservation_id FROM (SELECT reservation_id FROM reservation where active = true AND end_date < NOW()) as tmp" +
                    ");";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
