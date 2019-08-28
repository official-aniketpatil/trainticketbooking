package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.RouteDao;
import com.epam.trainticketbooking.dao.StationDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;
import com.epam.trainticketbooking.model.Train;

public class TrainDaoImpl implements TrainDao {
	private static Logger logger = LogManager.getLogger(TrainDaoImpl.class);
	private StationDao stationDao = new StationDaoImpl(new ConnectionManager());
	private RouteDao routeDao = new RouteDaoImpl(new ConnectionManager());
	private static final String FAILED_CONNECTION_MESSAGE = "connection to database failed";
	private static final String SET_TRAIN_ID_AND_DATE = "where train_id = ? and date = ?";
	private static final String GET_BY_LOCATION = "select train_id from routes "
			+ "where station_id = ? and train_id in "
			+ "(select train_id from routes where station_id = ?)";
	private static final String GET_BY_ID = "select * from trains where id = ?";
	private static final String GET_AVAILABLE_SEATS = "select ac_seats,sleeper_seats "
			+ "from availability "
			+ SET_TRAIN_ID_AND_DATE;
	private static final String CHECK_AVAILABILITY = "select * from availability "
			+ SET_TRAIN_ID_AND_DATE;
	private static final String UPDATE_AVAILABLE_SEATS = "update availability "
			+ "set ac_seats = ?, sleeper_seats = ? "
			+ SET_TRAIN_ID_AND_DATE;
	private ConnectionManager connectionManager;
	
	public TrainDaoImpl(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	@Override
	public Train getById(long id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("local-mysql");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Train train = em.find(Train.class, id);
		em.getTransaction().commit();
		return train;
	}
	private long computeDistance(long trainId, long sourceId, long destinationId) {
		long distanceToSource = routeDao.getStationDistanceByTrainId(trainId, sourceId);
		long distanceToDestination = routeDao.getStationDistanceByTrainId(trainId, destinationId);
		
		return distanceToDestination - distanceToSource;
	}

	@Override
	public List<Train> getByLocation(String source, String destination) {
		List<Train> trains = new ArrayList<>();
		ResultSet rs = null;

		try (Connection connection = connectionManager.getDBConnection();
				PreparedStatement stmt = connection.prepareStatement(GET_BY_LOCATION);) {
			long sourceId = stationDao.getIdByName(source);
			long destinationId = stationDao.getIdByName(destination);

			stmt.setLong(1, sourceId);
			stmt.setLong(2, destinationId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				long trainId = rs.getLong("train_id");
				Train train = getById(trainId);
				train.setSource(source);
				train.setDestination(destination);
				long distance = computeDistance(trainId, sourceId, destinationId);
				train.setDistance(distance);
				trains.add(train);
			}

		} catch (DBConnectionFailedException ex) {
			logger.error(ex.getMessage());
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
		} finally {
			closeResource(rs);
		}
		return trains;
	}

	@Override
	public List<Train> getAll() {
		return Collections.emptyList();
	}

	@Override
	public boolean checkAvailability(long trainId, Date date) {
		ResultSet rs = null;
		try (Connection conn = connectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_AVAILABLE_SEATS);) {
			stmt.setLong(1, trainId);
			stmt.setDate(2, (java.sql.Date) date);
			rs = stmt.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (DBConnectionFailedException e) {
			logger.error(FAILED_CONNECTION_MESSAGE);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			closeResource(rs);
		}
		return false;
	}

	@Override
	public Map<String, Integer> getAvailableSeats(long trainId, Date date) {
		Map<String, Integer> seatTypeWithAvailableCount = new HashMap<>();
		ResultSet rs = null;

		try (Connection conn = connectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(CHECK_AVAILABILITY);) {
			stmt.setLong(1, trainId);
			stmt.setString(2, date.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				seatTypeWithAvailableCount.put("AC", rs.getInt("ac_seats"));
				seatTypeWithAvailableCount.put("SLEEPER", rs.getInt("sleeper_seats"));
			}
		} catch (DBConnectionFailedException e) {
			logger.error(FAILED_CONNECTION_MESSAGE);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			closeResource(rs);
		}

		return seatTypeWithAvailableCount;
	}

	@Override
	public void updateSeatAvailability(Train train) {
		long trainId = train.getId();
		Date date = train.getDate();
		int acSeats = train.getAcSeats();
		int sleeperSeats = train.getSleeperSeats();
		try(Connection connection = connectionManager.getDBConnection();
			PreparedStatement stmt = connection.prepareStatement(UPDATE_AVAILABLE_SEATS)){
			stmt.setInt(1, acSeats);
			stmt.setInt(2, sleeperSeats);
			stmt.setLong(3, trainId);
			stmt.setDate(4, date);
			stmt.execute();
		} catch(DBConnectionFailedException | SQLException ex) {
			logger.error(ex.getMessage());
		}
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
