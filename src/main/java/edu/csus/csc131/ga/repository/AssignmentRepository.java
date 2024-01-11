package edu.csus.csc131.ga.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.csus.csc131.ga.data.Assignment;


@Repository
public interface AssignmentRepository extends MongoRepository<Assignment, String> {

    List<Assignment> findBycourseId(String id);

    List<Assignment> findByID(String id);
    
    List<Assignment> deleteBycourseId(String id);

}   
