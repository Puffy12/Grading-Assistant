package edu.csus.csc131.ga.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class  GradingJob{
    @Id
    private String ID;
    private String assignmentId;
    private JobStatus status;
    private String completedDate;

    private AssignmentStatistics statistics;



    public GradingJob() {

    }

    public GradingJob(String ID, String assignmentId, JobStatus status,String completedDate,AssignmentStatistics statistics) {
        this.ID = ID;
        this.assignmentId = assignmentId;
        this.status = status;
        this.completedDate=completedDate;
        this.statistics=statistics;

    }

    public String getID() {
        return ID;
    }//id

    public void setID(String ID) {
        this.ID = ID;
    }//id

    public String getassignmentId() {
        return assignmentId;
    }

    public void setassignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public JobStatus getstatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    //
    public String getcompletedDate() {
        return completedDate;
    }

    public void setcompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    //
    public  AssignmentStatistics getstatistics() {
        return statistics;
    }

    public void setcompletedDate(AssignmentStatistics statistics) {
        this.statistics = statistics;
    }

}

