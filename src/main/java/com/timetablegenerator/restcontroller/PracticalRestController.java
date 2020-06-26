package com.timetablegenerator.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.timetablegenerator.entity.Practical;
import com.timetablegenerator.service.PracticalService;


@RestController
public class PracticalRestController {

	@Autowired
	PracticalService practicalService;
	
	@CrossOrigin(origins = {"http://localhost:4200"}, allowedHeaders = {"Accept"})
	@GetMapping("/getAllPracticals/{collegeId}")
	public List<Practical> getAllPracticals(@PathVariable("collegeId") Long collegeId){
		return practicalService.getAllPracticals(collegeId);
	}
	
}
