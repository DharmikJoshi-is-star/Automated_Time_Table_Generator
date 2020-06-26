package com.timetablegenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetablegenerator.entity.College;
import com.timetablegenerator.repository.CollegeRepository;

@Service
public class CollegeService {

	@Autowired
	CollegeRepository collegeRepository;
	
	public College getCollegeWithId(Long id) {
		return collegeRepository.getOne(id);
	}

	public College getCollegeForFrontEnd(Long collegeId) {
		
		College college = collegeRepository.getOne(collegeId);
		
		college.getStreams().forEach((stream)->{
			
			stream.setCollege(null);
			
			stream.getStandards().forEach((standard)->{
				
				standard.setStream(null);
				standard.getSubjects().forEach(subject->{
					
					subject.setStreamStandard(null);
					subject.setFaculty(null);
					
				});
				
				standard.getPracticals().forEach(practical->{
					
					practical.setStreamStandard(null);
					practical.setFaculty(null);
					
				});
				
				standard.getBatchs().forEach(batch->{
					
					batch.setStreamStandard(null);
					
				});
				
			});
			
		});
		
		
		college.getFaculties().forEach(faculty->{
			
			faculty.setCollege(null);
			
		});
		
		return college;
	}

	
}
