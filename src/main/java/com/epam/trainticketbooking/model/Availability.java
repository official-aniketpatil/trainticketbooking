package com.epam.trainticketbooking.model;

import java.sql.Date;

import javax.persistence.Embeddable;

@Embeddable
public class Availability {
	private Date date;
	private int acSeats;
	private int sleeperSeats;

	public Availability() {
	}

	public Availability(Date date, int acSeats, int sleeperSeats) {
		this.date = date;
		this.acSeats = acSeats;
		this.sleeperSeats = sleeperSeats;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	@Override
	public String toString() {
		return "Availability [date=" + date + ", acSeats=" + acSeats + ", sleeperSeats=" + sleeperSeats + "]";
	}

}