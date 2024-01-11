package edu.csus.csc131.ga.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.print.DocFlavor.READER;

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

import edu.csus.csc131.ga.data.Assignment;
import edu.csus.csc131.ga.data.AssignmentSubmission;
import edu.csus.csc131.ga.data.QuestionSolution;
import edu.csus.csc131.ga.data.Student;
import edu.csus.csc131.ga.repository.AssignmentSubmissionRepo;

import edu.csus.csc131.ga.repository.AssignmentRepository;
import edu.csus.csc131.ga.repository.StudentRepository;



@RestController
@RequestMapping(value = "/submissions")
public class AssignmentSubmissionController  {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final AssignmentSubmissionRepo Repository;
	private final AssignmentSubmissionAssembler Assembler;

	private final AssignmentRepository AssignmentRepo;
	private final StudentRepository StudentRepo;

    public AssignmentSubmissionController(AssignmentSubmissionRepo repo, AssignmentSubmissionAssembler assembler, AssignmentRepository AssignmentRepo, StudentRepository StudentRepo) {
		this.Repository = repo;
		this.Assembler = assembler;
		this.AssignmentRepo = AssignmentRepo;
		this.StudentRepo = StudentRepo;
	}

    @RequestMapping(value = "", method = RequestMethod.GET)
	public CollectionModel<EntityModel<AssignmentSubmission>> getALLSubmissions(@RequestParam(required = false) String assignmentId, @RequestParam(required = false) String studentId) {

		List<AssignmentSubmission> submissionList = Collections.emptyList();

		if(assignmentId == null && studentId == null){ 
			submissionList = Repository.findAll();
			LOG.info("Getting all AssignmentSubmissions.");
		} else if (assignmentId == null) {
			submissionList = Repository.findByStudentId(studentId);
			LOG.info("Getting all AssignmentSubmissions by Student Id.");
		} else if (studentId == null) {
			submissionList = Repository.findByassignmentId(assignmentId);
			LOG.info("Getting all AssignmentSubmissions by Assignment Id.");
		} else {
			submissionList = Repository.findByassignmentIdAndStudentId(assignmentId, studentId);

			LOG.info("Getting all AssignmentSubmissions by Assignment Id and Student Id.");
		}

		if(submissionList == null){
			LOG.info("Nothing Found");

		}

		List<EntityModel<AssignmentSubmission>> employees = submissionList.stream() 
				.map(Assembler::toModel) 
				.collect(Collectors.toList());
		return CollectionModel.of(employees,
				linkTo(methodOn(AssignmentSubmissionController.class).getALLSubmissions(assignmentId,studentId)).withSelfRel());
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<AssignmentSubmission> getSubmission(@PathVariable String id) {
		LOG.info("Getting a submission by Id: "+ id);
		AssignmentSubmission submission = Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AssignmentSubmission " + id));
		return Assembler.toModel(submission);
	}	


	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addNewSubmission(@RequestBody AssignmentSubmission submission) {
		String id = submission.getID();
		LOG.info("Adding submission with id " + id);
		
		//Student student = StudentRepo.findById(StudentID) .orElseThrow(() -> new ResourceNotFoundException("Student " + StudentID));
		//Assignment assignment = AssignmentRepo.findById(AssignmentID).orElseThrow(() -> new ResourceNotFoundException("Assignment " + AssignmentID));

		//submission.setAssignmentID(student.getID());
		//submission.setStudentId(assignment.getID());
		
		if (id != null) {
			throw new BadRequestException("Cannot create a new submission with id " + id);
		} else {
			AssignmentSubmission newSubmission = Repository.save(submission);
			EntityModel<AssignmentSubmission> entityModel = Assembler.toModel(newSubmission);
			return ResponseEntity //
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		}

	}


	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateSubmission(@RequestBody AssignmentSubmission submission, @PathVariable String id) {
		LOG.info("Updating submission with id " + id);
		boolean exist = Repository.existsById(id);
		submission.setID(id);
		AssignmentSubmission updatedSubmission = Repository.save(submission);
		EntityModel<AssignmentSubmission> entityModel = Assembler.toModel(updatedSubmission);
		
		if (exist) {
			return ResponseEntity.ok(entityModel);
		} else {
			return ResponseEntity //
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
					.body(entityModel);	
		}
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSubmission(@PathVariable String id) {
		LOG.info("Deleting submission with id: " + id);	
		Repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	

	@RequestMapping(value = "/{id}/solutions", method = RequestMethod.GET)
	public ResponseEntity<Map<String, QuestionSolution>> getAllQuestionSolution(@PathVariable String id) {
		LOG.info("Getting Question Solutions");

		AssignmentSubmission sub = Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission" + id));
		return new ResponseEntity<>(sub.getQuestionSolutions(), HttpStatus.OK);
	}	

	@RequestMapping(value = "/{id}/solutions/{key}", method = RequestMethod.GET)
	public ResponseEntity<QuestionSolution> getQuestionSolution(@PathVariable String id, @PathVariable String key) {

		AssignmentSubmission submission = Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission" + id));

		QuestionSolution settings = submission.getQuestionSolutions().get(key);

		if (settings == null) {
			return new ResponseEntity<QuestionSolution>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<QuestionSolution>(settings, HttpStatus.OK);
		}		
	}	

	@RequestMapping(value = "/{id}/solutions", method = RequestMethod.PUT)
	public ResponseEntity<QuestionSolution> updateQuestionSolution(@PathVariable String id, @RequestParam String key, @RequestBody QuestionSolution value) {

		AssignmentSubmission Solutions = Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission" + id));

		Solutions.getQuestionSolutions().put(key, value);

		AssignmentSubmission savedSolutions = Repository.save(Solutions);

		return new ResponseEntity<>(savedSolutions.getQuestionSolutions().get(key), HttpStatus.OK);
	}	

	@RequestMapping(value = "/{id}/solutions/{key}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeQuestionSolution(@PathVariable String id, @PathVariable String key) {
		AssignmentSubmission Solutions = Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission" + id));
		Solutions.getQuestionSolutions().remove(key);
		Repository.save(Solutions);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	

	@RequestMapping(value = "/{id}/solutions", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeQuestionSolutions(@PathVariable String id) {
		AssignmentSubmission Solutions = Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission" + id));

		Solutions.getQuestionSolutions().clear();
		Repository.save(Solutions);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	



}


