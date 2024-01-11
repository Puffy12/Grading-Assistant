package edu.csus.csc131.ga.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
public class  Student{
	@Id
	private String ID;
	private String firstName;
	private String lastName;


	public Student() {

	}

	public Student(String id, String firstName, String lastName) {
		this();
		this.ID = id;
		this.firstName = firstName;
		this.lastName= lastName;

	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String FirstName) {
		this.firstName = FirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String LastName) {
		this.lastName= LastName;
	}


}
