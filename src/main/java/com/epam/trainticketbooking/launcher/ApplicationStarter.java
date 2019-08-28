package com.epam.trainticketbooking.launcher;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;
import com.epam.trainticketbooking.services.BookingService;
import com.epam.trainticketbooking.services.TrainService;

public class ApplicationStarter {
	private static Logger logger = LogManager.getLogger(ApplicationStarter.class);
	private static final int SEARCH_TRAINS = 1;
	private static final int BOOK_TRAIN = 2;

	public static void main(String[] args) {
		TrainService trainService = new TrainService(new TrainDaoImpl(new ConnectionManager()));
		BookingService bookingService = new BookingService();
		ConsoleOperations.showServiceMenu();
		int choice = ConsoleOperations.getInt();

		if (choice == SEARCH_TRAINS) {
			ConsoleOperations.showAvailableTrainsMenu();
			String source = ConsoleOperations.getString();
			String destination = ConsoleOperations.getString();
			Date date = ConsoleOperations.getDate();
			List<Train> trains = trainService.findTrains(source, destination, date);
			logger.trace(trains.toString());
		} else if (choice == BOOK_TRAIN) {
			ConsoleOperations.showAvailableTrainsMenu();
			String source = ConsoleOperations.getString();
			String destination = ConsoleOperations.getString();
			Date date = ConsoleOperations.getDate();
			List<Train> trains = trainService.findTrains(source, destination, date);
			logger.trace(trains.toString());
			ConsoleOperations.showTrainBookingMenu();
			long trainId = ConsoleOperations.getLong();
			String seatType = ConsoleOperations.getString();
			int seatCount = ConsoleOperations.getInt();
			List<Passenger> passengers = new ArrayList<>();
			for(int i = 0; i < seatCount; i++) {
				passengers.add(ConsoleOperations.getPassenger());
			}
			Train train = trains.stream().filter(t->t.getId() == trainId).collect(Collectors.toList()).get(0);
			bookingService.bookTicket(passengers, train, seatType, seatCount);
		} else {
			logger.error("Enter a valid choice");
		}

	}

}
