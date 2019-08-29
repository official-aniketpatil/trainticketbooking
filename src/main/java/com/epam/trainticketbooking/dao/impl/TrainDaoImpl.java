package com.epam.trainticketbooking.dao.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.model.Availability;
import com.epam.trainticketbooking.model.Train;

public class TrainDaoImpl implements TrainDao {
	private static Logger logger = LogManager.getLogger(TrainDaoImpl.class);

	private static final String GET_ALL_TRAINS = "select train from Train train";
	private static final String GET_BY_LOCATION = "select train from Train train " + "JOIN train.stations s "
			+ "JOIN train.availability a " + "where s.name = :source and train.id "
			+ "in(select train.id from Train train " + "JOIN train.stations s where s.name = :destination)";

	private static final String GET_BY_LOCATION_AND_DATE = "select train from Train train " + "JOIN train.stations s "
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
		try {
			em.getTransaction().begin();
			em.persist(train);
			em.getTransaction().commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			em.getTransaction().rollback();
		}
		return train;
	}

	@Override
	public Train update(Train train) {
		try {
			em.getTransaction().begin();
			Train savedTrain = em.find(Train.class, train.getId());
			savedTrain.setAvailability(train.getAvailability());
			savedTrain.setTickets(train.getTickets());
			em.getTransaction().commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			em.getTransaction().rollback();
		}
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

	@Override
	public List<Train> getByLocation(String source, String destination) {
		Query query = em.createQuery(GET_BY_LOCATION);
		query.setParameter("source", "hyderabad");
		query.setParameter("destination", "bhopal");
		@SuppressWarnings("unchecked")
		List<Train> trains = query.getResultList();
		return trains;
	}

	@Override
	public List<Train> searchTrains(String source, String destination, Date date) {
		Query query = em.createQuery(GET_BY_LOCATION_AND_DATE);
		query.setParameter("source", source);
		query.setParameter("destination", destination);
		query.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Train> trains = query.getResultList();
		return trains;
	}

	@Override
	public List<Train> getAll() {
		em.getTransaction().begin();
		Query query = em.createQuery(GET_ALL_TRAINS);
		@SuppressWarnings("unchecked")
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
	
}
