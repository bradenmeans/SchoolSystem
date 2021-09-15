package org.university.hardware;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.University;

public class Classroom implements Serializable{
	private String roomNumber;
	private ArrayList<Course> classList;
	
	// Default constructor
	public Classroom() {
		setRoomNumber("unknown");
		classList = new ArrayList<Course>();
	}
	
	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	// A function that takes in the schedule number 
	// and converts it into a string, which it will return, of 
	// the day of the week and time slot it is on.
	public static String courseConverter(int courseNumber) {
		if (courseNumber >= 600) {
			return null;
		}
		String[] day = {"fill", "Mon", "Tue", "Wed", "Thu", "Fri"};
		String[] time = {"fill", "8:00am to 9:15am",
				"9:30am to 10:45am",
				"11:00am to 12:15pm",
				"12:30pm to 1:45pm", 
				"2:00pm to 3:15pm",
				"3:30pm to 4:45pm" };
		String dayOfWeek = "";
		String timeSlot = "";
		
		int i = 0;
		while (courseNumber / 100 != i) {
			i++;
		}
		
		if (courseNumber / 100 == i) {
			dayOfWeek = day[i];
		}
		
		int j = 0;
		while(courseNumber % 10 != j) {
			j++;
		}
		
		if (j == courseNumber % 10) {
			timeSlot = time[j];
		}
		
		return dayOfWeek + " " + timeSlot;
	}
	
	// This function take in an array list of type Course,
	// which it will sort, so that the schedule is printed in the 
	// correct order. 
	public static void orderSchedule (ArrayList<Course> list) {
		ArrayList<Integer> sorter = new ArrayList<Integer>();
		
		// Compiles a list of schedule numbers from the course array list into an integer array list
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCourseNumber() < 600) {
				for (int j = 0; j < list.get(i).getSchedule().size(); j++) {
					sorter.add(list.get(i).getSchedule().get(j));
				}
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCourseNumber() >= 600) {
				sorter.add(list.get(i).getCourseNumber());
			}
		}
		
		// Sorts the integer array list from smallest to largest number
		Collections.sort(sorter);
		
		
		// Loops through sorted integer array list
		for (int k = 0; k < sorter.size(); k++) {
					int n = 0;
					int m = 0;
					boolean flag = false;
					boolean exception = false;
					if (sorter.get(k) >= 600) {
						for (int x = 0; x < sorter.size(); x++) {
							if (sorter.get(k) == list.get(x).getCourseNumber()) {
								System.out.println(list.get(x).getCourseNumber() + " " + list.get(x).getName());
								break;
							}
						}
					}
					// Iterates through the list of courses, comparing the 
					// indexed value of the schedule number in the sorted integer array list
					// with the schedule numbers from the unsorted course list
					// that was taken as the argument.
					// When there is a match, it gets out of the loop, and prints it,
					// until it reaches the end of the list
					else {
				while (list.get(m).getSchedule().get(n) != sorter.get(k)) {
					while (n < list.get(m).getSchedule().size()) {
						if (list.get(m).getSchedule().get(n) == sorter.get(k)) {
							flag = true;
							break;
						}
						if (n == list.get(m).getSchedule().size() - 1) {
							n = 0;
							break;
						}
						else {
							n++;
						}
					}
					if (flag) {
						break;
					}
					m++;
					try {
					    list.get(m).getSchedule().get(n);
					} catch ( IndexOutOfBoundsException e ) {
						m++;
						n = 0;
						exception = true;
					    break;
					}
				}
				if (!exception) {
				System.out.println(Classroom.courseConverter(sorter.get(k)) + " " 
						+ list.get(m).getDepartment().getDepartmentName() +
				list.get(m).getCourseNumber() + " " + list.get(m).getName());
				
				n = 0;
				m = 0;
				}
				else {
					while(exception) {
						if (list.get(m).getCourseNumber() < 600 && list.get(m).getSchedule().get(n) == sorter.get(k)) {
					
							System.out.println(Classroom.courseConverter(sorter.get(k)) + " " 
									+ list.get(m).getDepartment().getDepartmentName() +
							list.get(m).getCourseNumber() + " " + list.get(m).getName());
							
							n = 0;
							m = 0;
							exception = false;
						}
						else {
							while (list.get(m).getCourseNumber() >= 600) {
								m++;
							}
					while (list.get(m).getSchedule().get(n) != sorter.get(k)) {
						while (n < list.get(m).getSchedule().size()) {
							if (list.get(m).getSchedule().get(n) == sorter.get(k)) {
								flag = true;
								break;
							}
							if (n == list.get(m).getSchedule().size() - 1) {
								n = 0;
								break;
							}
							else {
								n++;
							}
						}
						if (flag) {
							break;
						}
						m++;
						try {
						    list.get(m).getSchedule().get(n);
						    exception = false;
						} catch ( IndexOutOfBoundsException e ) {
							m++;
							n = 0;
							exception = true;
						    break;
						}
						if (!exception) {
							System.out.println(Classroom.courseConverter(sorter.get(k)) + " " 
									+ list.get(m).getDepartment().getDepartmentName() +
							list.get(m).getCourseNumber() + " " + list.get(m).getName());
							
							n = 0;
							m = 0;
						}
					}
					}
				}

			}
		}
		}
	}
	
	// Calls order schedule function to sort and print list
	public void printSchedule() {
		orderSchedule(classList);
	}
	
	public ArrayList<Course> getClassList() {
		return classList;
	}
	
	public void setClassList(ArrayList<Course> classList) {
		this.classList = classList;
	}
	
	
	
}
