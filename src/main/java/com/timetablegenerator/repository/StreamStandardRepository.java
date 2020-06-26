package com.timetablegenerator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.timetablegenerator.entity.StreamStandard;

public interface StreamStandardRepository extends JpaRepository<StreamStandard, Long>{

	@Query("Select standard from StreamStandard standard where standard.stream.college.id=?1")
	List<StreamStandard> getAllStreamStandards(Long collegeId);

}
