package com.timetablegenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timetablegenerator.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

}
