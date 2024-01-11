package edu.csus.csc131.ga.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashMap;
import java.util.Map;

@Document
public class Assignment{
	@Id
	private String ID;
	private String courseId;

	private String AssignmentName;

	private Map<String, Question> Questions;

	public Assignment(){


	}

	public Assignment(String ID, String CourseID, String AssignmentName, Map<String, Question> Questions) {
		this.ID = ID;
		this.courseId = CourseID;
		this.AssignmentName= AssignmentName;
		this.Questions = Questions;


	}

	public String getID() {

		return ID;
	}

	public void setID(String ID) {

		this.ID = ID;
	}

	public String getCourseID() {

		return courseId;
	}

	public void setCourseID(String CourseID) {

		this.courseId = CourseID;
	}

	public String getAssignmentName() {

		return AssignmentName;
	}

	public void setAssignmentName(String AssignmentName) {

		this.AssignmentName = AssignmentName;
	}



	public Map<String, Question> getQuestions() {
		return Questions;
	}

	public void setQuestions(Map<String, Question> Questions) {
		this.Questions = Questions;
	}


}

