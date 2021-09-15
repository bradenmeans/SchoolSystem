package org.university.software;

import java.io.Serializable;

import org.university.people.Student;

public class OnlineCourse extends Course implements Serializable {
	private int creditUnits;
	
	public OnlineCourse() {
		setCreditUnits(0);
	}

	public int getCreditUnits() {
		return creditUnits;
	}

	public void setCreditUnits(int creditUnits) {
		this.creditUnits = creditUnits;
	}
	
	// If the student is taking less than 6 campus course credits, the online course is not available 
	// for them
	public boolean availableTo(Student aStudent) {
		if (aStudent.creditsTaken() < 6) {
			return false;
		}
		return true;
	}
}
	

