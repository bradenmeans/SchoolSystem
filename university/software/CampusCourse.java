package org.university.software;

import java.io.Serializable;

import org.university.people.Student;

public class CampusCourse extends Course implements Serializable {
	protected int creditUnits;
	private int maxCourseLimit;
	
	public CampusCourse() {
		setCreditUnits(0);
		setMaxCourseLimit(0);
	}

	public int getCreditUnits() {
		return creditUnits;
	}

	public void setCreditUnits(int creditUnits) {
		this.creditUnits = creditUnits;
	}

	public int getMaxCourseLimit() {
		return maxCourseLimit;
	}

	public void setMaxCourseLimit(int maxCourseLimit) {
		this.maxCourseLimit = maxCourseLimit;
	}

	// Makes sure course size is not exceeded
	public boolean availableTo(Student aStudent) {
		if (getMaxCourseLimit() <= studentRoster.size()) {
			return false;
		}
		return true;
	}
	
}
