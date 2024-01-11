package edu.csus.csc131.ga.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
public class  Course{
	@Id
	private String ID;
    private String Name;
    private String Term;


	public Course() {
		
	}

    public Course(String ID, String Name, String Term) {
		this.ID = ID;
		this.Name = Name;
		this.Term = Term;

	}

    public String getID() {
		return ID;
	}

    public void setID(String ID) {
		this.ID = ID;
	}

    public String getName() {
		return Name;
	}

    public void setName(String Name) {
		this.Name = Name;
	}

    public String getTerm() {
		return Term;
	}

    public void setTerm(String Term) {
		this.Term = Term;
	}

}
