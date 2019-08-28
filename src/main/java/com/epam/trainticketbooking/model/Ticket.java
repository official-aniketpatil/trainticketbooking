package com.epam.trainticketbooking.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double fare;
	private String source;
	private String destination;
    @ManyToOne
	private Train train;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<Passenger> passengers;

	public Ticket(long id, double fare,String source, String destination, List<Passenger> passengers, Train train) {
		super();
		this.id = id;
		this.fare = fare;
		this.passengers = passengers;
		this.train = train;
		this.source = source;
		this.destination = destination;
	}
	
	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Ticket() {

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
}
