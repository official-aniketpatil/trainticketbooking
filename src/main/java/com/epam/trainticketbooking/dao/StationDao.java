package com.epam.trainticketbooking.dao;

public interface StationDao {
	public String getById(long id);
	public long getIdByName(String station);
}
