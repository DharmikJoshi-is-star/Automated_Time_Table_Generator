package com.timetablegenerator.repository;

import  com.timetablegenerator.entity.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StreamRepository extends JpaRepository<Stream ,Long>{

}
