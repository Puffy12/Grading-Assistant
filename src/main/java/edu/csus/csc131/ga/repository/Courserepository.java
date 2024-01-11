package edu.csus.csc131.ga.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.csus.csc131.ga.data.Assignment;
import edu.csus.csc131.ga.data.Course;


@Repository
public interface Courserepository extends MongoRepository<Course, String> {
        
}
