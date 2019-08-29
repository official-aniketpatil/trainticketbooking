package com.epam.trainticketbooking.dao.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.dao.TicketDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Ticket;
import com.epam.trainticketbooking.model.Train;

public class TicketDaoImpl implements TicketDao {
	private TrainDao trainDao = new TrainDaoImpl();
	private Logger logger = LogManager.getLogger(TicketDao.class);
	
	@Override
	public synchronized Ticket book(BookingDetail bookingDetail) {
		Train train = bookingDetail.getTrain();
		String source = bookingDetail.getSource();
		double fare = bookingDetail.getFare(); 
		String destination = bookingDetail.getDestination();
		List<Passenger> passengers = bookingDetail.getPassengers();		
		Ticket ticket = new Ticket(source, destination, passengers, train, fare);
		train.getTickets().add(ticket);
		trainDao.update(train);
		return ticket;
	}
	
	@Override
	public boolean cancel() {
		return false;
	}
}
