package edu.csus.csc131.ga.data;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

public class AssignmentStatistics {
    @Id
    private String assignmentId;
    private Map<String, String> data;
    private Map<String, QuestionStatistics> questionData;

    public AssignmentStatistics() {
    }
    public AssignmentStatistics(String assignmentId, Map<String,String> data, Map<String, QuestionStatistics> questionData) {
        this.assignmentId = assignmentId;
        this.data = data;
        this.questionData = questionData;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String ID) {
        this.assignmentId = ID;
    }
    public Map<String,String> getData() {
        return data;
    }

    public void setData (Map<String,String> data) {
        this.data = data;
    }

    public Map<String, QuestionStatistics> questionData () {
        return questionData;
    }

    public void setQuestionData (Map<String,QuestionStatistics> QuestionData) {
        this.questionData = QuestionData;
    }

}
