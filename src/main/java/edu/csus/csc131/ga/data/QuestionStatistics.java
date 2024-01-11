package edu.csus.csc131.ga.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Document
public class  QuestionStatistics{
    @Id
    private String questionId;
    private Map<String, String> statistics;


    public QuestionStatistics() {

    }

    public QuestionStatistics(String questionId, Map<String, String> statistics) {
        this.questionId = questionId;
        this.statistics = statistics;

    }

    public String getquestionId() {
        return questionId;
    }

    public void setquestionId(String questionId) {
        this.questionId = questionId;
    }

    public Map<String, String>  getstatistics() {
        return statistics;
    }

    public void setstatistics(Map<String, String> statistics) {
        this.statistics = statistics;
    }





}
