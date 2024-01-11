package edu.csus.csc131.ga.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.csus.csc131.ga.data.AssignmentSubmission;
import edu.csus.csc131.ga.data.Student;
import edu.csus.csc131.ga.repository.AssignmentSubmissionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import edu.csus.csc131.ga.repository.StudentRepository;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudentAssembler studentAssembler;

	@Autowired
	private AssignmentSubmissionRepo assignmentSubmissionRepo;

	public StudentController(StudentRepository studentRepository, StudentAssembler studentAssembler, AssignmentSubmissionRepo asRepo) {
		this.studentRepository = studentRepository;
		this.studentAssembler = studentAssembler;
		this.assignmentSubmissionRepo = asRepo;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public CollectionModel<EntityModel<Student>> getALLStudents() {
		LOG.info("Getting all students.");
		List<Student> students = Collections.emptyList();

		students = studentRepository.findAll();

		List<EntityModel<Student>> employees = students.stream()
				.map(studentAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(employees,
				linkTo(methodOn(StudentController.class).getALLStudents()).withSelfRel());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<Student> getStudent(@PathVariable String id) {
		LOG.info("Getting a student by their Id: {}.", id);
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student", id));
		return studentAssembler.toModel(student);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addNewStudent(@RequestBody Student student) {
		String id = student.getID();
		LOG.info("Adding student.");
		if (id != null) {
			throw new BadRequestException("Cannot create a new student with id " + id);
		} else {
			Student newStudent = studentRepository.save(student);
			EntityModel<Student> entityModel = studentAssembler.toModel(newStudent);
			return ResponseEntity //
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateStudent(@RequestBody Student newStudent, @PathVariable String id) {
		LOG.info("Updating student.");
		boolean exist = studentRepository.existsById(id);
		newStudent.setID(id);
		Student updatedUser = studentRepository.save(newStudent);
		EntityModel<Student> entityModel = studentAssembler.toModel(updatedUser);

		if (exist) {
			return ResponseEntity.ok(entityModel);
		} else {
			return ResponseEntity //
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
					.body(entityModel);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteStudent(@PathVariable String id) {
		LOG.info("Deleting student with id: {}.", id);
		studentRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteStudentWithOutSubmission(@PathVariable String id) {
		LOG.info("Deleting student without an assignment submission with id:" + id);
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id + " not found."));
		if (student == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("No student with that id found.");
		}
		List<AssignmentSubmission> submissions = assignmentSubmissionRepo.findByStudentId(id);

		if (submissions == null) {
			studentRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Student with id:  " + id + " deleted. Student has not made a submission.");
		} else{
			assignmentSubmissionRepo.deleteByStudentId(student.getID());
			studentRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Student with id:  " + id + " deleted. Student has made a submission.");
		}
	}
}



