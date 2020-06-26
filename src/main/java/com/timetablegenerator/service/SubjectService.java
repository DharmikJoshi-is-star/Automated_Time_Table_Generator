package com.timetablegenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetablegenerator.entity.Subject;
import com.timetablegenerator.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	SubjectRepository subjectRepository;
	
	public List<Subject> getAllSubjects(Long collegeId){
		
		List<Subject> subjects = subjectRepository.getAllSubjects(collegeId);
		
		subjects.forEach(subject->{
			subject.setFaculty(null);
			subject.setStreamStandard(null);
		});
		
		
		return subjects;
	}
	
}
