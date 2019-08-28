package com.epam.trainticketbooking.exceptions;

public class DBConnectionFailedException extends RuntimeException {
	private static final long serialVersionUID = 6913062679152490381L;

	public DBConnectionFailedException(String message) {
		super(message);
	}
}
