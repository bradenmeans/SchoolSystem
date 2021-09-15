package org.university.people;

import java.io.Serializable;
import java.util.ArrayList;

import org.university.hardware.Classroom;
import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;

public abstract class Person implements Serializable{
	private String name;
	protected ArrayList<CampusCourse> cCourses;
	protected ArrayList<OnlineCourse> oCourses; 
	protected ArrayList<Course> pSchedule;
	
	public Person() {
		setName("unknown");
		pSchedule = new ArrayList<Course>();
		cCourses = new ArrayList<CampusCourse>();
		oCourses = new ArrayList<OnlineCourse>();
	}
	
	// Check for conflict in the campus course by comparing course trying to be added
	// to courses already in the students schedule.
 	public boolean detectConflict(CampusCourse aCourse) {
		for (int i = 0; i < cCourses.size(); i++) {
			for (int j = 0; j < aCourse.getSchedule().size() && j < cCourses.get(i).getSchedule().size(); j++) {
			if (aCourse.getSchedule().get(j).equals(cCourses.get(i).getSchedule().get(j))){
				for (int k = 0; k < aCourse.getSchedule().size(); k++) {
					System.out.println(aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber()
				+ " cannot be added to " + getName() + "'s schedule. " + 
					aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber() + " conflicts with " +
				cCourses.get(i).getDepartment().getDepartmentName() + cCourses.get(i).getCourseNumber() +
					". The conflicting time slot is " + Classroom.courseConverter(aCourse.getSchedule().get(k)));
				}
				return true;
				}
			}
		}
		return false;
	}
	
 	// Prints persons schedule.
	public void printSchedule() {
		Classroom.orderSchedule(pSchedule);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Course> getPSchedule() {
		return pSchedule;
	}

	public void setPSchedule(ArrayList<Course> pSchedule) {
		this.pSchedule = pSchedule;
	}

	public ArrayList<CampusCourse> getcCourses() {
		return cCourses;
	}

	public void setcCourses(ArrayList<CampusCourse> cCourses) {
		this.cCourses = cCourses;
	}

	public ArrayList<OnlineCourse> getoCourses() {
		return oCourses;
	}

	public void setoCourses(ArrayList<OnlineCourse> oCourses) {
		this.oCourses = oCourses;
	}
	
	public abstract void addCourse(CampusCourse cCourse);
	
	public abstract void addCourse(OnlineCourse oCourse);
}
