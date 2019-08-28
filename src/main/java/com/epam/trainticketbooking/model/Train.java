package com.epam.trainticketbooking.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "train")
public class Train {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String source;
	private String destination;
	@ElementCollection(fetch = FetchType.LAZY)
	private List<Station> stations;

	@ElementCollection(fetch = FetchType.LAZY)
	private List<Availability> availability;

	public Train(List<Station> stations, List<Availability> availability, String sourceStation,
			String destinationStation) {
		super();
		this.availability = availability;
		this.source = sourceStation;
		this.destination = destinationStation;
		this.stations = stations;
	}

	public Train() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Availability> getAvailability() {
		return availability;
	}

	public void setAvailability(List<Availability> availability) {
		this.availability = availability;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String sourceStation) {
		this.source = sourceStation;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destinationStation) {
		this.destination = destinationStation;
	}

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	@Override
	public String toString() {
		return "Train [id=" + id + ", sourceStation=" + source + ", destinationStation=" + destination
				+ ", stations=" + stations + ", availability=" + availability + "]";
	}

}
