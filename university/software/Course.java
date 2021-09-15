package org.university.software;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.people.Person;
import org.university.people.Professor;
import org.university.people.Staff;
import org.university.people.Student;

public abstract class Course implements Comparable<Course>, Serializable {
	private String name;
	protected ArrayList<Person> studentRoster;
	private int courseNumber;
	private Department department;
	private ArrayList<Integer> schedule;
	private Professor professor;
	private Classroom roomAssigned;
	private Student student;
	public int index = -1;
	
	// Default constructor
	public Course() {
		setName("unknown");
		setCourseNumber(0);
		schedule = new ArrayList<Integer>();
		studentRoster = new ArrayList<Person>();
		setDepartment(new Department());
		setRoomAssigned(new Classroom());
	}
	
	public ArrayList<Person> getStudentRoster() {
		return studentRoster;
	}
	
	// Adds student to the course roster.
	public void setStudentRoster(ArrayList<Person> studentRoster) {
		studentRoster.add(new Student());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCourseNumber() {
		return courseNumber;
	}
	
	public void setCourseNumber(int courseNumber) {
		this.courseNumber = courseNumber;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public ArrayList<Integer> getSchedule() {
		return schedule;
	}
	
	// Adds course number to schedule.
	public void setSchedule(int schedule) {
		this.schedule.add(schedule);
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}


	public Classroom getRoomAssigned() {
		return roomAssigned;
	}
	
	// Checks to make sure the room is not already assigned
	// to a different course. If not 
	// assigns the given classroom to the course
	public void setRoomAssigned(Classroom roomAssigned) {
		this.roomAssigned = roomAssigned;
		if (!detectConflict(roomAssigned)) {
			roomAssigned.getClassList().add(this);
		}
	}
	
	// Compares given classroom to already assigned classrooms to make
	// sure that it does not conflict with any of them.
	// Returns true if there is a conflict and prints error.
	public boolean detectConflict(Classroom cr) {
		for (int i = 0; i < cr.getClassList().size(); i++) {
			if (schedule.get(i).equals(cr.getClassList().get(0).getSchedule().get(0))) {
				index = i;
				System.out.println(getDepartment().getDepartmentName() + getCourseNumber() + " conflicts with " +
						roomAssigned.getClassList().get(index).getDepartment().getDepartmentName() + roomAssigned.getClassList().get(index).getCourseNumber() +
				". Conflicting time slot " +
						Classroom.courseConverter(roomAssigned.getClassList().get(index).getSchedule().get(index)) + ". " +
				getDepartment().getDepartmentName() + getCourseNumber() + " course cannot be added to " +
						roomAssigned.getRoomNumber() + "'s Schedule.");
				return true;
			}
		}
		return false;
	}
	
	// Used to be able to compare two objects of type Course
	// using ".equals"
	@Override
	public int compareTo(Course o) {
		return this.getSchedule().get(0).compareTo(o.getSchedule().get(0));
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
		student.getStudentSchedule().add(this);
	}
	
	// Calls function to sort and print schedule
	public void printSchedule() {
		ArrayList<Integer> sorter = new ArrayList<Integer>();
		
		// Compiles a list of schedule numbers from the course array list into an integer array list
		for (int i = 0; i < schedule.size(); i++) {
			sorter.add(schedule.get(i));
		}
		
		// Sorts the integer array list from smallest to largest number
		Collections.sort(sorter);
		
		// Loops through sorted integer array list
		for (int k = 0; k < sorter.size(); k++) {
					int m = 0;
					boolean flag = false;
				
					// Iterates through the list of courses, comparing the 
					// indexed value of the schedule number in the sorted integer array list
					// with the schedule numbers from the unsorted course list
					// that was taken as the argument.
					// When there is a match, it gets out of the loop, and prints it,
					// until it reaches the end of the list
				while (schedule.get(m) != sorter.get(k)) {
						if (schedule.get(m) == sorter.get(k)) {
							flag = true;
							break;
						}
						m++;
					}
					if (flag) {
						break;
					}
				
				System.out.println(Classroom.courseConverter(sorter.get(k)) + " " 
						+ getRoomAssigned().getRoomNumber());
			}
	}
	
	// Adds staff to roster
	public void addStudentToRoster() {
		studentRoster.add(new Staff());
	}
	
	public abstract boolean availableTo(Student aStudent);
	
}
