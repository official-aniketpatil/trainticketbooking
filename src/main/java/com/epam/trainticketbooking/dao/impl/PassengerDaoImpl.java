package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.PassengerDao;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;
import com.epam.trainticketbooking.exceptions.DataAccessException;
import com.epam.trainticketbooking.exceptions.DataWriteException;
import com.epam.trainticketbooking.model.Passenger;

public class PassengerDaoImpl implements PassengerDao {
	private static Logger logger = LogManager.getLogger(PassengerDaoImpl.class);
	private static final String ADD_PASSENGER = "insert into passengers(name,gender,mobile) values(?, ?, ?)";
	private static final String GET_PASSENGER_BY_ID = "select * from passengers where id = ?";
	private static final String GET_LAST_PASSENGER_ID = "select max(id) as id from passengers";
	private ConnectionManager connectionManager;

	public PassengerDaoImpl(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public Passenger add(Passenger passenger) {
		try (Connection connection = connectionManager.getDBConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_PASSENGER);) {
			statement.setString(1, passenger.getName());
			statement.setString(2, passenger.getGender());
			statement.setString(3, passenger.getMobile());
			statement.execute();
			passenger.setId(getLastAddedPassengerId());
			return passenger;
		} catch (DBConnectionFailedException | SQLException | NullPointerException ex) {
			logger.error(ex.getMessage());
			throw new DataWriteException("Unable to add Passenger");
		}
	}

	@Override
	public Passenger getById(long id) {
		Passenger passenger = null;
		ResultSet resultSet = null;

		try (Connection connection = connectionManager.getDBConnection();
				PreparedStatement statement = connection.prepareStatement(GET_PASSENGER_BY_ID);) {
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String gender = resultSet.getString("gender");
				String mobile = resultSet.getString("mobile");
				passenger = new Passenger(id, name, gender, mobile);
			}
			return passenger;
		} catch (DBConnectionFailedException | SQLException ex) {
			logger.error(ex.getMessage());
			throw new DataAccessException("Unable to get Passenger");
		} finally {
			closeResource(resultSet);
		}
	}
	
	private long getLastAddedPassengerId() {
		ResultSet resultset = null;
		long id = 0;
		try(Connection connection = connectionManager.getDBConnection();
			Statement statement = connection.createStatement();){
			resultset = statement.executeQuery(GET_LAST_PASSENGER_ID);
			while(resultset.next()) {
				id = resultset.getLong("id");
			}
			return id;
		} catch(DBConnectionFailedException | SQLException ex) {
			logger.error(ex.getMessage());
			throw new DataAccessException("Unable to Access last added passenger id");
		} finally {
			closeResource(resultset);
		}
	}
	private void closeResource(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException ex) {
			logger.error("unable to close resultset");
		}
	}

}
