package edu.csus.csc131.ga.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.csus.csc131.ga.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.csc131.ga.repository.AssignmentRepository;
import edu.csus.csc131.ga.repository.AssignmentSubmissionRepo;
import edu.csus.csc131.ga.data.Question;

@RestController
@RequestMapping(value = "/assignments")
public class AssignmentController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final AssignmentRepository assignmentRepository;
    private final AssignmentAssembler assignmentAssembler;
    private final AssignmentSubmissionRepo assignmentSubmissionRepo;
    //private final StudentRepository studentRepository;


    public AssignmentController(AssignmentRepository assignmentRepository, AssignmentAssembler assignmentAssembler, AssignmentSubmissionRepo assignmentSubmissionRepo) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentAssembler = assignmentAssembler;
        this.assignmentSubmissionRepo = assignmentSubmissionRepo;
       //this.studentRepository = studentRepository;
    }

    //get all assignments by course id
    @RequestMapping(value = "", method = RequestMethod.GET)
    public CollectionModel<EntityModel<Assignment>> getALLAssignments(@RequestParam(required = false) String courseId){
        LOG.info("Getting all Assignments.");
        List<Assignment> assignment = Collections.emptyList();

        if (courseId != null) {
            assignment = assignmentRepository.findBycourseId(courseId);
            LOG.info("Getting all Assignments by Course Id.");
        }else{
            assignment = assignmentRepository.findAll(); //getting all assignments

        }


        List<EntityModel<Assignment>> employees = assignment.stream()
                .map(assignmentAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees,
                linkTo(methodOn(AssignmentController.class).getALLAssignments(courseId)).withSelfRel());
    }

    //Get assignment by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EntityModel<Assignment> getAssignment(@PathVariable String id) {
        LOG.info("Getting an Assignment by id: "+ id);
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment " + id));
        return assignmentAssembler.toModel(assignment);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addNewAssignment(@RequestBody Assignment assignment) {
        String id = assignment.getID();
        LOG.info("Adding assignment.");
        if (id != null) {
            throw new BadRequestException("Cannot create a new assignment with id " + id);
        } else {
            Assignment newAssignment = assignmentRepository.save(assignment);
            EntityModel<Assignment> entityModel = assignmentAssembler.toModel(newAssignment);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAssignment(@RequestBody Assignment newAssignment, @PathVariable String id) {
        LOG.info("Updating assignment.");
        boolean exist = assignmentRepository.existsById(id);
        newAssignment.setID(id);
        Assignment updatedAssignment = assignmentRepository.save(newAssignment);
        EntityModel<Assignment> entityModel = assignmentAssembler.toModel(updatedAssignment);

        if (exist) {
            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                    .body(entityModel);
        }
    }
    //get all questions
    @RequestMapping(value = "/{id}/questions", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Question>> getAllQuestions(@PathVariable String id) {
        LOG.info("Getting all Questions.");
        Assignment as = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment " + id));
        return new ResponseEntity<> (as.getQuestions(), HttpStatus.OK);
    }

    //get a question
    @RequestMapping(value = "/{id}/questions/{key}", method = RequestMethod.GET)
    public ResponseEntity<Question> getQuestion(@PathVariable String id, @PathVariable String key) {

        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question" + id));

        Question settings = assignment.getQuestions().get(key);

        if (settings == null) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Question>(settings, HttpStatus.OK);
        }
    }

    //create or update a question
    @RequestMapping(value = "/{id}/solutions", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestion(@PathVariable String id, @RequestParam String key, @RequestBody Question value) {

        Assignment Questions = assignmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Assignment" + id));

        Questions.getQuestions().put(key, value);

        Assignment savedSolutions = assignmentRepository.save(Questions);

        return new ResponseEntity<>(savedSolutions.getQuestions().get(key), HttpStatus.OK);
    }

    // delete an assignment question
    @RequestMapping(value = "/{id}/questions/{key}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeQuestion(@PathVariable String id, @PathVariable String key) {
        Assignment Questions = assignmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission" + id));
        Questions.getQuestions().remove(key);
        assignmentRepository.save(Questions);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //delete an assignment has submission


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAssignment(@PathVariable String id) {
        LOG.info("Deleting assignment with id: {}.", id);
        assignmentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
