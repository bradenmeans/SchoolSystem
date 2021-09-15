package org.university.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;

public class Student extends Person implements Serializable  {
	private String name;
	private Department department;
	private Course course;
	private ArrayList<Course> studentSchedule;
	public int index = -1;
	private int requiredCredits;
	private int currentCredits;
	private int completedUnits;
	private int tuitionFee;
	
	// Default constructor
	public Student() {
		name = "unknown";
		setDepartment(new Department());
		studentSchedule = new ArrayList<Course>();
		setRequiredCredits(0);
		setCompletedUnits(0);
		setCurrentCredits(0);
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	// Drops campus course from student schedule
	// Checks for online courses in student schedule
	// If none found, course can be dropped as long as the course is in the schedule
	// If online course found, makes sure student will have at least 
	public void dropCourse(CampusCourse course) {
		int flag = 0;
		int ind = -1;
		int amountOnline = 0;
		
		for (int j = 0; j < studentSchedule.size(); j++) {
			if (studentSchedule.get(j).getCourseNumber() >= 600) {
				amountOnline++;
			}
		}
		
		for (int i = 0; i < studentSchedule.size(); i++) {
			if(course.getName().equals(studentSchedule.get(i).getName())) {
				flag = 1;
				ind = i;
			}
		}
		if (flag == 1) {
			if (amountOnline == 0) {
				course.getStudentRoster().remove(this);
				for (int k = 0; k < cCourses.size(); k++) {
					if (studentSchedule.get(ind).getName().equals(cCourses.get(k).getName())) {
						cCourses.remove(k);
					}
				}
				for (int k = 0; k < pSchedule.size(); k++) {
					if (studentSchedule.get(ind).getName().equals(pSchedule.get(k).getName())) {
						pSchedule.remove(k);
					}
				}
				studentSchedule.remove(ind);
				
			}
			else if (creditsTaken()  - course.getCreditUnits() >= 6 && amountOnline > 0) {
			course.getStudentRoster().remove(this);
			for (int k = 0; k < cCourses.size(); k++) {
				if (studentSchedule.get(ind).getName().equals(cCourses.get(k).getName())) {
					cCourses.remove(k);
				}
			}
			for (int k = 0; k < pSchedule.size(); k++) {
				if (studentSchedule.get(ind).getName().equals(pSchedule.get(k).getName())) {
					pSchedule.remove(k);
				}
			}
			studentSchedule.remove(ind);
			}
			else if (amountOnline > 0) {
				System.out.println(getName() + " can't drop this CampusCourse, because this student doesn't"
						+ " have enough campus course credit to hold the online course.");
				
			}
		}
		else if (flag == 0) { 
			System.out.println("The course " + course.getDepartment().getDepartmentName() + course.getCourseNumber() + " cannot be "
					+ "dropped because " + getName() + " is not enrolled in " 
			+ course.getDepartment().getDepartmentName() + course.getCourseNumber() + ".");
		}
	}
	
	// Removes online course from student schedule
	// If course attempting to be removed is not found in the schedule, error printed.
	public void dropCourse(OnlineCourse course) {
		int flag = 0;
		int ind = -1;
		for (int i = 0; i < studentSchedule.size(); i++) {
			if(course.getName().equals(studentSchedule.get(i).getName())) {
				flag = 1;
				ind = i;
			}
		}
		
		if (flag == 1) {
			course.getStudentRoster().remove(this);
			for (int k = 0; k < oCourses.size(); k++) {
				if (studentSchedule.get(ind).getName().equals(oCourses.get(k).getName())) {
					oCourses.remove(k);
				}
			}
			for (int k = 0; k < pSchedule.size(); k++) {
				if (studentSchedule.get(ind).getName().equals(pSchedule.get(k).getName())) {
					pSchedule.remove(k);
				}
			}
			studentSchedule.remove(ind);
		}
		else {
			System.out.println("The course " + course.getDepartment().getDepartmentName() + course.getCourseNumber() + " cannot be "
					+ "dropped because " + getName() + " is not enrolled in " 
			+ course.getDepartment().getDepartmentName() + course.getCourseNumber() + ".");
		}
	}
	
	public void setStudentSchedule(ArrayList<Course> studentSchedule) {
		this.studentSchedule = studentSchedule;
	}
	
	public ArrayList<Course> getStudentSchedule() {
		return studentSchedule;
	}
	
	// Calls function to sort and print schedule
	public void printSchedule() {
		Classroom.orderSchedule(pSchedule);
	}

	public int getRequiredCredits() {
		return requiredCredits;
	}

	public void setRequiredCredits(int requiredCredits) {
		this.requiredCredits = requiredCredits;
	}

	public int getCompletedUnits() {
		return completedUnits;
	}

	public void setCompletedUnits(int completedUnits) {
		this.completedUnits = completedUnits;
	}

	// Returns the difference between a students total required credits and total complete credits
	public int requiredToGraduate() {
		int reqCredits = 0;
		
		reqCredits = getRequiredCredits() - (getCompletedUnits() + creditsTaken() + 
				onlineCreditsTaken());
		return reqCredits;
	}

	public int getTuitionFee() {
		return totalTuitionFee();
	}

	public void setTuitionFee(int tuitionFee) {
		this.tuitionFee = tuitionFee;
	}
	
	// Adds campus course to student schedule.
	// Makes sure it doesn't conflict with another class in students schedule
	// and the classes course limit is not exceeded.
	public void addCourse(CampusCourse cCourse) {
		if(!detectConflict(cCourse) && cCourse.availableTo(this)) {
			cCourse.getStudentRoster().add(this);
			setCourse(cCourse);
			cCourse.setStudent(this);
			cCourses.add(cCourse);
			pSchedule.add(cCourse);
			setCurrentCredits(cCourse.getCreditUnits());
			
		}
		else if (!cCourse.availableTo(this)) {
			System.out.println(this.getName() + " can't add Campus Course " +
				cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() 
			+ " " + cCourse.getName() + 
				". Because this campus Course have enough student.");
		}
			
	}
	
	// Adds online course to student schedule
	// Makes sure student has at least 6 campus course credits before adding an
	// online course to their schedule
	public void addCourse(OnlineCourse oCourse) {
		if(oCourse.availableTo(this)) {
			oCourse.getStudentRoster().add(this);
			setCourse(oCourse);
			oCourse.setStudent(this);
			oCourses.add(oCourse);
			pSchedule.add(oCourse);
		}
		else {
			System.out.println("Student " + getName() + " has only " + creditsTaken() + " campus credits enrolled"
						+ ". Should have "
					+ "at least 6 credits registered before registering in online courses");
			System.out.println(getName() + " can't add online Course " + oCourse.getDepartment().getDepartmentName()
					+ oCourse.getCourseNumber() + " " + oCourse.getName() + ". Because this student"
					+ " doesn't have enough Campus course credit.");
		}
	}

	public int getCurrentCredits() {
		return currentCredits;
	}

	public void setCurrentCredits(int currentCredits) {
		this.currentCredits = currentCredits;
	}
	
	// Calculation of current total campus course credits taken by student
	public int creditsTaken() {
		int creditsTaken = 0;
		for (int i = 0; i < cCourses.size(); i++) {
			creditsTaken = cCourses.get(i).getCreditUnits() + creditsTaken;
		}
		
		return creditsTaken;
	}
	
	// Calculation of current total online credits taken by student
	public int onlineCreditsTaken() {
		int onlineCredits = 0;
		for (int i = 0; i < oCourses.size(); i ++) {
			onlineCredits = oCourses.get(i).getCreditUnits() + onlineCredits;
		}
		
		return onlineCredits;
	}
	
	// Calculation of tuition fee for student.
	// Same as staff
	public int totalTuitionFee() {
		int tuition = 0;
		
		for (int i = 0; i < pSchedule.size(); i++) {
			if(pSchedule.get(i).getCourseNumber() >= 600) {
				for(int j = 0; j < oCourses.size(); j++) {
					if (oCourses.get(j).getCreditUnits() == 3) {
						tuition = tuition + 2000;
					}
					if (oCourses.get(j).getCreditUnits() == 4) {
						tuition = tuition + 3000;
					}
					
				}
			}
		}
		tuition = creditsTaken() * 300 + tuition;
		
		return tuition;
	}


}
