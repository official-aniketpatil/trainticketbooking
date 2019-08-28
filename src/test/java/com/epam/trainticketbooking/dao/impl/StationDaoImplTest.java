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
import com.epam.trainticketbooking.dao.StationDao;
import com.epam.trainticketbooking.exceptions.DataAccessException;

class StationDaoImplTest {
	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement statement;

	@Mock
	private ResultSet resultSet;

	@Mock
	private ConnectionManager connectionManager;

	private StationDao stationDao;

	@BeforeEach
	void setUp() throws SQLException  {
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
		when(resultSet.getString("name")).thenReturn("pune");
		when(resultSet.getLong("id")).thenReturn(1L);
		stationDao = new StationDaoImpl(connectionManager);
	}

	@Test
	void testGetById() {
		StationDao sd = new StationDaoImpl(null);
		String actualStation = stationDao.getById(1);
		assertEquals("pune", actualStation);
		assertThrows(DataAccessException.class,() -> sd.getById(1));
	}

	@Test
	void getIdByName() {
		StationDao sd = new StationDaoImpl(null);
		long actualId = stationDao.getIdByName("pune");
		assertEquals(1, actualId);
		assertThrows(DataAccessException.class,() -> sd.getIdByName("pune"));
	}
}
