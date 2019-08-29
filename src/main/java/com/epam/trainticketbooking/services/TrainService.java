package com.epam.trainticketbooking.services;

import java.sql.Date;
import java.util.List;

import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.model.Train;

public class TrainService {
	private TrainDao trainDao;
	
	public TrainService() {
		this.trainDao = new TrainDaoImpl();
	}

	public List<Train> findTrains(String source, String destination, Date date) {
		return trainDao.searchTrains(source, destination, date);
	}
	
	
}
