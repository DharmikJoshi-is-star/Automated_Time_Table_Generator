package com.timetablegenerator.entity;

import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.timetablegenerator.enums.PreferredSession;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Practical {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long id;
	
	@Column(name = "practical_name")
	@Getter @Setter
	private String name;
	
	@Column(name = "practical_code_name")
	@Getter @Setter
	private String practicalCode;
	
	@Column(name = "preferred_session_timming")
	@Getter @Setter
	private String preferredSession;
	
	@Column(name = "total_session_hours")
	@Getter @Setter
	private Double sessionHours;
	
	@Column(name = "single_session_hours")
	@Getter @Setter
	private Double singleSession;
	
	@Column(name = "lab_no")
	@Getter @Setter
	private String lab;
	
	@Column(name = "is_practical_batch_wise")
	@Getter @Setter
	private boolean accordingToBatch;
	
	@Transient
	@Getter @Setter
	private HashMap<Batch, Boolean> batches;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "standard_id")
	@Getter @Setter
	private StreamStandard streamStandard;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "faculty_id")
	@Getter @Setter
	private Faculty faculty;

	
	
	public Practical(String name, String practicalCode, String preferredSession, Double sessionHours,
			Double singleSession, String lab, Boolean accordingToBatch,
			StreamStandard streamStandard) {
		super();
		this.name = name;
		this.practicalCode = practicalCode;
		this.preferredSession = preferredSession;
		this.sessionHours = sessionHours;
		this.singleSession = singleSession;
		this.lab = lab;
		this.accordingToBatch = accordingToBatch;
		this.streamStandard = streamStandard;
	}

	public Practical() {
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

	public String getPracticalCode() {
		return practicalCode;
	}

	public void setPracticalCode(String practicalCode) {
		this.practicalCode = practicalCode;
	}

	public String getPreferredSession() {
		return preferredSession;
	}

	public void setPreferredSession(String preferredSession) {
		this.preferredSession = preferredSession;
	}

	public Double getSessionHours() {
		return sessionHours;
	}

	public void setSessionHours(Double sessionHours) {
		this.sessionHours = sessionHours;
	}

	public Double getSingleSession() {
		return singleSession;
	}

	public void setSingleSession(Double singleSession) {
		this.singleSession = singleSession;
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public Boolean getAccordingToBatch() {
		return accordingToBatch;
	}

	public void setAccordingToBatch(Boolean accordingToBatch) {
		this.accordingToBatch = accordingToBatch;
	}


	public void setAccordingToBatch(boolean accordingToBatch) {
		this.accordingToBatch = accordingToBatch;
	}

	public StreamStandard getStreamStandard() {
		return streamStandard;
	}

	public void setStreamStandard(StreamStandard streamStandard) {
		this.streamStandard = streamStandard;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	
	
	public HashMap<Batch, Boolean> getBatches() {
		return batches;
	}

	public void setBatches(HashMap<Batch, Boolean> batches) {
		this.batches = batches;
	}

	public Practical[] sortAccordingToPreferredSession(Practical[] practicalArr, String prioritySession) {
		
		Practical[] practicals = new Practical[practicalArr.length];
		int priroityIndex = 0;
		int k=0;
		
		String[] sessions = {
			PreferredSession.Morning.toString(),
			PreferredSession.Noon.toString(),
			PreferredSession.Afternoon.toString(),
			PreferredSession.Evening.toString(),
			PreferredSession.Night.toString(),
			PreferredSession.Any.toString(),
		};
		
		for (int i = 0; i < sessions.length; i++) {
			if(sessions[i].equalsIgnoreCase(prioritySession)) {
				priroityIndex = i;
				break;
			}
			
		}
		
		for (int i = 0; i < practicalArr.length; i++) {
			if(practicalArr[i].getPreferredSession().equalsIgnoreCase( sessions[priroityIndex] )) {
				practicals[k++] = practicalArr[i];
				//System.out.println(subArr[i].getName());
			}
		}
	
		if(priroityIndex!=sessions.length-1) {
			for (int i = 0; i < practicalArr.length; i++) {
				if(practicalArr[i].getPreferredSession().equalsIgnoreCase( sessions[sessions.length-1] )) {
					practicals[k++] = practicalArr[i];
				}
			}
		}
	
		for (int j = 0; j < priroityIndex; j++) {
			for (int i = 0; i < practicalArr.length; i++) {
				if(practicalArr[i].getPreferredSession().equalsIgnoreCase( sessions[j] )) {
					practicals[k++] = practicalArr[i];
				}
			}
		}
		
		for (int j = priroityIndex+1; j < sessions.length-1; j++) {
			for (int i = 0; i < practicalArr.length; i++) {
				if(practicalArr[i].getPreferredSession().equalsIgnoreCase( sessions[j] )) {
					practicals[k++] = practicalArr[i];
				}
			}
		}
		
		return practicals;
	}

	
	
}
