package com.epam.trainticketbooking.dao;

import com.epam.trainticketbooking.model.Passenger;

public interface PassengerDao {
	public Passenger add(Passenger passenger);
	public Passenger getById(long id);
}
