package com.epam.trainticketbooking.dao.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.epam.trainticketbooking.connection.ConnectionManager;

class TicketDaoImplTest {
	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement statement;

	@Mock
	private ResultSet resultSet;

	@Mock
	private ConnectionManager connectionManager;

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
		when(resultSet.getString("name")).thenReturn("pune");
		when(resultSet.getLong("id")).thenReturn(1L);
	}

}
