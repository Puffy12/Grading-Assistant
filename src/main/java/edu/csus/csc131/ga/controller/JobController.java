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

import edu.csus.csc131.ga.data.*;

import edu.csus.csc131.ga.repository.*;
import edu.csus.csc131.ga.controller.*;

@RestController
@RequestMapping(value = "/jobs")
public class JobController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final JobAssembler assembler;
    private final JobRepository repo;

    public JobController(JobAssembler assembler, JobRepository repository) {
        this.assembler = assembler;
        this.repo = repository;
	}

    @RequestMapping(value = "", method = RequestMethod.GET)
	public CollectionModel<EntityModel<GradingJob>> getALLJobs(@RequestParam(required = false) String assignmentId) {

		List<GradingJob> submissionList = Collections.emptyList();

		if(assignmentId == null){ 
			submissionList = repo.findAll();
			LOG.info("Getting all Jobs");
			
		} else{
			submissionList = repo.findByassignmentId(assignmentId);
			LOG.info("Getting all Jobs by assignment Id");
		}

		if(submissionList == null){
			LOG.info("Nothing Found");

		}

		List<EntityModel<GradingJob>> employees = submissionList.stream() 
				.map(assembler::toModel) 
				.collect(Collectors.toList());
		return CollectionModel.of(employees,
				linkTo(methodOn(JobController.class).getALLJobs(assignmentId)).withSelfRel());
	}


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<GradingJob> getJob(@PathVariable String id) {
		LOG.info("Getting a job by Id: "+ id);
		GradingJob submission = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AssignmentSubmission " + id));
		return assembler.toModel(submission);
	}	


	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addNewJob(@RequestBody GradingJob submission) {
		String id = submission.getID();
		LOG.info("Adding job with id " + id);
		
		if (id != null) {
			throw new BadRequestException("Cannot create a new submission with id " + id);
		} else {
			GradingJob newSubmission = repo.save(submission);
			EntityModel<GradingJob> entityModel = assembler.toModel(newSubmission);
			return ResponseEntity //
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteJob(@PathVariable String id) {
		LOG.info("Deleting submission with id: " + id);	
		repo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
}
