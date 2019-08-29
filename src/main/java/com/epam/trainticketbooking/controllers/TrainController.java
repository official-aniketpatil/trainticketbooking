package com.epam.trainticketbooking.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.connection.ConnectionManager;
import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;
import com.epam.trainticketbooking.services.BookingService;
import com.epam.trainticketbooking.services.TrainService;
import com.epam.trainticketbooking.utility.DateConversion;

@WebServlet(urlPatterns = { "/train", "/book" })
public class TrainController extends HttpServlet {

	private static final long serialVersionUID = -4838676184738977955L;
	private static Logger logger = LogManager.getLogger(TrainController.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String source = request.getParameter("source").trim();
		String destination = request.getParameter("destination").trim();
		String inputDate = request.getParameter("date").trim();
		Date date = DateConversion.convertToSqlDate(inputDate);
		TrainService trainService = new TrainService();
		List<Train> trains = trainService.findTrains(source, destination, date);
		request.setAttribute("trainsList", trains);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("show-trains.jsp");
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		List<Passenger> passengers = new ArrayList<>();
		try {
			int passengerCount = Integer.parseInt(request.getParameter("passengerCount"));
			String seatType = request.getParameter("seatType").trim();
			long trainId = Long.parseLong(request.getParameter("trainId"));
			Date date = DateConversion.convertToSqlDate(request.getParameter("date"));
			String[] passengerWithNames = request.getParameterMap().get("passengerName");
			String[] passengerWithMobileNumber = request.getParameterMap().get("mobile");
			String[] passengerWithGender = request.getParameterMap().get("gender");
			for (int i = 0; i < passengerCount; i++) {
				String name = passengerWithNames[i];
				String gender = passengerWithGender[i];
				String mobile = passengerWithMobileNumber[i];
				Passenger passenger = new Passenger(name, gender, mobile);
				passengers.add(passenger);
			}
			BookingService bookingService = new BookingService();
			//bookingService.bookTicket(passengers, trainId, seatType, passengerCount, date);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("success.jsp");
			requestDispatcher.forward(request, response); 
		} catch (NumberFormatException | ServletException | IOException ex) {
			logger.error(ex.getMessage());
		}
	}
}
