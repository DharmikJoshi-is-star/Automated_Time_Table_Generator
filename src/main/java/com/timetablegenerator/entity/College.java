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
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class College {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "college_name")
	private String name;  
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "college")
	private List<Stream> streams;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "college")
	private List<Faculty> faculties;

	public College(String name) {
		super();
		this.name = name;
	}

	public College() {
		super();
		
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

	public List<Stream> getStreams() {
		return streams;
	}

	public void setStreams(List<Stream> streams) {
		this.streams = streams;
	}

	public List<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
	}
	
	
	
}
