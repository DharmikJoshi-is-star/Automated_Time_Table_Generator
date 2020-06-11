package com.timetablegenerator.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timetablegenerator.algorithm.TimeTableAlgorithm;

@RestController
public class algorithmTestController {

	@Autowired
	TimeTableAlgorithm timeTableAlgorithm;

	@GetMapping("/testComponent")
	public String testComponent() {
		//System.out.println(collegeService.getCollegeWithId(new Long("188")).getName());;
		timeTableAlgorithm.createTable();
		//timeTableAlgorithm.addPracticalToTimeTable();
		///ttas.getStreamStandardSubjectArray();
		return "done";
	}
}
