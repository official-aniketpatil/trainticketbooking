package com.epam.trainticketbooking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.model.Train;
import com.epam.trainticketbooking.utility.DateConversion;

class TrainServiceTest{
	
	@Mock
	private ConnectionManager connectionManager;
	
	@Mock
	Map<String, Integer> seatTypeWithAvailableCount;
	
	@Mock
	private Iterator<Train> trainIterator;
	
	@Mock
	private List<Train> trains;
	
	@Mock
	private Train train;
	
	@Mock
	private TrainDao trainDao;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		when(trainDao.getByLocation("pune","bhopal")).thenReturn(trains);
		when(trains.iterator()).thenReturn(trainIterator);
		when(trainDao.checkAvailability(any(Long.class), any(Date.class))).thenReturn(true);
		when(trainDao.getAvailableSeats(any(Long.class), any(Date.class))).thenReturn(seatTypeWithAvailableCount);
		when(seatTypeWithAvailableCount.get("AC")).thenReturn(10);
		when(seatTypeWithAvailableCount.get("SLEEPER")).thenReturn(10);
	}
	@Test
	void testFindTrains() {
		TrainService trainService = new TrainService(trainDao);
		List<Train> actual = trainService.findTrains("pune", "bhopal",DateConversion.convertToSqlDate("12-08-2019"));
		assertEquals(trains, actual);
	}

}
