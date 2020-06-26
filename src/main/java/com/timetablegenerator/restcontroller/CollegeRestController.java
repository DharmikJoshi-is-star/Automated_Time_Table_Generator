package com.timetablegenerator.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timetablegenerator.entity.College;
import com.timetablegenerator.service.CollegeService;

@RestController
public class CollegeRestController {
  
	@Autowired
	CollegeService collegeService;
	
	@CrossOrigin(origins = {"http://localhost:4200"}, allowedHeaders = {"Accept"})
	@GetMapping("/getCollege/{collegeId}")
	public College getCollege(@PathVariable("collegeId") Long collegeId) {
		
		College college = null;
		
		if(collegeId!=null)
			college =  collegeService.getCollegeForFrontEnd(collegeId);
		
		return college;
	}
	
}
