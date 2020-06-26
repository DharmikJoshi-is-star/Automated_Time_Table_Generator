package com.timetablegenerator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.timetablegenerator.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

	@Query("Select subject from Subject subject where subject.streamStandard.stream.college.id=?1")
	List<Subject> getAllSubjects(Long collegeId);

}
