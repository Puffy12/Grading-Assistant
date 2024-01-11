package edu.csus.csc131.ga.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.ga.data.AssignmentSubmission;

@Component
public class AssignmentSubmissionAssembler implements RepresentationModelAssembler<AssignmentSubmission, EntityModel<AssignmentSubmission>> {

	@Override
	public EntityModel<AssignmentSubmission> toModel(AssignmentSubmission submission) {
		return EntityModel.of(submission, //
		        linkTo(methodOn(AssignmentSubmissionController.class).getSubmission(submission.getID())).withSelfRel(),
		        linkTo(methodOn(AssignmentSubmissionController.class).getALLSubmissions(submission.getAssignmentID(), submission.getStudentId())).withRel("submissions"));
	}

}