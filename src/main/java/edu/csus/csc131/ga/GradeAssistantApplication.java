package edu.csus.csc131.ga;

import edu.csus.csc131.ga.data.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.csus.csc131.ga.controller.CourseController;
import edu.csus.csc131.ga.repository.Courserepository;
import edu.csus.csc131.ga.repository.JobRepository;
import edu.csus.csc131.ga.controller.AssignmentController;
import edu.csus.csc131.ga.repository.AssignmentRepository;
import edu.csus.csc131.ga.repository.AssignmentSubmissionRepo;
import edu.csus.csc131.ga.controller.AssignmentSubmissionController;
import edu.csus.csc131.ga.controller.StudentController;
import edu.csus.csc131.ga.repository.StudentRepository;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
public class GradeAssistantApplication implements CommandLineRunner {

	private final org.slf4j.Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private Courserepository CourseRepo;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AssignmentSubmissionRepo SubmissionRepo;

	@Autowired
	private AssignmentRepository AssignmentRepository;

	@Autowired
	private JobRepository JobRepo;

	public static void main(String[] args) {
		SpringApplication.run(GradeAssistantApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CreateCourses();
		CreateStudents();
		CreateSubmissions();
		CreateAssignments();

		JobRepo.deleteAll();
		CourseRepo.deleteAll();
		studentRepository.deleteAll();
		SubmissionRepo.deleteAll();
		AssignmentRepository.deleteAll();

		GradingJob job = new GradingJob("1", "null", null, null, null);
		JobRepo.save(job);


	}

	private void CreateSubmissions() {
		SubmissionRepo.deleteAll();

		String[] Subjects = { "Math","Bio","Phys","English","CompSci"};
		String[] term = { "student232", "student3452", "studentr64345", "student2455", "student7777"};
		double[] PointsAwardedArray = {2.3,33,4.5,54,1};

		//List<String> list = new ArrayList<String>();
		//Map<String,QuestionSolution> map = new HashMap<String,QuestionSolution>();
		//QuestionSolution solution = new QuestionSolution(Integer.toString(12),23.2,list);

		List<String> list = new ArrayList<String>();
		list.add(0, Subjects[1]);
		list.add(1, Subjects[2]);

		List<String> list2 = new ArrayList<String>();
		list2.add(0, Subjects[3]);
		list2.add(1, Subjects[4]);

		QuestionSolution solution = new QuestionSolution("1",PointsAwardedArray[3],list);
		QuestionSolution solution2 = new QuestionSolution("2",PointsAwardedArray[4],list2);

		Map<String,QuestionSolution> map = new HashMap<String,QuestionSolution>();

		map.put("0", solution);
		map.put("1", solution2);

		AssignmentSubmission submission = new AssignmentSubmission(Integer.toString(1), Subjects[1],term[1], PointsAwardedArray[0], map);

		submission = SubmissionRepo.save(submission);

		LOG.info("ID: " + submission.getID() + " Assignment ID:" + submission.getAssignmentID() + " Student ID:" + submission.getStudentId() + " Points:" + submission.getPointsAwarded());

		Map<String,QuestionSolution> map1 = submission.getQuestionSolutions();

		for(int i = 0; i < map1.size();i++) {
			QuestionSolution sol = map1.get(Integer.toString(i));


			LOG.info(i + "| Solution ID: " + sol.getID() + " Points: " + sol.getPointsAwarded());

			List<String> list3 = sol.getAnswers();

			for (int j = 0; j < list3.size(); j++) {
				String ans = list3.get(j);
				LOG.info(j + " Answer " + ans);
			}

			LOG.info("---------------------");
		}
	}


	private void CreateCourses(){
		CourseRepo.deleteAll();
		//List<Course> Courses = CourseRepo.findAll();
		
		String[] Subjects = { "Math","Bio","Phys","English","CompSci"};
		String[] term = { "Spring", "Summer", "Autum", "Winter", "Spring"};
		for (int i = 0; i < Subjects.length; i++) {
			Course course = new Course(Integer.toString(i), Subjects[i],term[i]);
			
			course = CourseRepo.save(course);
			LOG.info( course.getID() + course.getName() + course.getTerm() );
		}	
		
	}
	private void CreateStudents(){
		studentRepository.deleteAll();
		
		String[] firstName = { "Prithisha","Jaspreet","Heamandeep","Michael","Yu"};
		String[] lastName = { "Panta", "Chera", "Kaur", "Mehrdadi", "Chen"};
		for (int i = 0; i < firstName.length; i++) {
			Student student = new Student(Integer.toString(i), firstName[i],lastName[i]);

			student = studentRepository.save(student);
			LOG.info( student.getID() + student.getFirstName() + student.getLastName() );
		}

	}
	
	private void CreateAssignments() {
		AssignmentRepository.deleteAll();


		String[] AssignmentName = {"Research Paper", "Case Study", "Presentation", "Group Project", "Reflection Paper"};
		String[] CourseID = {"1", "2", "3", "4", "5"};
		String[] AssignmentID = {"101", "102", "103", "104", "105"};
		double[] PointsAwarded = {2.3, 33, 4.5, 54, 1};
		QuestionType Type = QuestionType.MULTIPLE_CHOICES;
		QuestionType Type1 = QuestionType.TRUE_FALSE;
		GradingPolicy Policy= GradingPolicy.EXACT_MATCH;
		GradingPolicy Policy1= GradingPolicy.PARTIAL_CREDIT_WITH_PENALTY;


		List<String> answers = new ArrayList<String>();

		answers.add(0, AssignmentName[1]);
		answers.add(1, AssignmentName[2]);

		List<String> answers1 = new ArrayList<String>();
		answers1.add(0, AssignmentName[3]);
		answers1.add(1, AssignmentName[4]);

		Map<String, String> choices = new HashMap<>();

		choices.put("0", "A");
		choices.put("1", "B");


		//    public Question(String ID, String Stem, double points, Map<String, String> choices, List<String> answers)
		Question question1 = new Question("1", AssignmentID[3],Type, PointsAwarded[3],Policy,choices,answers);
		Question question2 = new Question("2", AssignmentID[4],Type1, PointsAwarded[4],Policy1,choices,answers1);

		Map<String, Question> map11 = new HashMap<String, Question>();

		map11.put("0", question1);
		map11.put("1", question2);

		for (int i = 0; i < AssignmentName.length; i++) {
			Assignment assignment = new Assignment(Integer.toString(1), CourseID[i],AssignmentName[i], map11);

			assignment = AssignmentRepository.save(assignment);
			LOG.info(assignment.getID() + assignment.getCourseID());


		}






		 for (int j = 0; j < map11.size(); j++) {
			Question sol = map11.get(Integer.toString(j));

			LOG.info(j + "| Question ID: " + sol.getID() + " | Points: " + sol.getPoints());

		 }

	   }
	}


