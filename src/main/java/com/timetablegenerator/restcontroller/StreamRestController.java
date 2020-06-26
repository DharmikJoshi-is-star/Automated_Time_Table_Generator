package com.timetablegenerator.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.timetablegenerator.entity.Stream;
import com.timetablegenerator.service.StreamService;

@RestController
public class StreamRestController {

	@Autowired
	StreamService streamService;
	
	@CrossOrigin(origins = {"http://localhost:4200"}, allowedHeaders = {"Accept"})
	@GetMapping("/getAllStreams/{collegeId}")
	public List<Stream> getAllStreams(@PathVariable("collegeId") Long collegeId){
		
		List<Stream> streams = streamService.getAllStreams(collegeId);
		
		return streams;
		
	}
	
}
