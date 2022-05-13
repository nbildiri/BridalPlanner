package BridalPlanner;

import java.time.LocalDate;

public class Event implements Comparable<Event> {
	//what the event is
		private EventAppointments appointment;
		private String eventTitle;
		private String customTitle;
		private EventBookings booking;
		private LocalDate date;	
		private String location;
		private String appointmentNote;
	
	public Event(EventAppointments title, LocalDate date, String address, String note) {
		this.eventTitle = title.name();
		this.date = date;
		this.location = address;
		this.appointmentNote = note;
		}
	
	//for custom appointment
	public Event(String appTitle,LocalDate date, String address, String note) {
		this.eventTitle = appTitle;
		this.date = date;
		this.location = address;
		this.appointmentNote = note;
	}
		
	public Event(EventBookings book, String title) {
		
		this.booking = book;
		this.eventTitle = title;
	}

	

	public EventAppointments getAppointment() {
		return appointment;
	}

	public void setAppointment(EventAppointments appointment) {
		this.appointment = appointment;
	}

	public EventBookings getBooking() {
		return booking;
	}

	public void setBooking(EventBookings booking) {
		this.booking = booking;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String address) {
		this.location = address;
	}
	
	public String getAppointmentNote() {
		return appointmentNote;
	}

	public void setAppointmentNote(String appointmentNote) {
		this.appointmentNote = appointmentNote;
	}
	
	public String getEventTitle() {
		return eventTitle;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("\nAppointment: " + eventTitle);
		builder.append("\nDate: "+ date);
		builder.append("\nLocation: "+ location);
		builder.append("\nNotes: " + appointmentNote);
		return builder.toString();
	}
	
	

	public int compareTo(Event other) {
		return date.compareTo(other.date);
	}
	
	
	
	
}
