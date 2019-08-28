package com.epam.trainticketbooking.launcher;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.model.Availability;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Route;
import com.epam.trainticketbooking.model.Station;
import com.epam.trainticketbooking.model.Train;
import com.epam.trainticketbooking.services.BookingService;
import com.epam.trainticketbooking.services.TrainService;
import com.epam.trainticketbooking.utility.DateConversion;

public class ApplicationStarter {
	private static Logger logger = LogManager.getLogger(ApplicationStarter.class);
	private static final int SEARCH_TRAINS = 1;
	private static final int BOOK_TRAIN = 2;

	public static void main(String[] args) {
		TrainService trainService = new TrainService(new TrainDaoImpl());
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
			Train train = new Train(new ArrayList<Station>(), new ArrayList<Availability>(),"pune","bhopal");
			Availability dayOne = new Availability(DateConversion.convertToSqlDate("11-11-2011"), 10, 10);
			Availability dayTwo = new Availability(DateConversion.convertToSqlDate("12-11-2011"), 10, 10);
			Station s1 = new Station("jalgaon", 5);
			Station s2 = new Station("bhopal", 10);
			train.getAvailability().add(dayOne);
			train.getAvailability().add(dayTwo);
			train.getStations().add(s1);
			train.getStations().add(s2);
			new TrainDaoImpl().save(train);
		}

	}

}
