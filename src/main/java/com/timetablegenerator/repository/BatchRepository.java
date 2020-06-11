package com.timetablegenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timetablegenerator.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long> {

}
