package edu.csus.csc131.ga.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashMap;
import java.util.Map;

@Document
public class AssignmentSubmission{ 
	@Id
	private String ID;
    private String assignmentId;
    private String studentId;
	private double pointsAwarded;
	private Map<String, QuestionSolution> questionSolutions;
	
	public AssignmentSubmission(){


	}

    public AssignmentSubmission(String ID, String AssignmentID, String StudentId, double PointsAwarded,Map<String, QuestionSolution> QuestionSolutions) {
		this.ID = ID;
		this.assignmentId = AssignmentID;
		this.studentId = StudentId;
		this.pointsAwarded = PointsAwarded;
		this.questionSolutions = QuestionSolutions;

	}

    public String getID() {
		return ID;
	}

    public void setID(String ID) {
		this.ID = ID;
	}

    public String getAssignmentID() {
		return assignmentId;
	}

    public void setAssignmentID(String AssignmentID) {
		this.assignmentId = AssignmentID;
	}

    public String getStudentId() {
		return studentId;
	}

    public void setStudentId(String StudentId) {
		this.studentId = StudentId;
	}

	public Double getPointsAwarded() {
		return pointsAwarded;
	}

    public void setPointsAwarded(Double PointsAwarded) {
		this.pointsAwarded = PointsAwarded;
	}


	public Map<String, QuestionSolution> getQuestionSolutions() {
		return questionSolutions;
	}

	public void setQuestionSolutions(Map<String, QuestionSolution> QuestionSolutions) {
		this.questionSolutions = QuestionSolutions;
	}
}
