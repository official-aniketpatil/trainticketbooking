package com.epam.trainticketbooking.model;

abstract class Ticket {
	private long id;
	private double fare;
	private String source;
	private String destination;
	
	public Ticket(long id, double fare, String source, String destination) {
		super();
		this.id = id;
		this.fare = fare;
		this.source = source;
		this.destination = destination;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}	
}
