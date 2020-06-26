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

import com.timetablegenerator.enums.PreferredSession;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long id;
	
	@Column(name = "suject_name")
	@Getter @Setter
	private String name;
	
	@Column(name = "subject_code_name")
	@Getter @Setter
	private String subjectCode;
	
	@Column(name = "preferred_timming")
	@Getter @Setter
	private String preferredSession;
	
	@Column(name = "total_hours_requirement")
	@Getter @Setter
	private Double sessionHours;
	
	@Column(name = "classroom_number")
	@Getter @Setter
	private String roomNo;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "standard_id")
	@Getter @Setter
	private StreamStandard streamStandard;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "faculty_id")
	@Getter @Setter
	private Faculty faculty;

	public Subject(Subject subject) {
		super();
		this.id = subject.getId();
		this.name = subject.getName();
		this.subjectCode = subject.getSubjectCode();
		this.preferredSession = subject.getPreferredSession();
		this.sessionHours = subject.getSessionHours();
		this.roomNo = subject.getRoomNo();
	}
	
	public Subject(String name, String subjectCode, String preferredSession, Double sessionHours, String roomNo,
			StreamStandard streamStandard, Faculty faculty) {
		super();
		this.name = name;
		this.subjectCode = subjectCode;
		this.preferredSession = preferredSession;
		this.sessionHours = sessionHours;
		this.roomNo = roomNo;
		this.streamStandard = streamStandard;
		this.faculty = faculty;
	}

	public Subject(String name, String subjectCode, String preferredSession, Double sessionHours, String roomNo) {
		super();
		this.name = name;
		this.subjectCode = subjectCode;
		this.preferredSession = preferredSession;
		this.sessionHours = sessionHours;
		this.roomNo = roomNo;
	}
	
	public Subject() {
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

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
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

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
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
	
	public Subject[] sortAccordingToPreferredSession(Subject[] subArr, String prioritySession) {
		
		Subject[] subjects = new Subject[subArr.length];
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
		
		for (int j = priroityIndex; j >= 0; j--) {
			for (int i = 0; i < subArr.length; i++) {
				if(subArr[i].getPreferredSession().equalsIgnoreCase( sessions[j] )) {
					subjects[k++] = subArr[i];
					//System.out.println(subArr[i].getName());
				}
			}
		}
		
	
		for (int i = 0; i < subArr.length; i++) {
			if(subArr[i].getPreferredSession().equalsIgnoreCase( sessions[sessions.length-1] )) {
				subjects[k++] = subArr[i];
			}
		}
	
		
		for (int j = priroityIndex+1; j < sessions.length-1; j++) {
			for (int i = 0; i < subArr.length; i++) {
				if(subArr[i].getPreferredSession().equalsIgnoreCase( sessions[j] )) {
					subjects[k++] = subArr[i];
				}
			}
		}
		
		return subjects;
	}
	
	
	
	
}
