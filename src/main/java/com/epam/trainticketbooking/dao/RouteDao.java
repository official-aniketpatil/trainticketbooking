package com.epam.trainticketbooking.dao;

public interface RouteDao {
	public long getStationDistanceByTrainId(long trainId, long stationId);
}