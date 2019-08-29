package com.epam.trainticketbooking.dao.impl;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.RouteDao;
import com.epam.trainticketbooking.dao.StationDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.model.Availability;
import com.epam.trainticketbooking.model.Station;
import com.epam.trainticketbooking.model.Train;

public class TrainDaoImpl implements TrainDao {
	private static Logger logger = LogManager.getLogger(TrainDaoImpl.class);

	private static final String GET_ALL_TRAINS = "select train from Train train";
	private static final String GET_BY_LOCATION = "select train from Train train " + "JOIN train.stations s "
			+ "JOIN train.availability a " + "where s.name = :source and train.id "
			+ "in(select train.id from Train train "
			+ "JOIN train.stations s where s.name = :destination) and a.date = :date";

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

		return 0;
	}

	@Override
	public List<Train> getByLocation(String source, String destination) {
		Query query = em.createQuery(GET_BY_LOCATION);
		query.setParameter("source", "hyderabad");
		query.setParameter("destination", "bhopal");
		List<Train> trains = query.getResultList();
		return trains;
	}

	@Override
	public List<Train> searchTrains(String source, String destination, Date date) {
		Query query = em.createQuery(GET_BY_LOCATION);
		query.setParameter("source", source);
		query.setParameter("destination", destination);
		query.setParameter("date", date);
		List<Train> trains = query.getResultList();
		logger.trace(trains.toString());
		return trains;
	}

	@Override
	public List<Train> getAll() {
		em.getTransaction().begin();
		Query query = em.createQuery(GET_ALL_TRAINS);
		List<Train> trains = query.getResultList();
		em.getTransaction().commit();
		em.close();
		return trains;
	}

	@Override
	public boolean checkAvailability(Train train, Date date) {
		List<Availability> availabilities = train.getAvailability();
		for (Availability availability : availabilities) {
			if (availability.getDate().equals(date)) {
				return true;
			}
		}
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
