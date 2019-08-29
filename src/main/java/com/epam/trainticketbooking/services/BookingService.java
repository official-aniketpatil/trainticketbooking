package com.epam.trainticketbooking.services;

import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.dao.TicketDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.dao.impl.TicketDaoImpl;
import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.model.Availability;
import com.epam.trainticketbooking.model.Station;
import com.epam.trainticketbooking.model.Train;

public class BookingService {
	private TicketDao ticketDao = new TicketDaoImpl();
	private TrainDao trainDao = new TrainDaoImpl();
	private static final Double AC_FARE = 4.5;
	private static final Double SLEEPER_FARE = 3.0;
	private static Logger logger = LogManager.getLogger(BookingService.class);

	public synchronized void bookTicket(BookingDetail bookingDetail) {
		long trainId = bookingDetail.getTrainId();
		Train train = trainDao.getById(trainId);
		String seatType = bookingDetail.getSeatType();
		String source = bookingDetail.getSource();
		String destination = bookingDetail.getDestination();
		int seatCount = bookingDetail.getSeatCount();
		Date date = bookingDetail.getDate();
		if (trainDao.checkAvailability(train, date) && isSeatAvailableToBook(train, date, seatType, seatCount)) {
			updateSeatCount(train, seatType, seatCount, date);
			long distance = computeDistanceBetweenStations(train, source, destination);
			double fare = computeFare(distance, seatType);
			bookingDetail.setFare(fare);
			ticketDao.book(bookingDetail);
		} else {
			logger.info("Train is not available");
		}
	}

	private Map<String, Integer> getAvailableSeats(Train train, Date date) {
		List<Availability> availabilities = train.getAvailability();
		Map<String, Integer> seatTypeWithAvailableCount = new HashMap<>();
		for (Availability availability : availabilities) {
			if (availability.getDate().equals(date)) {
				seatTypeWithAvailableCount.put("AC", availability.getAcSeats());
				seatTypeWithAvailableCount.put("SLEEPER", availability.getSleeperSeats());
			}
		}
		return seatTypeWithAvailableCount;
	}

	private boolean isSeatAvailableToBook(Train train, Date date, String seatType, int seatCount) {
		Map<String, Integer> seatTypeWithAvailableCount = getAvailableSeats(train, date);
		return seatTypeWithAvailableCount.get(seatType.toUpperCase()) >= seatCount;
	}

	private void updateSeatCount(Train train, String seatType, int seatCount, Date date) {
		List<Availability> availabilities = train.getAvailability();
		Iterator<Availability> iterator = availabilities.listIterator();
		while (iterator.hasNext()) {
			Availability availability = iterator.next();
			if (availability.getDate().equals(date)) {
				if (seatType.equalsIgnoreCase("AC")) {
					int seats = availability.getAcSeats() - seatCount;
					availability.setAcSeats(seats);
				} else {
					int seats = availability.getSleeperSeats() - seatCount;
					availability.setSleeperSeats(seats);
				}
			}
		}
	}

	private double computeFare(long distance, String seatType) {
		if (seatType.equalsIgnoreCase("AC")) {
			return distance * AC_FARE;
		}
		return distance * SLEEPER_FARE;
	}

	private long computeDistanceBetweenStations(Train train, String source, String destination) {
		List<Station> stations = train.getStations();
		long distanceToSource = 0;
		long distanceToDestination = 0;

		for (Station station : stations) {
			if (station.getName().equalsIgnoreCase(source)) {
				distanceToSource = station.getDistance();
			} else if (station.getName().equalsIgnoreCase(destination)) {
				distanceToDestination = station.getDistance();
			}
		}
		return distanceToDestination - distanceToSource;
	}

}
