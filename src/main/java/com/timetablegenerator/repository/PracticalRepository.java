package com.timetablegenerator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.timetablegenerator.entity.Practical;

public interface PracticalRepository extends JpaRepository<Practical, Long>{

	@Query("Select practical from Practical practical where practical.streamStandard.stream.college.id=?1")
	List<Practical> getAllPracticalsWithCollegeId(Long collegeId);

}
