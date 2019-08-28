package com.epam.trainticketbooking.services;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.model.Train;

public class TrainService {
	private TrainDao trainDao;
	
	public TrainService(TrainDao trainDao) {
		this.trainDao = trainDao;
	}

	public List<Train> findTrains(String source, String destination, Date date) {
		List<Train> trains = trainDao.getByLocation(source, destination);
		Iterator<Train> trainIterator = trains.iterator();
		while (trainIterator.hasNext()) {
			Train train = trainIterator.next();
			if (trainDao.checkAvailability(train.getId(), date)) {
				Map<String, Integer> seatTypeWithAvailableCount = trainDao.getAvailableSeats(train.getId(), date);
				train.setAcSeats(seatTypeWithAvailableCount.get("AC"));
				train.setSleeperSeats(seatTypeWithAvailableCount.get("SLEEPER"));
				train.setDate(date);
			} else {
				trainIterator.remove();
			}
		}
		return trains;
	}
}
