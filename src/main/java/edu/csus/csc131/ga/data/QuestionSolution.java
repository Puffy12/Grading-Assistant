package edu.csus.csc131.ga.data;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class QuestionSolution{
	@Id
	private String questionId;
    private double pointsAwarded;
    private List<String> answers;


    public QuestionSolution(){

    }

    public QuestionSolution(String QuestionID, double pointsAwarded, List<String> Answers) {
		this.questionId = QuestionID;
		this.pointsAwarded = pointsAwarded;
		this.answers = Answers;

	}

    public String getID() {
		return questionId;
	}

    public void setID(String ID) {
		this.questionId = ID;
	}

    public double getPointsAwarded() {
		return pointsAwarded;
	}

    public void setPointsAwarded(double pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}

    public List<String> getAnswers() {
		return answers;
	}

    public void setAnswers(List<String> Answers) {
		this.answers = Answers;
	}

}
