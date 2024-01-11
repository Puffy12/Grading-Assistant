package edu.csus.csc131.ga.controller;

import  static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import edu.csus.csc131.ga.data.Course;
import edu.csus.csc131.ga.repository.AssignmentRepository;
import edu.csus.csc131.ga.repository.Courserepository;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final Courserepository Repository;
	private final CourseAssembler Assembler;
	private final AssignmentRepository AssignmentRepo;

    public CourseController(Courserepository repo, CourseAssembler assembler,AssignmentRepository assignmentRepository) {
		this.Repository = repo;
		this.Assembler = assembler;
		this.AssignmentRepo = assignmentRepository;
	}

    @RequestMapping(value = "", method = RequestMethod.GET)
	public CollectionModel<EntityModel<Course>> getALLCourses() {
		LOG.info("Getting all courses.");
		List<Course> coursesList = Collections.emptyList();

        coursesList = Repository.findAll();

		List<EntityModel<Course>> employees = coursesList.stream() 
				.map(Assembler::toModel) 
				.collect(Collectors.toList());
		return CollectionModel.of(employees,
				linkTo(methodOn(CourseController.class).getALLCourses()).withSelfRel());
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<Course> getCourse(@PathVariable String id) {
		LOG.info("Getting a Course by Id: "+ id);
		Course course = Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course", id));
		return Assembler.toModel(course);
	}	

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addNewCourse(@RequestBody Course course) {
		String id = course.getID();
		LOG.info("Adding user with id " + id);
		if (id != null) {
			throw new BadRequestException("Cannot create a new user with id " + id);
		} else {
			Course newCourse = Repository.save(course);
			EntityModel<Course> entityModel = Assembler.toModel(newCourse);
			return ResponseEntity //
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		}

	}


	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCourse(@RequestBody Course course, @PathVariable String id) {
		LOG.info("Updating user with id " + id);
		boolean exist = Repository.existsById(id);
		course.setID(id);
		Course updatedCourse = Repository.save(course);
		EntityModel<Course> entityModel = Assembler.toModel(updatedCourse);
		
		if (exist) {
			return ResponseEntity.ok(entityModel);
		} else {
			return ResponseEntity //
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
					.body(entityModel);	
		}
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCourse(@PathVariable String id) {
		LOG.info("Deleting course with id: " + id);	
		Repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCourseWithAssignment(@PathVariable String id) {
		LOG.info("Deleting Course with assignment id:" + id);

		Course course = Repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id + " not found."));


		if (course == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("No student with that id found.");
		}

		List<Assignment> assignments = AssignmentRepo.findBycourseId(id);
		
		if (assignments == null) {
			Repository.deleteById(id);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Course with id:  " + id + " deleted. Course has no assingments.");
		} else{
			Repository.deleteById(id);
			AssignmentRepo.deleteBycourseId(id);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Course with id:  " + id + " deleted. Course has assingments.");
		}
	}

}

