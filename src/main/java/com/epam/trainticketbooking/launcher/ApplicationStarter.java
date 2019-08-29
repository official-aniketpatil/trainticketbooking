package com.epam.trainticketbooking.launcher;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.model.Availability;
import com.epam.trainticketbooking.model.Passenger;
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
		TrainService trainService = new TrainService();
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
			BookingDetail bookingDetail = new BookingDetail(passengers, source, destination, date, seatType, seatCount, trainId);
			bookingService.bookTicket(bookingDetail);
		} else {
			logger.error("Enter a valid choice");
			Availability dayOne = new Availability(DateConversion.convertToSqlDate("11-11-2011"), 10, 10);
			Availability dayTwo = new Availability(DateConversion.convertToSqlDate("12-11-2011"), 10, 10);
			//Station s0 = new Station("chennai", 0);
			Station s1 = new Station("hyderabad", 0);
			Station s2 = new Station("pune", 500);
			Station s3 = new Station("bhopal", 900);
			Train train = new Train(new ArrayList<Station>(), new ArrayList<Availability>(), "hyderabad", "bhopal");
			train.getAvailability().add(dayOne);
			train.getAvailability().add(dayTwo);
			//train.getStations().add(s0);
			train.getStations().add(s1);
			train.getStations().add(s2);
			train.getStations().add(s3);
			new TrainDaoImpl().save(train);
		}

	}

}
