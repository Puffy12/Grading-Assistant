package edu.csus.csc131.ga.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;





import edu.csus.csc131.ga.data.Assignment;


@Component
public class AssignmentAssembler implements RepresentationModelAssembler<Assignment, EntityModel<Assignment>> {

    @Override
    public EntityModel<Assignment> toModel(Assignment assignment) {
        return EntityModel.of(assignment, //
                linkTo(methodOn(AssignmentController.class).getAssignment(assignment.getID())).withSelfRel(),
                linkTo(methodOn(AssignmentController.class).getALLAssignments(assignment.getCourseID())).withRel("assignments"));
    }

}

