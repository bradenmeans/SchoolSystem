package org.university.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;

public class Professor extends Employee implements Serializable  {
	private String name;
	private Course course;
	private Department department;
	private ArrayList<Course> profSchedule;
	private double salary;

	
	// Default constructor
	public Professor() {
		setName("unknown");
		profSchedule = new ArrayList<Course>();
		setDepartment(new Department());
		setSalary(0);
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	// Calls function to order and print the schedule of the professor
	public void printSchedule() {
		Classroom.orderSchedule(pSchedule);
	}
	
	// Adds campus course to professors schedule
	// Error if there is a conflict with another course or another professor is signed up for the course
	public void addCourse(CampusCourse cCourse) {
		if(!detectConflict(cCourse)) {
			if (cCourse.getProfessor() == null) {
				setCourse(cCourse);
				cCourse.setProfessor(this);
				cCourse.getProfessor().cCourses.add(cCourse);
				cCourse.getProfessor().pSchedule.add(cCourse);
			}
			else {
				System.out.println("The professor " + getName() + " cannot be assigned to this campus"
						+ " course because " + cCourse.getProfessor().getName() + " is already"
						+ " assigned to the course " + cCourse.getName() + ".");
			}
		}
	}
	
	// Adds online course to professors schedule
	// Error if another professor is already signed up for the course.
	public void addCourse(OnlineCourse oCourse) {
		if (oCourse.getProfessor() == null) {
			oCourse.setProfessor(this);
			setCourse(oCourse);
			oCourse.getProfessor().oCourses.add(oCourse);
			oCourse.getProfessor().pSchedule.add(oCourse);
		}
		else {
			System.out.println("The professor cannot be assigned to this online course because professor "
					+ oCourse.getProfessor().getName() + " is already"
			+ " assigned to the online course " + oCourse.getName() + ".");
		}
		
	}

	public ArrayList<Course> getProfSchedule() {
		return profSchedule;
	}

	public void setProfSchedule(ArrayList<Course> profSchedule) {
		this.profSchedule = profSchedule;
	}

	public double getSalary() {
		return salary/26;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	// Calculates professor salary with their raise.
	public void raise(double amount) {
		double raise = 0;
		
		raise = (getSalary() * (amount/100)) + getSalary();
		setSalary(raise * 26);
	}
	
	// Returns amount professor earns.
	public double earns() {
		return getSalary();
	}


	
}
