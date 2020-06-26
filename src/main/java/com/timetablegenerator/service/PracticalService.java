package com.timetablegenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetablegenerator.entity.Practical;
import com.timetablegenerator.repository.PracticalRepository;

@Service
public class PracticalService {


	@Autowired  
	PracticalRepository practicalRepository;
	
	public List<Practical> getAllPracticals(Long collegeId){
		
		List<Practical> practicals = practicalRepository.getAllPracticalsWithCollegeId(collegeId);
		
		practicals.forEach(practical->{
			practical.setBatch(null);
			practical.setBatches(null);
			practical.setFaculty(null);
			practical.setStreamStandard(null);
		});
		
		return practicals;
	}
	
	
}
