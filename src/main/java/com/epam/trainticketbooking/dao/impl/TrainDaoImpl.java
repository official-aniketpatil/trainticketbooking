package com.epam.trainticketbooking.dao.impl;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.RouteDao;
import com.epam.trainticketbooking.dao.StationDao;
import com.epam.trainticketbooking.dao.TrainDao;
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
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public TrainDaoImpl() {
		emf = Persistence.createEntityManagerFactory("local-mysql");
		em = emf.createEntityManager();
	}
	
	@Override
	public Train save(Train train) {
		em.getTransaction().begin();
		em.persist(train);
		em.getTransaction().commit();
		em.close();
		return train;
	}
	@Override
	public Train getById(long id) {
		em.getTransaction().begin();
		Train train = em.find(Train.class, id);
		em.getTransaction().commit();
		em.close();
		return train;
	}
	private long computeDistance(long trainId, long sourceId, long destinationId) {
		long distanceToSource = routeDao.getStationDistanceByTrainId(trainId, sourceId);
		long distanceToDestination = routeDao.getStationDistanceByTrainId(trainId, destinationId);
		
		return distanceToDestination - distanceToSource;
	}

	@Override
	public List<Train> getByLocation(String source, String destination) {
		return null;
	}

	@Override
	public List<Train> getAll() {
		return Collections.emptyList();
	}

	@Override
	public boolean checkAvailability(long trainId, Date date) {
		return false;
	}

	@Override
	public Map<String, Integer> getAvailableSeats(long trainId, Date date) {
		return null;
	}

	@Override
	public void updateSeatAvailability(Train train) {
	
	}
}
