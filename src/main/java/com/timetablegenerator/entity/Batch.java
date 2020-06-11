package com.timetablegenerator.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Batch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long id;
	
	@Column(name = "batch_name")
	@Getter @Setter
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "standard_id")
	@Getter @Setter
	private StreamStandard streamStandard;

	
	
	public Batch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Batch(String name, StreamStandard streamStandard) {
		super();
		this.name = name;
		this.streamStandard = streamStandard;
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

	public StreamStandard getStreamStandard() {
		return streamStandard;
	}

	public void setStreamStandard(StreamStandard streamStandard) {
		this.streamStandard = streamStandard;
	}
	
	
}
