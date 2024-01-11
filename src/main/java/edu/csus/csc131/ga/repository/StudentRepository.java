package edu.csus.csc131.ga.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.csus.csc131.ga.data.Student;
 
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    
}
