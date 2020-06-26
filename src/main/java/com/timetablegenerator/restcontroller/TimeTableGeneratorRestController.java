package com.timetablegenerator.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timetablegenerator.algorithm.TimeTableAlgorithm;
import com.timetablegenerator.algorithm.TimeTableAlgorithmService;
import com.timetablegenerator.entity.Lecture;
import com.timetablegenerator.entity.TimeTable;

@RestController
public class TimeTableGeneratorRestController {

	@Autowired
	TimeTableAlgorithm timeTableAlgorithm;
	
	@Autowired
	TimeTableAlgorithmService timeTableAlgorithmService;
	
	TimeTable timeTable =  new TimeTable();

	@CrossOrigin(origins = {"http://localhost:4200"}, allowedHeaders = {"Accept"})
	@GetMapping("/generateTimeTable/{collegeId}")
	public TimeTable generateTimeTable(@PathVariable("collegeId") Long collegeId){
		
		
		if(collegeId!=null)
			timeTable.setLectures(timeTableAlgorithm.generateTimeTableForCollege(collegeId));
		
		timeTable = timeTableAlgorithmService.cleanTheTimeTable(timeTable);
		
		return timeTable;
	}
	
	
}
