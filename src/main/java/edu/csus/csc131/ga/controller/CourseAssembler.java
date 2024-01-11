package edu.csus.csc131.ga.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.ga.data.Course;

@Component
public class CourseAssembler implements RepresentationModelAssembler<Course, EntityModel<Course>> {

	@Override
	public EntityModel<Course> toModel(Course course) {
		return EntityModel.of(course, //
		        linkTo(methodOn(CourseController.class).getCourse(course.getID())).withSelfRel(),
		        linkTo(methodOn(CourseController.class).getALLCourses()).withRel("courses"));
	}

}