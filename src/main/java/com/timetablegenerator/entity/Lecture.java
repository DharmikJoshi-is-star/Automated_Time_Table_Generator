package com.timetablegenerator.entity;

import java.util.HashMap;

import javax.persistence.Entity;


public class Lecture {

	private Long id;
	
	private boolean isSubject;
	
	private Subject subject;
	
	private Practical practical;
	
	private Integer lectureNo;
	
	private String lectureSession;
	
	private String lectureHour;
	
	private String lectureDay;
	
	private String timming;

	private Faculty faculty;
	
	private boolean isBatchPractical;

	private HashMap<Practical, Batch> practicalBatchWise;
	
	public Lecture(Integer lectureNo, String lectureSession, String lectureHour, String lectureDay, String timming) {
		super();
		this.lectureNo = lectureNo;
		this.lectureSession = lectureSession;
		this.lectureHour = lectureHour;
		this.lectureDay = lectureDay;
		this.timming = timming;
	}

	public Lecture(Boolean isSubject, Subject subject, Practical practical, Integer lectureNo, String lectureSession,
			String lectureHour, String lectureDay, String timming) {
		super();
		this.isSubject = isSubject;
		this.subject = subject;
		this.practical = practical;
		this.lectureNo = lectureNo;
		this.lectureSession = lectureSession;
		this.lectureHour = lectureHour;
		this.lectureDay = lectureDay;
		this.timming = timming;
	}

	public Lecture() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsSubject() {
		return isSubject;
	}

	public void setIsSubject(Boolean isSubject) {
		this.isSubject = isSubject;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Practical getPractical() {
		return practical;
	}

	public void setPractical(Practical practical) {
		this.practical = practical;
	}

	public Integer getLectureNo() {
		return lectureNo;
	}

	public void setLectureNo(Integer lectureNo) {
		this.lectureNo = lectureNo;
	}

	public String getLectureSession() {
		return lectureSession;
	}

	public void setLectureSession(String lectureSession) {
		this.lectureSession = lectureSession;
	}

	public String getLectureHour() {
		return lectureHour;
	}

	public void setLectureHour(String lectureHour) {
		this.lectureHour = lectureHour;
	}

	public String getLectureDay() {
		return lectureDay;
	}

	public void setLectureDay(String lectureDay) {
		this.lectureDay = lectureDay;
	}

	public String getTimming() {
		return timming;
	}

	public void setTimming(String timming) {
		this.timming = timming;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Boolean getIsBatchPractical() {
		return isBatchPractical;
	}

	public void setIsBatchPractical(Boolean isBatchPractical) {
		this.isBatchPractical = isBatchPractical;
	}

	public HashMap<Practical, Batch> getPracticalBatchWise() {
		return practicalBatchWise;
	}

	public void setPracticalBatchWise(HashMap<Practical, Batch> practicalBatchWise) {
		this.practicalBatchWise = practicalBatchWise;
	}

	
	
	
	
}
