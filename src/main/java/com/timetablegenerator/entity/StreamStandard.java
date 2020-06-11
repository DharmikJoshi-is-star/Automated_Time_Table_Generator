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

@Getter @Setter
@Entity
public class StreamStandard {     

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "standard_name")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "streamStandard")
	private List<Subject> subjects;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "streamStandard")
	private List<Practical> practicals;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "streamStandard")
	private List<Batch> batchs;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "stream_id")
	private Stream stream;

	public StreamStandard(String name, Stream stream) {
		super();
		this.name = name;
		this.stream = stream;
	}

	
	
	public StreamStandard() {
		super();
		// TODO Auto-generated constructor stub
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

	public List<Batch> getBatchs() {
		return batchs;
	}

	public void setBatchs(List<Batch> batchs) {
		this.batchs = batchs;
	}

	public Stream getStream() {
		return stream;
	}

	public void setStream(Stream stream) {
		this.stream = stream;
	}

	
	
	
}

