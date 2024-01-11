package edu.csus.csc131.ga.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.ga.data.GradingJob;



@Component
public class JobAssembler implements RepresentationModelAssembler<GradingJob, EntityModel<GradingJob>> {

    @Override
    public EntityModel<GradingJob> toModel(GradingJob jobs) {
        return EntityModel.of(jobs, //
                linkTo(methodOn(JobController.class).getJob(jobs.getID())).withSelfRel(),
                linkTo(methodOn(JobController.class).getALLJobs(jobs.getassignmentId())).withRel("jobs"));
    }

}



