package com.timetablegenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetablegenerator.entity.Stream;
import com.timetablegenerator.repository.StreamRepository;

@Service
public class StreamService {

	@Autowired
	StreamRepository streamRepository;
	
	public List<Stream> getAllStreams(Long collegeId) {
		
		return streamRepository.getAllStreamWithCollegeId(collegeId);
		
	}

}
