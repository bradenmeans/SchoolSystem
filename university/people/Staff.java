package org.university.people;

import java.io.Serializable;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;

public class Staff extends Employee implements Serializable  {
	private String name;
	private Department department;
	private int tuitionFee;
	private double payRate;
	private int monthlyHoursWorked;
	
	
	public Staff() {
		setName("unknown");
		setTuitionFee(0);
		setDepartment(new Department());
		setMonthlyHours(0);
		setPayRate(0);
		setDepartment(new Department());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// Prints schedule of staff
	public void printSchedule() {
		if (pSchedule.get(0).getCourseNumber() >= 600) {
			System.out.println(pSchedule.get(0).getCourseNumber() + " " 
				+ pSchedule.get(0).getName());
		}
		else {
			Classroom.orderSchedule(pSchedule);
		}
	}

	public int getTuitionFee() {
		return totalTuitionFee();
	}

	public void setTuitionFee(int tuitionFee) {
		this.tuitionFee = tuitionFee;
	}
	
	// Adds campus course to staff schedule.
	// If the staff has no existing course, the campus course is added to the schedule.
	// If the staff is enrolled in another course, that course is overwritten by the new course being added
	public void addCourse(CampusCourse cc) {
		if(!cCourses.isEmpty() || !oCourses.isEmpty()) {
			if (!cCourses.isEmpty()) {
			System.out.println(cCourses.get(0).getDepartment().getDepartmentName() + 
					cCourses.get(0).getCourseNumber() + " has been removed from " + getName() +
				"'s schedule(Staff can only take one class at a time). " + 
					cc.getDepartment().getDepartmentName() + cc.getCourseNumber() + " has been added to "
				+ getName() + "'s Schedule.");
			pSchedule.remove(cCourses.get(0));
			cCourses.remove(cCourses.get(0));
			cCourses.add(cc);
			pSchedule.add(cc);
			cc.addStudentToRoster();
			}
			else {
				System.out.println(oCourses.get(0).getDepartment().getDepartmentName() + 
						oCourses.get(0).getCourseNumber() + " has been removed from " + getName() +
					"'s schedule(Staff can only take one class at a time). " + 
						cc.getDepartment().getDepartmentName() + cc.getCourseNumber() + " has been added to "
					+ getName() + "'s Schedule.");
				pSchedule.remove(oCourses.get(0));
				oCourses.remove(oCourses.get(0));
				cCourses.add(cc);
				pSchedule.add(cc);
				cc.getStudentRoster().add(this);
			}
		}
		else {
			cCourses.add(cc);
			pSchedule.add(cc);
			cc.getStudentRoster().add(this);
		}
	}
	
	// Adds online course to staff schedule.
	// If the staff has no existing course, the online course is added to the schedule.
	// If the staff is enrolled in another course, that course is overwritten by the new course being added
	public void addCourse(OnlineCourse oc) {
		if(!oCourses.isEmpty() || !cCourses.isEmpty()) {
			if (!oCourses.isEmpty()) {
			System.out.println(oCourses.get(0).getDepartment().getDepartmentName() + 
					oCourses.get(0).getCourseNumber() + " has been removed from " + getName() +
				"'s schedule(Staff can only take one class at a time). " + 
					oc.getDepartment().getDepartmentName() + oc.getCourseNumber() + " has been added to "
				+ getName() + "'s Schedule.");
			pSchedule.remove(oCourses.get(0));
			oCourses.remove(oCourses.get(0));
			oCourses.add(oc);
			pSchedule.add(oc);
			oc.addStudentToRoster();
			}
			else {
				System.out.println(cCourses.get(0).getDepartment().getDepartmentName() + 
						cCourses.get(0).getCourseNumber() + " has been removed from " + getName() +
					"'s schedule(Staff can only take one class at a time). " + 
						oc.getDepartment().getDepartmentName() + oc.getCourseNumber() + " has been added to "
					+ getName() + "'s Schedule.");
				pSchedule.remove(cCourses.get(0));
				cCourses.remove(cCourses.get(0));
				oCourses.add(oc);
				pSchedule.add(oc);
				oc.addStudentToRoster();
				oc.getStudentRoster().add(this);
			}
		}
		else {
			oCourses.add(oc);
			pSchedule.add(oc);
			oc.addStudentToRoster();
			oc.getStudentRoster().add(this);
		}
		
	}

	public double getPayRate() {
		return payRate;
	}

	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}

	public int getHoursWorked() {
		return monthlyHoursWorked;
	}

	public void setMonthlyHours(int monthlyHoursWorked) {
		this.monthlyHoursWorked = monthlyHoursWorked;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	// Calculates tuition fee
	// If an online course is detected, will first compute that cost and 
	// add it to the cost of the campus courses.
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
		if (oCourses.isEmpty()) {
			tuition = cCourses.get(0).getCreditUnits() * 300 + tuition;
		}
		
		return tuition;
	}
	
	// Calculates raise given to staff
	public void raise(double amount) {
		double percentage = amount /100;
		double raise = getPayRate() * percentage + getPayRate();
		setPayRate(raise);
		
	}
	
	// Computes staff earnings, and returns it 
	public double earns() {
		double earnings = 0;
		
		earnings = getHoursWorked() * getPayRate();
		
		return earnings;

	}

	

}
