package org.university.people;

import java.io.Serializable;

public abstract class Employee extends Person implements Serializable {
	
	public Employee() {
		super();
	}

	public abstract double earns();
	
	public abstract void raise(double amount);

}
