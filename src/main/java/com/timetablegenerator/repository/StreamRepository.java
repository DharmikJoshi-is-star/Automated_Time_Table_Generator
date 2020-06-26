package com.timetablegenerator.repository;

import  com.timetablegenerator.entity.Stream;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface StreamRepository extends JpaRepository<Stream ,Long>{

	@Query("Select stream from Stream stream where stream.college.id=?1")
	List<Stream> getAllStreamWithCollegeId(Long collegeId);

}
