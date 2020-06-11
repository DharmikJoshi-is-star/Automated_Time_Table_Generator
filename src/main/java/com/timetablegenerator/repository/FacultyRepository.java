package com.timetablegenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timetablegenerator.entity.Faculty;


public interface FacultyRepository extends JpaRepository<Faculty, Long>{

}
      