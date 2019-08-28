package com.epam.trainticketbooking.connection;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

class ConnectionManagerTest {

	@Test
	void testDBConnection() {
		Connection connection = new ConnectionManager().getDBConnection();
		assertNotNull(connection,"Db connection failed");
	}

}
