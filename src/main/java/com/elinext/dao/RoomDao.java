package com.elinext.dao;

import com.elinext.connection.ConnectionManager;
import com.elinext.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RoomDao implements GenericDao<Room> {

    private static volatile RoomDao INSTANCE;
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static RoomDao getInstance() {
        if (INSTANCE == null) {
            synchronized (RoomDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RoomDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Room create(Room entity) {
        try(Connection connection = ConnectionManager.newConnection()) {
            readWriteLock.writeLock().lock();
            String sql = "INSERT INTO room (room_number, type) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,entity.getRoomNumber());
            preparedStatement.setString(2,entity.getType());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
        return entity;
    }

    @Override
    public Room findById(Long id) {
        try(Connection connection = ConnectionManager.newConnection()) {
            readWriteLock.readLock().lock();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM room WHERE room_id = ?;");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Room(
                        resultSet.getLong("room_id"),
                        resultSet.getInt("room_number"),
                        resultSet.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            readWriteLock.readLock().unlock();
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try(Connection connection = ConnectionManager.newConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM room");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                rooms.add(new Room(resultSet.getLong("room_id"),
                        resultSet.getInt("room_number"),
                        resultSet.getString("type")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
