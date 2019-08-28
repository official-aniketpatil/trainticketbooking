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
		return null;
	}
}
