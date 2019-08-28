package com.epam.trainticketbooking.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trains")
public class Train {
	@Id
	private long id;
	private String source;
	private String destination;
	private Date date;
	private long distance;
	private int acSeats;
	private int sleeperSeats;

	public Train(long id, String source, String destination, Date date, long distance, int acSeats, int sleeperSeats) {
		super();
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.distance = distance;
		this.acSeats = acSeats;
		this.sleeperSeats = sleeperSeats;
		this.date = date;
	}

	public Train() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public int getAcSeats() {
		return acSeats;
	}

	public void setAcSeats(int acSeats) {
		this.acSeats = acSeats;
	}

	public int getSleeperSeats() {
		return sleeperSeats;
	}

	public void setSleeperSeats(int sleeperSeats) {
		this.sleeperSeats = sleeperSeats;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Train [id=" + id + ", source=" + source + ", destination=" + destination + ", distance=" + distance
				+ ", acSeats=" + acSeats + ", sleeperSeats=" + sleeperSeats + "]";
	}

}
