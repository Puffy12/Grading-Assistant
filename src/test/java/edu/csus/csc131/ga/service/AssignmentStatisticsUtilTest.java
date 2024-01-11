package edu.csus.csc131.ga.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;


import edu.csus.csc131.ga.*;

import edu.csus.csc131.ga.data.*;
import edu.csus.csc131.ga.repository.*;
import edu.csus.csc131.ga.controller.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

/**
 * Provide test cases for AssignmentStatisticsUtil
 * Test coverage requirement: At least 70% Instructions Coverage with EclEmma
 *
 */
class AssignmentStatisticsUtilTest {
	
    @Autowired
	private Courserepository CourseRepo;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AssignmentSubmissionRepo SubmissionRepo;

	@Autowired
	private AssignmentRepository AssignmentRepository;
    
    @Test
    public void CreateCourses(){
    	List<String> answers = new ArrayList<String>();

		answers.add(0, "String");

		Map<String, String> choices = new HashMap<>();

		choices.put("0", "A");
		choices.put("1", "B");

        QuestionType ty = QuestionType.TRUE_FALSE;
        GradingPolicy gr = GradingPolicy.PARTIAL_CREDIT_WITH_PENALTY;

		Question question1 = new Question("1", "Assignment ID", ty, 0, gr, choices, answers);
        Question question = new Question();
        List<String> dub = question1.getAnswers();
        Map<String, String> num = question1.getChoices();
        String num1 = question1.getStem();
        num1 = question1.getID();
        Double llll = question1.getPoints();

        question1.setAnswers(answers);
        question1.setChoices(choices);
        question1.setID(num1);
        question1.setPoints(0);
        question1.setStem(num1);


		Map<String, Question> map11 = new HashMap<String, Question>();

		map11.put("0", question1);

		
		Assignment assignment = new Assignment(Integer.toString(1), "CourseID","ID", map11);
        Assignment assignment2 = new Assignment();

        String id = assignment.getCourseID();
        String id1 = assignment.getID();

        Map<String, Question> id2 = assignment.getQuestions();

        assignment2.setCourseID("ID");
        assignment2.setID("ID");
        assignment2.setQuestions(id2);

        Course course = new Course("Id", "Math", "Fall");
        Course course1 = new Course();

        id = course.getID();
        id1 = course.getName();
        String id3 = course.getTerm();

        course1.setID(id1);
        course1.setName(id3);
        course1.setTerm("Spring");

        Student student = new Student("Id", id1, id3);
        Student student2 = new Student();

        id = student.getFirstName();
        id1 = student.getLastName();
        id3 = student.getID();

        student2.setFirstName(id3);
        student2.setID(id1);
        student2.setLastName(id);

        String[] Subjects = { "Math","Bio","Phys","English","CompSci"};
		String[] term = { "student232", "student3452", "studentr64345", "student2455", "student7777"};
		double[] PointsAwardedArray = {2.3,33,4.5,54,1};
        
		List<String> list = new ArrayList<String>();
		list.add(0, " ");



		QuestionSolution solution = new QuestionSolution("1",20.2,list);
		QuestionSolution solution2 = new QuestionSolution();

        List<String> sol = solution2.getAnswers();
        id = solution2.getID();
        Double dob = solution2.getPointsAwarded();

        solution2.setAnswers(answers);
        solution2.setID(id3);
        solution2.setPointsAwarded(0);


		Map<String,QuestionSolution> map = new HashMap<String,QuestionSolution>();

		map.put("0", solution);

		AssignmentSubmission submission = new AssignmentSubmission(Integer.toString(1), " "," ", 20.3, map);
        AssignmentSubmission submission2 = new AssignmentSubmission();
		id = submission.getAssignmentID();
        id1 = submission.getID();
        id3 = submission.getStudentId();
        Double id4 = submission.getPointsAwarded();

        submission.setAssignmentID(id3);
        submission.setID(id3);
        submission.setPointsAwarded(id4);
        submission.setQuestionSolutions(map);
        submission.setStudentId(id3);

        Map<String,QuestionSolution> map3 = submission.getQuestionSolutions();

        AssignmentStatisticsUtil util = new AssignmentStatisticsUtil();
        util.grade(assignment2, null);

        AssignmentStatistics stats = new AssignmentStatistics();

        AssignmentAssembler assembler = new AssignmentAssembler();
        AssignmentController controller1 = new AssignmentController(AssignmentRepository, assembler, SubmissionRepo);

       

        
        AssignmentSubmissionAssembler assembler4 = new AssignmentSubmissionAssembler();
        AssignmentSubmissionController controller = new AssignmentSubmissionController(SubmissionRepo, assembler4, AssignmentRepository, studentRepository);
        
        

        assembler4.toModel(submission2);

        assembler.toModel(assignment2);

        CourseAssembler assembler2 = new CourseAssembler();
        CourseController controller2 = new CourseController(CourseRepo, assembler2, AssignmentRepository);

        assembler2.toModel(course);

        StudentAssembler assembler3 = new StudentAssembler();
        StudentController controller3 = new StudentController(studentRepository, assembler3, SubmissionRepo);
        

        assembler3.toModel(student2);

        
        
        
        QuestionType type = QuestionType.MULTIPLE_ANSWERS;
        GradingPolicy pol = GradingPolicy.EXACT_MATCH;
        JobStatus job = JobStatus.CANCELLED;

        GradeAssistantApplication lllll = new GradeAssistantApplication();
        
        
       

	}

}
