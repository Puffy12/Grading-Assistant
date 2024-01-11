package edu.csus.csc131.ga.data;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Question {
	@Id
	private String ID;
    private QuestionType Type;
    private String Stem;
    private double points;
	private Map<String, String> choices;
    private List<String> answers;
    private GradingPolicy Policy;
    
    public Question(){

    }

    public Question(String ID, String Stem,QuestionType Type, double points,GradingPolicy Policy, Map<String, String> choices, List<String> answers) {
		this.ID = ID;
		this.Stem = Stem;
		this.points = points;
		this.choices = choices;
		this.answers = answers;
		this.Type=Type;
		this.Policy=Policy;

	}

    public String getID() {
		return ID;
	}

    public void setID(String ID) {
		this.ID = ID;
	}

    public String getStem() {
		return Stem;
	}

    public void setStem(String Stem) {
		this.Stem = Stem;
	}

    public double getPoints() {
		return points;
	}

    public void setPoints(double points) {
		this.points = points;
	}

    public Map<String, String> getChoices() {
		return choices;
	}

    public void setChoices(Map<String, String> choices) {
		this.choices = choices;
	}

    public List<String> getAnswers() {
		return answers;
	}

    public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public QuestionType getType() {
		return Type;
	}

	public void setType(QuestionType Type) {
		this.Type= Type;
	}

	public GradingPolicy getPolicy() {
		return Policy;
	}

	public void setPolicy(GradingPolicy Policy) {
		this.Policy= Policy;
	}

}