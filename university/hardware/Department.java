package org.university.hardware;
import java.io.Serializable;
import java.util.ArrayList;

import org.university.people.Professor;
import org.university.people.Staff;
import org.university.people.Student;
import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;


public class Department implements Serializable {
	private String departmentName;
	private ArrayList<Course> courseList;
	private ArrayList<Student> studentList;
	private ArrayList<Professor> professorList;
	private ArrayList<Staff> staffList;
	private ArrayList<CampusCourse> campusCourseList;
	private ArrayList<OnlineCourse> onlineCourseList;

	// Default constructor
	public Department() {
		setDepartmentName("unknown");
		studentList = new ArrayList<Student>();
		courseList = new ArrayList<Course>();
		professorList = new ArrayList<Professor>();
		staffList = new ArrayList<Staff>();
		campusCourseList = new ArrayList<CampusCourse>();
		setOnlineCourseList(new ArrayList<OnlineCourse>());
		
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public ArrayList<Course> getCourseList(){
		return courseList;
	}
	
	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}
	
	// Sets the department that the course belongs 
	// Adds course to the lists of courses in the department
	public void addCourse(Course course) {
		course.setDepartment(this);
		course.getDepartment().courseList.add(course);
	}
	
	public void addCourse(CampusCourse cc) {
		cc.setDepartment(this);
		cc.getDepartment().campusCourseList.add(cc);
		cc.getDepartment().courseList.add(cc);
	} 
	public void addCourse(OnlineCourse oc) {
		oc.setDepartment(this);
		oc.getDepartment().onlineCourseList.add(oc);
		oc.getDepartment().courseList.add(oc);
	} 
	
	// Set the department the the student is a part of 
	// Adds the student to the list of students in the department
	public void addStudent(Student student) {
		student.setDepartment(this);
		student.getDepartment().studentList.add(student);
	}
	
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	
	// List of students in the department
	public void setStudentList(ArrayList<Student> studentList) {
		studentList.add(new Student());
	}
	
	// Adds professor to department, and 
	// adds them to the list of professors in that department
	public void addProfessor(Professor professor) {
		professor.setDepartment(this);
		professor.getDepartment().professorList.add(professor);
	}
	
	public ArrayList<Professor> getProfessorList() {
		return professorList;
	}
	
	
	public void setProfessorList(ArrayList<Professor> professorList) {
		this.professorList = professorList;
	}

	// These functions print the lists of their respective objects
	public void printStudentList() {
		for (int i = 0; i < studentList.size(); i++) {
			System.out.println(studentList.get(i).getName());
		}
	}
	
	public void printProfessorList() {
		for (int i = 0; i < professorList.size(); i++) {
			System.out.println(professorList.get(i).getName());
		}
	}
	
	public void printCourseList() {
		for (int i = 0; i < courseList.size(); i++) {
			System.out.println(courseList.get(i).getDepartment().getDepartmentName() + 
					courseList.get(i).getCourseNumber() + " " + courseList.get(i).getName());
		}
	}
	
	public void printStaffList() {
		for (int i = 0; i < staffList.size(); i++) {
			System.out.println(staffList.get(i).getName());
		}
	}
	
	// Adds staff to staff list, and sets their department
	public void addStaff(Staff staff) {
		staff.setDepartment(this);
		staff.getDepartment().staffList.add(staff);
	}

	public ArrayList<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(ArrayList<Staff> staffList) {
		this.staffList = staffList;
	}

	public ArrayList<CampusCourse> getCampusCourseList() {
		return campusCourseList;
	}

	public void setCampusCourseList(ArrayList<CampusCourse> campusCourseList) {
		this.campusCourseList = campusCourseList;
	}

	public ArrayList<OnlineCourse> getOnlineCourseList() {
		return onlineCourseList;
	}

	public void setOnlineCourseList(ArrayList<OnlineCourse> onlineCourseList) {
		this.onlineCourseList = onlineCourseList;
	}
	
}
