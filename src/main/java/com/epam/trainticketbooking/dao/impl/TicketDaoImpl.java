package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.PassengerDao;
import com.epam.trainticketbooking.dao.StationDao;
import com.epam.trainticketbooking.dao.TicketDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;

public class TicketDaoImpl implements TicketDao {
	private Logger logger = LogManager.getLogger(TicketDaoImpl.class);
	private PassengerDao passengerDao = new PassengerDaoImpl(new ConnectionManager());
	private TrainDao trainDao = new TrainDaoImpl();
	private StationDao stationDao = new StationDaoImpl(new ConnectionManager());
	private ConnectionManager connectionManager;
	private static final Double AC_FARE = 4.5;
	private static final Double SLEEPER_FARE = 3.0;
	private static final String ADD_BOOKING = "insert into bookings(passenger_id, train_id,"
			+ "source_id,destination_id,date,travel_class,fare) " + "values(?,?,?,?,?,?,?)";

	public TicketDaoImpl(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public synchronized void book(List<Passenger> passengers, Train train, String seatType, int seatCount) {
	}

	private void updateSeatCount(Train train, String seatType, int seatCount) {

	}

	private double computeFare(double distance, String seatType) {
		if (seatType.equalsIgnoreCase("AC")) {
			return distance * AC_FARE;
		}
		return distance * SLEEPER_FARE;
	}

	@Override
	public boolean cancel() {
		return false;
	}

	private boolean checkSeatAvailability(Train train, int seatCount, String seatType) {
		return false;
	}

}
