package com.epam.trainticketbooking.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.epam.trainticketbooking.model.Train;

public interface TrainDao {
	public Train getById(long id);
	public List<Train> getByLocation(String source,String destination);
	public List<Train> getAll();
	public boolean checkAvailability(long trainId,Date date);
	public Map<String,Integer> getAvailableSeats(long trainId,Date date);
	public void updateSeatAvailability(Train train);
}
