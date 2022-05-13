package BridalPlanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

public class Main {

	static File aAppointments = new File("accomplishedAppointments.txt");
	static File uAppointments = new File("unaccomplishedAppointments.txt");
	static File aBookings = new File("accomplishedBookings.txt");
	static File uBookings = new File("unaccomplishedBookings.txt");
	static File weddingDate = new File("weddingDate.txt");
	
	public static void main(String[] args) {
		
		ArrayList<File> files = new ArrayList<>();
		files.add(aAppointments);
		files.add(uAppointments);
		files.add(aBookings);
		files.add(uBookings);
		files.add(weddingDate);
		
		ModifyEvents me = new ModifyEvents();
		
		Scanner keyboard = new Scanner(System.in);

		//welcome intro 
		System.out.println("Welcome to Bridal Planner!");
		System.out.println("We are excited to come along on your wedding journey ");
		System.out.println("Our Goal: We hope to make this process smooth and worry free");
		System.out.println("Let us plan your wedding so you don't have to. Be a guest at your own wedding! \n");
		System.out.print("Press 1 to plan a new wedding, press 2 to continue planning a current wedding: ");
		
		int ans = keyboard.nextInt();
		
		while (ans != 1 && ans != 2) {
			System.out.print("Please only enter 1 or 2: ");
			ans = keyboard.nextInt();
		}

		switch (ans) {
		case 1:
			me.todoSetUp();
			for (File f : files) {
				f.delete();
				try {
					f.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			System.out.println("What is the date of your wedding?");
			System.out.print("DATE (in yyyy-mm-dd format): ");
			LocalDate date = null;
			boolean valid = false;
			keyboard.nextLine();
			
			while(!valid) {
				String weddingDay= keyboard.nextLine();
				try {
					date = LocalDate.parse(weddingDay);
					valid = true;
				}
				catch(Exception e) {
					System.out.println("ERROR: please enter date in correct format");
					System.out.print("DATE (in yyyy-mm-dd format): ");

					valid = false;
				}
			}	

			dateToFile(weddingDate, date);
			
			FileWriter fw;
			try {
				fw = new FileWriter(uBookings);
				ArrayList<EventBookings> toDo = me.getTodo();
				for (EventBookings e : toDo) {
					fw.write(e.name() + "\n");
				}
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			break;
		case 2:
			try {
				Scanner scanner = new Scanner(uAppointments);
				ArrayList<String> uAppointments = new ArrayList<>();
				while (scanner.hasNextLine()) {
					uAppointments.add(scanner.nextLine());
				}

				ArrayList<Event> calendar = new ArrayList<>();
				
				for (String s : uAppointments) {
					String title = s.substring(s.indexOf("title: ") + 7, s.indexOf(" date: "));
					String thisDate = s.substring(s.indexOf(" date: ") + 7, s.indexOf(" location: "));
					LocalDate realDate = LocalDate.parse(thisDate);
					String location = s.substring(s.indexOf(" location: ") + 11, s.indexOf(" note: "));
					String note = s.substring(s.indexOf(" note: ") + 7);
					
					
					Event event = new Event(title, realDate, location, note);
					calendar.add(event);
				}
				
				me.setCalendar(calendar);
				
				scanner = new Scanner(aAppointments);
				ArrayList<String> aAppointments = new ArrayList<>();
				while (scanner.hasNextLine()) {
					aAppointments.add(scanner.nextLine());
				}
				
				ArrayList<Event> accomplishedEvents = new ArrayList<>();
				
				for (String s : aAppointments) {
					String title = s.substring(s.indexOf("title: ") + 7, s.indexOf(" date: "));
					String thisDate = s.substring(s.indexOf(" date: ") + 7, s.indexOf(" location: "));
					LocalDate realDate = LocalDate.parse(thisDate);
					String location = s.substring(s.indexOf(" location: ") + 11, s.indexOf(" note: "));
					String note = s.substring(s.indexOf(" note: ") + 7);
					if (note.equals("null")) {
						note = "None";
					}
					Event event = new Event(title, realDate, location, note);
					accomplishedEvents.add(event);
				}
				
				me.setAccomplishedAppointments(accomplishedEvents);
				
				scanner = new Scanner(uBookings);
				ArrayList<String> uBookings = new ArrayList<>();
				while (scanner.hasNextLine()) {
					uBookings.add(scanner.nextLine());
				} 
				
				ArrayList<EventBookings> toDo = new ArrayList<>();
				
				for (String s : uBookings) {
					for (int i = 0; i < EventBookings.values().length; i ++) {
						if (s.equals(EventBookings.values()[i].name())) {
							toDo.add(EventBookings.values()[i]);
							break;
						}
					}
					
				}
				
				me.setTodo(toDo);
				
				scanner = new Scanner(aBookings);
				ArrayList<String> aBookings = new ArrayList<>();
				while (scanner.hasNextLine()) {
					aBookings.add(scanner.nextLine());
				}
				
				ArrayList<Event> done = new ArrayList<>();
				
				for (String s : aBookings) {
					EventBookings title = null;
					for (int i = 0; i < EventBookings.values().length; i ++) {
						String sTitle = s.substring(0, s.indexOf(" - "));
						if (sTitle.equals(EventBookings.values()[i].name())) {
							title = EventBookings.values()[i];
							break;
						}
					}
					String desc = s.substring(s.indexOf(" - ") + 3);
					done.add(new Event(title, desc));
					
				}
				
				me.setAccomplishedBookings(done);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		getCountDown(weddingDate);
		menu(keyboard, me);
	}

	
	
	public static void parseAppointments() {
		
	}

	public static void getCountDown(File weddingDate ) {
		ArrayList<String> date = readFile(weddingDate);
		LocalDate wedding = LocalDate.parse(date.get(0));
		LocalDate todayDate = LocalDate.now();

		long daysBetween = ChronoUnit.DAYS.between(todayDate, wedding);
		System.out.println(daysBetween + " days until your wedding!!");
	}


	//menu to add events
	public static void menu(Scanner keyboard, ModifyEvents modEvent) {
		final int MENU = 8;
		boolean showMenu = true;
		while(showMenu) {
			System.out.println("\nplease select an option");
			System.out.println("----Menu----");
			System.out.println("1. view your appointments "
					+ "\n2. view your to do list   "
					+ "\n3. view accomplished "
					+ "\n4. add appointment "
					+ "\n5. complete an event "
					+ "\n6. Edit an Event"
					+ "\n7. how many days till wedding "
					+ "\n8. exit");
			
			int option = keyboard.nextInt();
			while(option > MENU || option < 1) {
				System.out.println("Please enter 1 through " + MENU);
				option = keyboard.nextInt();
				}
	
				switch(option) {
				case 1: 
					modEvent.displayCalendar();
					break;
				case 2:
					modEvent.displayTodo();
					break;
				case 3: 
					int accomplished;
					System.out.println("Press 1 to view accomplished appointments, press 2 to view accomplished bookings");
					accomplished = keyboard.nextInt();
					while(accomplished != 1 && accomplished != 2) {
						System.out.println("ERROR: Please enter 1 or 2");
						accomplished = keyboard.nextInt();
					}
					if(accomplished == 1) {
						modEvent.displayAccomplishedAppointment();
					}
					else if(accomplished == 2) {
						modEvent.displayAccomplishedBooking();
					}
					
					break;
				case 4:
					modEvent.addAppointment();
					break;
				case 5:
					int complete;
					System.out.println("Press 1 to complete appointment, press 2 to complete booking");
					complete = keyboard.nextInt();
					while(complete != 1 && complete != 2) {
						System.out.println("ERROR: Please enter 1 or 2");
						complete = keyboard.nextInt();
					}
					if(complete == 1) {
						if(modEvent.getCalendar().size() == 0) {
							System.out.println("No appointments listed");
						}
						else {
						modEvent.displayCalendar();
						System.out.println("Please enter event number ");
						boolean valid = false;
						int eventNum;
						do {
							
							eventNum = keyboard.nextInt();
							if (eventNum > 0 && eventNum <= modEvent.getCalendar().size()) {
								valid = true;
							} else {
								System.out.println("Please enter a number that corresponds with an appointment!");
							}
							
						} while (!valid);
							modEvent.completeAppointment(eventNum);

							System.out.println("Event marked complete!");
						}
					}
					else if(complete == 2) {
						if (modEvent.getTodo().size() == 0) {
							System.out.println("No bookings listed");
						} else {
							modEvent.displayTodo();
							System.out.println("Please enter event number ");
							int eventNum = keyboard.nextInt();
							
							String desc = "No description";
							
							keyboard.nextLine();
							System.out.println("Please enter a description for the booking: ");
							desc = keyboard.nextLine();
							
							modEvent.completeBooking(eventNum, desc);

							System.out.println("Event marked complete!");
						}

					}
					break;
				case 6:
					//check to make sure good
					System.out.println("Choose an event to Edit:");
					modEvent.displayCalendar();
					boolean isValid = false;
					int editEvent;
					do {
						
						editEvent = keyboard.nextInt();
						if (editEvent > 0 && editEvent <= modEvent.getCalendar().size()) {
							isValid = true;
						} else {
							System.out.println("Please enter a number that corresponds to an event!");
							
						}
					} while (!isValid);

					System.out.println("What would you like to edit?" +
							"\n1. Title" +
							"\n2. Date" +
							"\n3. Location" +
							"\n4. Note");
					int edit = keyboard.nextInt();

					if(edit < 1 || edit > 4)
					{
						System.out.println("Please enter correct number");
						edit = keyboard.nextInt();
					}
					switch(edit){
						case 1:
							System.out.println("Please enter the number that corresponds to the title.");
							EventAppointments appointments[] = EventAppointments.values();
							modEvent.printEventsEnum();
							System.out.print("EVENT NUMBER: ");
							int eventNumber = keyboard.nextInt();
							while(eventNumber <= 0 || eventNumber > appointments.length+1 ) {
								System.out.println("There is no choice with that number, please re-enter");
								eventNumber = keyboard.nextInt();
							}
							modEvent.getCalendar().get(editEvent - 1).setAppointment(appointments[eventNumber - 1]);
							break;
						case 2:
							System.out.print("Please enter a new date (in yyyy-mm-dd format): ");
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
							//String sDate = keyboard.nextLine();
							//LocalDate date = LocalDate.parse(sDate);
							modEvent.getCalendar().get(editEvent - 1).setDate(date);
							break;
						case 3:
							System.out.print("Please enter a new location: ");
							keyboard.nextLine();
							String location = keyboard.nextLine();
							modEvent.getCalendar().get(editEvent - 1).setLocation(location);
							break;
						case 4:
							System.out.print("Please enter a new note: ");
							keyboard.nextLine();
							String note = keyboard.nextLine();
							modEvent.getCalendar().get(editEvent - 1).setAppointmentNote(note);
							break;

					}
					break;


				case 7:
					getCountDown(new File("weddingDate.txt"));
					break;
				
				case 8:
					System.out.println("Good Bye");
					updateFiles(modEvent);
					showMenu = false;
					break;
			
					
				}
			}
	}

	public static void makeFiles(File file, ArrayList<Event> event) {

		try {
			FileWriter fw = new FileWriter(file);
			PrintWriter writer = new PrintWriter(fw);
			for(int i=0; i<event.size(); i++) {
				writer.println(event.get(i));
			}

			writer.close();   
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void dateToFile(File file, LocalDate date) {

		try {
			FileWriter fw = new FileWriter(file);
			PrintWriter writer = new PrintWriter(fw);
			writer.println(date);
			writer.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> readFile(File file) {
		ArrayList<String> date = new ArrayList<>();
		if(!file.exists()){
			System.out.println("Oops! The file doesn't exist on this computer.");
			System.exit(1);
		}
		Scanner fileReader;
		try {
			fileReader = new Scanner(file);
			//reading multiple lines
			int counter = 1;
			while(fileReader.hasNext()&& counter <= 1){
				counter ++;
				date.add(fileReader.nextLine());
			}

			fileReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return date;

	}
	
	public static void updateFiles(ModifyEvents me) {
		try {
			FileWriter fw1 = new FileWriter(uBookings);
			PrintWriter pw1 = new PrintWriter(fw1);
			for (EventBookings ub : me.getTodo()) {
				pw1.write(ub.name() + "\n");
			}
			
			pw1.close();
			
			FileWriter fw2 = new FileWriter(aBookings);
			PrintWriter pw2 = new PrintWriter(fw2);
			for (Event ab : me.getAccomplishedBookings()) {
				pw2.write(ab.getBooking().name() + " - " + ab.getEventTitle() + "\n");
			}
			
			pw2.close();
			
			FileWriter fw3 = new FileWriter(uAppointments);
			PrintWriter pw3 = new PrintWriter(fw3);
			for (Event ua : me.getCalendar()) {
				pw3.write("title: " + ua.getEventTitle() + " date: " + ua.getDate().toString() + " location: " + ua.getLocation() + " note: " + ua.getAppointmentNote() + "\n");
			}
			
			pw3.close();
			
			FileWriter fw4 = new FileWriter(aAppointments);
			PrintWriter pw4 = new PrintWriter(fw4);
			for (Event aa : me.getAccomplishedAppointments()) {
				pw4.write("title: " + aa.getEventTitle() + " date: " + aa.getDate().toString() + " location: " + aa.getLocation() + " note: " + aa.getAppointmentNote() + "\n");;
			}
			
			pw4.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}