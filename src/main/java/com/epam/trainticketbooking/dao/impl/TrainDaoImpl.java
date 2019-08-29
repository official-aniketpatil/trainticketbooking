package com.epam.trainticketbooking.dao.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
	private EntityTransaction tx;

	public TrainDaoImpl() {
	}

	@Override
	public Train save(Train train) {
		emf = Persistence.createEntityManagerFactory("local-mysql");
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {	
			tx.begin();
			em.persist(train);
			tx.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
		return train;
	}

	@Override
	public Train update(Train train) {
		emf = Persistence.createEntityManagerFactory("local-mysql");
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		Train savedTrain = em.find(Train.class, train.getId());
		savedTrain.setAvailability(train.getAvailability());
		savedTrain.setTickets(train.getTickets());
		tx.commit();
		em.close();
		emf.close();
		return train;
	}

	@Override
	public Train getById(long id) {
		emf = Persistence.createEntityManagerFactory("local-mysql");
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		Train train = em.find(Train.class, id);
		tx.commit();
		em.close();
		emf.close();
		return train;
	}

	@Override
	public List<Train> getByLocation(String source, String destination) {
		Query query = em.createQuery(GET_BY_LOCATION);
		query.setParameter("source", source);
		query.setParameter("destination", destination);
		@SuppressWarnings("unchecked")
		List<Train> trains = query.getResultList();
		return trains;
	}

	@Override
	public List<Train> searchTrains(String source, String destination, Date date) {
		emf = Persistence.createEntityManagerFactory("local-mysql");
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery(GET_BY_LOCATION_AND_DATE);
		query.setParameter("source", source);
		query.setParameter("destination", destination);
		query.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Train> trains = query.getResultList();
		tx.commit();
		em.close();
		emf.close();
		return trains;
	}

	@Override
	public List<Train> getAll() {
		tx.begin();
		Query query = em.createQuery(GET_ALL_TRAINS);
		@SuppressWarnings("unchecked")
		List<Train> trains = query.getResultList();
		tx.commit();
		return trains;
	}

	@Override
	public boolean checkAvailability(Train train, Date date) {
		boolean flag = false;
		List<Availability> availabilities = train.getAvailability();
		for (Availability availability : availabilities) {
			if (availability.getDate().equals(date)) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public Map<String, Integer> getAvailableSeats(long trainId, Date date) {
		return null;
	}

}
