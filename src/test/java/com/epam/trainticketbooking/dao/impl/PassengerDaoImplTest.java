package com.epam.trainticketbooking.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.PassengerDao;
import com.epam.trainticketbooking.exceptions.DataWriteException;
import com.epam.trainticketbooking.model.Passenger;

class PassengerDaoImplTest {

	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement statement;

	@Mock
	private ResultSet resultSet;

	@Mock
	private ConnectionManager connectionManager;

	private PassengerDao passengerDao;
	private Passenger expectedPassenger;

	@BeforeEach
	void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		when(connectionManager.getDBConnection()).thenReturn(connection);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		when(statement.execute()).thenReturn(true);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenAnswer(new Answer<Boolean>() {
		    private int iterations = 1;

		    public Boolean answer(InvocationOnMock invocation) {
		        return iterations-- > 0;
		    }
		});
		when(resultSet.getString("name")).thenReturn("aniket");
		when(resultSet.getString("gender")).thenReturn("male");
		when(resultSet.getString("mobile")).thenReturn("9145752925");
		expectedPassenger = new Passenger(1, "aniket", "male", "9145752925");
		passengerDao = new PassengerDaoImpl(connectionManager);
	}

	@Test
	void testAdd() {
		Passenger actualPassenger = passengerDao.add(expectedPassenger);
		assertEquals(actualPassenger.getName(),expectedPassenger.getName());
		assertEquals(actualPassenger.getGender(), expectedPassenger.getGender());
		assertEquals(actualPassenger.getMobile(), expectedPassenger.getMobile());
		assertThrows(DataWriteException.class, ()-> passengerDao.add(null));
	}
	
	@Test
	void testGetById() {
		Passenger actualPassenger = passengerDao.getById(1);
		assertEquals(expectedPassenger, actualPassenger);
	}

}
