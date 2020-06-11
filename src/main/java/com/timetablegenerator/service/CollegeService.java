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

	
}
