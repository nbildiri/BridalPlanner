package BridalPlanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ModifyEvents {
	private ArrayList<Event> calendar;
	private ArrayList<Event> accomplishedAppointments;
	private ArrayList<EventBookings> todo;
	//private ArrayList<EventBookings> accomplishedBookingsOLD;
	private ArrayList<Event> accomplishedBookings;
	private Scanner keyboard;
	
	public ModifyEvents() {
		calendar = new ArrayList<>();
		accomplishedAppointments = new ArrayList<>();
		todo = new ArrayList<>();
		//accomplishedBookingsOLD = new ArrayList<>();
		accomplishedBookings = new ArrayList<>();
		//todoSetUp();
		keyboard = new Scanner(System.in);
		
	}
	//adding event to calendar
	public void addAppointment() {
		
		EventAppointments appointments[] = EventAppointments.values();
		printEventsEnum();
	
		
		System.out.println("Please choose from the following events:");
		System.out.print("EVENT NUMBER: ");
		int eventNumber = keyboard.nextInt();
		while(eventNumber <= 0 || eventNumber > appointments.length+1 ) {
			System.out.println("There is no choice with that number, please re-enter");
			eventNumber = keyboard.nextInt();
		}
		
		System.out.print("DATE (in yyyy-mm-dd format): ");
		keyboard.nextLine();
		LocalDate date = null;
		boolean valid = false;
		while(!valid) {
			String deadline = keyboard.nextLine();
			try {
				date = LocalDate.parse(deadline);
				valid = true;
			}
			catch(Exception e) {
				System.out.println("ERROR: please enter date in correct format");
				System.out.print("DATE (in yyyy-mm-dd format): ");
				valid = false;
			}
		}	
		
		System.out.print("LOCATION: ");
		String location = keyboard.nextLine();
		
		System.out.println("NOTE: ");
		String note = addNote();
		
		//if they choose number 10 then have them input a string of the title
				if(eventNumber == 10) {
					System.out.println("TITLE: ");
					String title = appointments[eventNumber - 1].name() + ": " + customAppointment();
					calendar.add(new Event(title, date, location, note));
					return;
				}
				calendar.add(new Event(appointments[eventNumber - 1], date, location, note));
				System.out.println("Appointment Has Been Added");
				
}	
	
	public String customAppointment() {
		System.out.println("Enter the title of your custom appoinment: ");
		String title = keyboard.nextLine();
		return title;
		
	}
	
	public void todoSetUp() {
		EventBookings bookings[] = EventBookings.values();
		for( int i =0; i<bookings.length; i++) {
			todo.add(bookings[i]);
		}
	}
	
	public void printEventsEnum() {
		//displaying events for bride to see
		EventAppointments appointments[] = EventAppointments.values();
		for(int i =0; i<appointments.length; i++) {
			System.out.println((i + 1) + ": " + appointments[i]);
		}
	}
		
		//checking completed event
	public void completeAppointment(int eventNum) {
		accomplishedAppointments.add(calendar.get(eventNum -1));
		calendar.remove(eventNum - 1);
	}
	
	
	public void completeBooking(int eventNum, String desc) {
		accomplishedBookings.add(new Event(todo.get(eventNum-1), desc));
		todo.remove(eventNum - 1);	
	}
	
	
	
	public void displayCalendar() {
		if(calendar.size() == 0) {
			System.out.println("No appointments listed");
		}
		else {
			System.out.println("---Appointments---");
			Collections.sort(calendar);
			for(int i=0; i<calendar.size(); i++) {
				System.out.println((i+1) + ". " + calendar.get(i).toString());
				
			}
			System.out.println();
		}
	}
	
	public void displayTodo() {
		if(todo.size() == 0) {
			System.out.println("No bookings listed");
		}
		else {
			System.out.println("--- To Do ---");
			for(int i=0; i<todo.size(); i++) {
				System.out.println((i+1)+ ". " + todo.get(i).toString());
		
			}
			System.out.println();
			}
		}
	
	public void displayAccomplishedAppointment() {
		if(accomplishedAppointments.size() == 0) {
			System.out.println("No appointments accomplished");
		}
		else {
			System.out.println("---Accomplished Events---");
			Collections.sort(accomplishedAppointments);
			for(int i=0; i<accomplishedAppointments.size(); i++) {
				System.out.println((i+1) + ". " + accomplishedAppointments.get(i).toString());
			}
			System.out.println();
		}
	}
	
	public void displayAccomplishedBooking() {
		if(accomplishedBookings.size() == 0) {
			System.out.println("No bookings accomplished");
		}
		else {
			System.out.println("---Accomplished Bookings---");
			//Collections.sort(accomplishedBookingsOLD);
			for(int i=0; i<accomplishedBookings.size(); i++) {
				System.out.println((i+1) + ". " + accomplishedBookings.get(i).getBooking().name() + " - " + accomplishedBookings.get(i).getEventTitle()); //+ " - " + accomplishedBookings.get(i).getEventTitle());
			}
			System.out.println();
		}
	}
	
	public String addNote() {
		String note = "None";
		System.out.println("would you like to add a note to an appointment? (1=yes, 2=no)");
		int choice = keyboard.nextInt();
		while(choice != 1 && choice !=2) {
			System.out.println("Please enter 1 for yes or 2 for no");
			choice = keyboard.nextInt();
		}
		keyboard.nextLine();
		if(choice == 1) {
			System.out.println("Please Type in Note: ");
			note = keyboard.nextLine();
			return note;
		}
		return note;
	}
	
	public ArrayList<Event> getCalendar() {
		return calendar;
	}
	public void setCalendar(ArrayList<Event> calendar) {
		this.calendar = calendar;
	}
	public ArrayList<Event> getAccomplishedAppointments() {
		return accomplishedAppointments;
	}
	public void setAccomplishedAppointments(ArrayList<Event> accomplishedAppointments) {
		this.accomplishedAppointments = accomplishedAppointments;
	}
	public ArrayList<EventBookings> getTodo() {
		return todo;
	}
	public void setTodo(ArrayList<EventBookings> todo) {
		this.todo = todo;
	}
	public ArrayList<Event> getAccomplishedBookings() {
		return accomplishedBookings;
	}
	public void setAccomplishedBookings(ArrayList<Event> accomplishedBookings) {
		this.accomplishedBookings = accomplishedBookings;
	}
	

}
