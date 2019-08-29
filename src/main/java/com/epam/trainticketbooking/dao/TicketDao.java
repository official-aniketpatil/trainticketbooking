package com.epam.trainticketbooking.dao;

import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.model.Ticket;

public interface TicketDao {

	public boolean cancel();

	public Ticket book(BookingDetail bookingDetail);
}
