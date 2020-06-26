package com.timetablegenerator.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.timetablegenerator.entity.StreamStandard;
import com.timetablegenerator.service.StreamStandardService;

@RestController
public class StreamStandardRestController {

	@Autowired
	StreamStandardService streamStandardService;
	
	@CrossOrigin(origins = {"http://localhost:4200"}, allowedHeaders = {"Accept"})
	@GetMapping("/getAllStreamStandards/{collegeId}")
	public List<StreamStandard> getAllStreamStandards(@PathVariable("collegeId") Long collegeId){
				
		return streamStandardService.getAllStreamStandards(collegeId);
	}
	
}
