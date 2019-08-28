package com.epam.trainticketbooking.dao;

import java.util.List;

import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;

public interface TicketDao {

	public boolean cancel();

	public void book(List<Passenger> passenger, Train train, String seatType, int seatCount);
}
