package edu.csus.csc131.ga.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.csus.csc131.ga.data.AssignmentSubmission;


@Repository
public interface AssignmentSubmissionRepo extends MongoRepository<AssignmentSubmission, String> {

    List<AssignmentSubmission> findByStudentId(String id);

    List<AssignmentSubmission> deleteByStudentId(String id);
    
    List<AssignmentSubmission>  findByassignmentId(String assignmentId);

    List<AssignmentSubmission>  findByID(String id);

    List<AssignmentSubmission>  findByassignmentIdAndStudentId(String assignmentId, String StudentId);
}
