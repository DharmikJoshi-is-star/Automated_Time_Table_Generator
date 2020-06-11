package com.timetablegenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timetablegenerator.entity.Practical;

public interface PracticalRepository extends JpaRepository<Practical, Long>{

}
