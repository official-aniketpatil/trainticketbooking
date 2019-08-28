package com.epam.trainticketbooking.services;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.TicketDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.dao.impl.TicketDaoImpl;
import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;

public class BookingService {
	private TicketDao ticketDao = new TicketDaoImpl(new ConnectionManager());
	private TrainDao trainDao = new TrainDaoImpl(new ConnectionManager());
	
	public void bookTicket(List<Passenger> passengers,Train train,String seatType, int seatCount) {
		ticketDao.book(passengers, train, seatType, seatCount);
	}
	
	public void bookTicket(List<Passenger> passengers,long trainId,String seatType, int seatCount,Date date) {
		Train train = trainDao.getById(trainId);
		Map<String,Integer>seatTypeWithAvailability = trainDao.getAvailableSeats(trainId, date);
		train.setAcSeats(seatTypeWithAvailability.get("AC"));
		train.setSleeperSeats(seatTypeWithAvailability.get("SLEEPER"));
		train.setDate(date);
		ticketDao.book(passengers, train, seatType, seatCount);
	}
}
