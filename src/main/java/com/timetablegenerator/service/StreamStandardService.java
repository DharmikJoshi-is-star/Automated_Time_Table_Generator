package com.timetablegenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetablegenerator.entity.StreamStandard;
import com.timetablegenerator.repository.StreamStandardRepository;

@Service
public class StreamStandardService {

	@Autowired
	StreamStandardRepository streamStandardRepository;
	
	public List<StreamStandard> getAllStreamStandards(Long collegeId){
		
		
		List<StreamStandard> streamStandards = streamStandardRepository.getAllStreamStandards(collegeId);
		
		streamStandards.forEach(standard->{
			standard.setBatchs(null);
			standard.setPracticals(null);
			standard.getStream().setStandards(null);
			standard.getStream().setCollege(null);
			standard.setSubjects(null);
			
		});
		
		return streamStandards;
	}
	
}
