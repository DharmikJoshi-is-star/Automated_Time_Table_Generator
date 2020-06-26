package com.timetablegenerator.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.timetablegenerator.entity.Subject;
import com.timetablegenerator.service.SubjectService;

@RestController
public class SubjectRestController {

	@Autowired
	SubjectService subjectService;
	
	@CrossOrigin(origins = {"http://localhost:4200"}, allowedHeaders = {"Accept"})
	@GetMapping("/getAllSubjects/{collegeId}")
	public List<Subject> getAllSubjects(@PathVariable("collegeId") Long collegeId){
		
		return subjectService.getAllSubjects(collegeId);
	}
	
}
