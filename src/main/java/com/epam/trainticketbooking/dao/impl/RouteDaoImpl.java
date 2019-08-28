package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.RouteDao;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;
import com.epam.trainticketbooking.exceptions.DataAccessException;

public class RouteDaoImpl implements RouteDao {
	private Logger logger = LogManager.getLogger(RouteDaoImpl.class);
	private static final String GET_STATION_DISTANCE = "select distance from routes "
			+ "where train_id = ? and station_id = ?";
	private ConnectionManager connectionManager;
	
	public RouteDaoImpl(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	@Override
	public long getStationDistanceByTrainId(long trainId, long stationId) {
		ResultSet rs = null;
		long distance = 0L;
		try(Connection connection = connectionManager.getDBConnection();
				PreparedStatement stmt = connection.prepareStatement(GET_STATION_DISTANCE);){
			stmt.setLong(1, trainId);
			stmt.setLong(2, stationId);
			rs = stmt.executeQuery();
			while(rs.next()) {
				distance = rs.getLong("distance");
			}
		} catch(DBConnectionFailedException | SQLException | NullPointerException ex) {
			logger.error(ex.getMessage());
			throw new DataAccessException("unable to get distance");
		} finally {
			closeResource(rs);
		}
		return distance;
	}
	
	private void closeResource(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

}
