package com.timetablegenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timetablegenerator.entity.College;

public interface CollegeRepository extends JpaRepository<College, Long>{

}      
