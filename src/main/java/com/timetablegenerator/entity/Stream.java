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
public class Stream {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter 
	private Long id;
	
	@Column(name = "stream_name")
	@Getter @Setter 
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stream")
	@Getter @Setter 
	private List<StreamStandard> standards;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "college_id")
	@Getter @Setter 
	private College college;

	public Stream() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Stream(String name, College college) {
		super();
		this.name = name;
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

	public List<StreamStandard> getStandards() {
		return standards;
	}

	public void setStandards(List<StreamStandard> standards) {
		this.standards = standards;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}
	
	
	
}
