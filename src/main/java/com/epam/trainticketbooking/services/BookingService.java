package com.epam.trainticketbooking.services;

import java.util.List;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.TicketDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.dao.impl.TicketDaoImpl;
import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;

public class BookingService {
	private TicketDao ticketDao = new TicketDaoImpl(new ConnectionManager());
	private TrainDao trainDao = new TrainDaoImpl();
	
	public void bookTicket(List<Passenger> passengers,Train train,String seatType, int seatCount) {
		ticketDao.book(passengers, train, seatType, seatCount);
	}
	
}
