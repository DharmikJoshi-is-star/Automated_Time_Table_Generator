package com.timetablegenerator.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Faculty {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)  
	@Getter @Setter
	private Long id;
	
	@Column(name = "faculty_name")
	@Getter @Setter
	private String name;
	
	@Column(name = "faculty_code_name")
	@Getter @Setter
	private String facultyCode;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "faculty")
	@Getter @Setter
	private List<Subject> subjects;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "faculty")
	@Getter @Setter
	private List<Practical> practicals;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "college_id")
	@Getter @Setter 
	private College college;

	public Faculty() {
		super();
		
	}

	public Faculty(Faculty faculty) {
		super();
		this.id  = faculty.getId();
		this.name = faculty.getName();
		this.facultyCode = faculty.getFacultyCode();
	}
	
	public Faculty(String name, String facultyCode) {
		super();
		this.name = name;
		this.facultyCode = facultyCode;
	}

	public Faculty(String name, String facultyCode, College college) {
		super();
		this.name = name;
		this.facultyCode = facultyCode;
		this.college = college;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFacultyCode() {
		return facultyCode;
	}

	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Practical> getPracticals() {
		return practicals;
	}

	public void setPracticals(List<Practical> practicals) {
		this.practicals = practicals;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}
	
	
	
	
}
