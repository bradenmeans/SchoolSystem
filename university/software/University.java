package org.university.software;

import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.university.hardware.Classroom;
import org.university.hardware.Department;

public class University implements Serializable {
	public ArrayList<Department> departmentList;
	public ArrayList<Classroom> classroomList;
	
	public University() {
		departmentList = new ArrayList<Department>();
		classroomList = new ArrayList<Classroom>();
	}

	
	// Takes all departments listed at the university and prints them
	public void printDepartmentList() {
		for (int i=0; i < departmentList.size(); i++) {
			System.out.println(departmentList.get(i).getDepartmentName());
		}
	}

	// Gets each course in all departments and prints them
	public void printCourseList() {
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getCampusCourseList().size(); j++) {
				System.out.println(departmentList.get(i).getCampusCourseList().get(j).
						getDepartment().getDepartmentName() + departmentList.get(i).getCampusCourseList()
						.get(j).getCourseNumber() + " " + departmentList.get(i).getCampusCourseList().
						get(j).getName());
			}
		}
		for (int k = 0; k < departmentList.size(); k++) {
			for (int l = 0; l < departmentList.get(k).getCourseList().size(); l++) {
				if (departmentList.get(k).getCourseList().get(l).getCourseNumber() >= 600) {
					System.out.println(departmentList.get(k).getCourseList().get(l).
							getDepartment().getDepartmentName() + departmentList.get(k).getCourseList()
							.get(l).getCourseNumber() + " " + departmentList.get(k).getCourseList().
							get(l).getName());
				}
			}
		}
	
	}
	
	// Prints all the students in the university.
	public void printStudentList() {
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getStudentList().size();j++) {
				System.out.println(departmentList.get(i).getStudentList().get(j).getName());
			}
		}
	}	
	
	public void printProfessorList() {
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getProfessorList().size();j++) {
				System.out.println(departmentList.get(i).getProfessorList().get(j).getName());
			}
		}
	}	
	
	public void printStaffList() {
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getStaffList().size();j++) {
				System.out.println(departmentList.get(i).getStaffList().get(j).getName());
			}
		}
	}	
	
	public static void saveData(University univ){
		
		
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut= null;

		try 
		{
			fileOut = new FileOutputStream( "university.ser" );	
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(univ);
			objOut.close();
			fileOut.close();
	     }	
		
		catch(IOException i)
	    {
			i.printStackTrace();
	    }		
 	}
	
	public static University loadData()
	{	
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		University uni=null;
			
		try
		{
			fileIn = new FileInputStream("university.ser");
			objIn = new ObjectInputStream(fileIn);
			uni = (University) objIn.readObject();
			objIn.close();
			fileIn.close();
		}
		catch(IOException i)
		{
			i.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}  
		return uni;
	}	
	
	public void printAll() {
		System.out.println("List of departments: ");
		printDepartmentList();
		
		System.out.println("\nClassroom List: ");
		for (int i = 0; i < classroomList.size(); i++) {
			System.out.println(classroomList.get(i).getRoomNumber());
		}
		
		System.out.println("\nThe professor list for department ECE: ");
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.get(i).getDepartmentName().equals("ECE")) {
				for (int j = 0; j <departmentList.get(i).getProfessorList().size(); j++) {
					System.out.println(departmentList.get(i).getProfessorList().get(j).getName());
				}
			}
		}
		
		System.out.println("\nThe professor list for department CS: ");
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.get(i).getDepartmentName().equals("CS")) {
				for (int j = 0; j <departmentList.get(i).getProfessorList().size(); j++) {
					System.out.println(departmentList.get(i).getProfessorList().get(j).getName());
				}
			}
		}
		
		System.out.println("\nThe course list for department ECE: ");
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.get(i).getDepartmentName().equals("ECE")) {
				for (int j = 0; j <departmentList.get(i).getCourseList().size(); j++) {
					if(departmentList.get(i).getCourseList().get(j).getCourseNumber() < 600) {
						System.out.println(departmentList.get(i).getCourseList().get(j).getDepartment().getDepartmentName() +
							departmentList.get(i).getCourseList().get(j).getCourseNumber()
							+ " " + departmentList.get(i).getCourseList().get(j).getName());
					}
				}
				for (int j = 0; j <departmentList.get(i).getCourseList().size(); j++) {
					if(departmentList.get(i).getCourseList().get(j).getCourseNumber() >= 600) {
						System.out.println(departmentList.get(i).getCourseList().get(j).getDepartment().getDepartmentName() +
							departmentList.get(i).getCourseList().get(j).getCourseNumber()
							+ " " + departmentList.get(i).getCourseList().get(j).getName());
					}
				}
			}
		}
		
		System.out.println("\nThe course list for department CS: ");
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.get(i).getDepartmentName().equals("CS")) {
				for (int j = 0; j <departmentList.get(i).getCourseList().size(); j++) {
					if(departmentList.get(i).getCourseList().get(j).getCourseNumber() < 600) {
						System.out.println(departmentList.get(i).getCourseList().get(j).getDepartment().getDepartmentName() +
							departmentList.get(i).getCourseList().get(j).getCourseNumber()
							+ " " + departmentList.get(i).getCourseList().get(j).getName());
					}
				}
				for (int j = 0; j <departmentList.get(i).getCourseList().size(); j++) {
					if(departmentList.get(i).getCourseList().get(j).getCourseNumber() >= 600) {
						System.out.println(departmentList.get(i).getCourseList().get(j).getDepartment().getDepartmentName() +
							departmentList.get(i).getCourseList().get(j).getCourseNumber()
							+ " " + departmentList.get(i).getCourseList().get(j).getName());
					}
				}
			}
		}
	
		for(int i = 0; i < classroomList.size(); i++) {
			System.out.println("\nThe schedule for classroom " + classroomList.get(i).getRoomNumber() + ": ");
			classroomList.get(i).printSchedule();
		}
		
		System.out.println("\nDepartment ECE");
		System.out.println("\nPrinting Professor Schedules: ");
		
		
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getProfessorList().size(); j++) {
				if(departmentList.get(i).getDepartmentName().equals("ECE")) {
					System.out.println("\nThe Schedule for Prof. " + departmentList.get(i).getProfessorList().get(j).getName());
					departmentList.get(i).getProfessorList().get(j).printSchedule();
				}
			}
		}
		
		System.out.println("\nPrinting Student Schedules: ");
		
		
		for (int i = 0; i < departmentList.size(); i++) {			
			for(int j = 0; j < departmentList.get(i).getStudentList().size(); j++) {
				if (departmentList.get(i).getStudentList().get(j).getDepartment().getDepartmentName().equals("ECE")) {
					System.out.println("\nThe Schedule for student " + departmentList.get(i).getStudentList().get(j).getName());
					departmentList.get(i).getStudentList().get(j).printSchedule();
					
				}
			}
		}
		
		System.out.println("\nPrint Staff Schedules: ");
		System.out.println("\nThe rosters for courses offered by ECE");
		
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getCourseList().size(); j++) {
				if (departmentList.get(i).getCourseList().get(j).getDepartment().getDepartmentName().equals("ECE") && departmentList.get(i).getCourseList().get(j).getCourseNumber() < 600) {
				System.out.println("\nThe roster for course " + departmentList.get(i).getCourseList().get(j).getDepartment()
						.getDepartmentName() + departmentList.get(i).getCourseList().get(j).getCourseNumber());
					for (int k = 0; k < departmentList.get(i).getCourseList().get(j).getStudentRoster().size(); k++) {
						System.out.println(departmentList.get(i).getCourseList().get(j).getStudentRoster().get(k).getName());
					}
				}
			}
		}
		
		System.out.println("\nDepartment CS");
		System.out.println("\nPrinting Professor Schedules: ");
		
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getProfessorList().size(); j++) {
				if(departmentList.get(i).getDepartmentName().equals("CS")) {
					System.out.println("\nThe Schedule for Prof. " + departmentList.get(i).getProfessorList().get(j).getName());
					departmentList.get(i).getProfessorList().get(j).printSchedule();
				}
			}
		}
		
		System.out.println("\nPrinting Student Schedules: ");
		
		for (int i = 0; i < departmentList.size(); i++) {			
			for(int j = 0; j < departmentList.get(i).getStudentList().size(); j++) {
				if (departmentList.get(i).getStudentList().get(j).getDepartment().getDepartmentName().equals("CS")) {
					System.out.println("\nThe Schedule for student " + departmentList.get(i).getStudentList().get(j).getName());
					departmentList.get(i).getStudentList().get(j).printSchedule();
				}
			}
		}
		
		System.out.println("\nPrinting Staff Schedules: ");
		
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getStaffList().size(); j++) {
					if (departmentList.get(i).getDepartmentName().equals("CS")) {
						System.out.println("\nThe schedule for employee " + departmentList.get(i).getStaffList().get(j).getName());
						departmentList.get(i).getStaffList().get(j).printSchedule();
						System.out.println("\nStaff: " + departmentList.get(i).getStaffList().get(j).getName() + " earns $" +
								departmentList.get(i).getStaffList().get(j).earns() +" this month.");
				}	
			}
		}
		
		System.out.println("\nThe rosters for courses offered by CS");
		
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getCourseList().size(); j++) {
				if (departmentList.get(i).getCourseList().get(j).getDepartment().getDepartmentName().equals("CS") && departmentList.get(i).getCourseList().get(j).getCourseNumber() < 600) {
				System.out.println("\nThe roster for course " + departmentList.get(i).getCourseList().get(j).getDepartment()
						.getDepartmentName() + departmentList.get(i).getCourseList().get(j).getCourseNumber());
					for (int k = 0; k < departmentList.get(i).getCourseList().get(j).getStudentRoster().size(); k++) {
						System.out.println(departmentList.get(i).getCourseList().get(j).getStudentRoster().get(k).getName());
					}
				}
			}
		}
		
	}

}

