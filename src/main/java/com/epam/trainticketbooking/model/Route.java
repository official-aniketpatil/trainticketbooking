package com.epam.trainticketbooking.model;

public class Route {
	private long sourceId;
	private long destinationId;
	private long distance;
	
	public Route(long sourceId, long destinationId, long distance) {
		super();
		this.sourceId = sourceId;
		this.destinationId = destinationId;
		this.distance = distance;
	}
	
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public long getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(long destinationId) {
		this.destinationId = destinationId;
	}
	public long getDistance() {
		return distance;
	}
	public void setDistance(long distance) {
		this.distance = distance;
	}
	
	
}
