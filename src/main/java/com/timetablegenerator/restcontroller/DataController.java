package com.timetablegenerator.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;
import org.hibernate.annotations.Subselect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timetablegenerator.algorithm.TimeTableAlgorithmService;
import com.timetablegenerator.entity.Batch;
import com.timetablegenerator.entity.College;
import com.timetablegenerator.entity.Faculty;
import com.timetablegenerator.entity.Practical;
import com.timetablegenerator.entity.Stream;
import com.timetablegenerator.entity.StreamStandard;
import com.timetablegenerator.entity.Subject;
import com.timetablegenerator.repository.BatchRepository;
import com.timetablegenerator.repository.CollegeRepository;
import com.timetablegenerator.repository.FacultyRepository;
import com.timetablegenerator.repository.PracticalRepository;
import com.timetablegenerator.repository.StreamRepository;
import com.timetablegenerator.repository.StreamStandardRepository;
import com.timetablegenerator.repository.SubjectRepository;
import com.timetablegenerator.service.CollegeService;

@RestController
public class DataController {

	@Autowired
	TimeTableAlgorithmService ttas;
	
	@Autowired
	CollegeService collegeService;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	FacultyRepository facultyRepository;
	
	@Autowired
	CollegeRepository collegeRepository;
	
	@Autowired
	StreamRepository streamRepository;
	
	@Autowired
	StreamStandardRepository streamStandardRepository;
	
	@Autowired
	BatchRepository batchRepository;
	
	@Autowired
	PracticalRepository practicalRepository;
	
	String[] sessions = {"Morning","Noon","Afternoon","Any"};
	
	Double[] session_in_hrs = {new Double(2),new Double(4),new Double(6)};
			
	String roomNo = "502";
	
	int sessionIndex(int index) {
		if(index>=3) {
			return 3;
		}
		return index;
	}
	
	int sessionInHrsIndex(int index) {
		if(index>=3) {
			return index-session_in_hrs.length;
		}
		return index;
	}
	
	@GetMapping("/addSubject1")
	public String addSubject() {
		for (int j = 1; j <= 4; j++) {
			roomNo = (j+1)+"05";
			String stream = "ETRX_YEAR"+j+"_SUB";
			String code = "ETY"+j+"S";
			for (int i = 0; i < 6; i++) {
				
				subjectRepository.save(new Subject(stream+(i+1), code+(i+1), sessions[sessionIndex(i)], session_in_hrs[sessionInHrsIndex(i)], roomNo));
			}
			
		}
		
		return "done";
	}
	
	@GetMapping("/addTeacher1")
	public String addTeacher() {
	
		College c = collegeRepository.getOne(new Long("188"));
		for (int i = 66; i <= 74; i++) {
			if(c.getFaculties()!=null)
				c.getFaculties().add(facultyRepository.save(new Faculty("Faculty_"+i,"F"+i, c)));
			else {
				List<Faculty> faculties = new ArrayList<Faculty>();
				faculties.add(facultyRepository.save(new Faculty("Faculty_"+i,"F"+i, c)));
				c.setFaculties(faculties);
			}
			collegeRepository.save(c);
			
		}
	
		
		return "done";
	}
	
	@GetMapping("/addStream1")
	public String addStream() {
		
		College c = collegeRepository.getOne(new Long("188"));
		
		String[] streams = {"COMPUTER", "IT", "MECHANICS", "EXTC", "ETRX"};
		
		for (int i = 0; i < streams.length; i++) {
			if(c.getStreams()!=null)
				c.getStreams().add(streamRepository.save(new Stream(streams[i], c)));
			else {
				List<Stream> st = new ArrayList<Stream>();
				st.add(streamRepository.save(new Stream(streams[i], c)));
				c.setStreams(st);
			}
			
		}
		
		return "done";
	}
	
	
	@GetMapping("/addStreamStandard1")
	public String addStreamStandard() {
		
		List<Stream> streams= streamRepository.findAll();
		
		List<String> standards = new ArrayList<String>();
		standards.add("YEAR_1");
		standards.add("YEAR_2");
		standards.add("YEAR_3");
		standards.add("YEAR_4");
		
		streams.forEach((stream)->{
			standards.forEach((standard)->{
				if(stream.getStandards()!=null)
					stream.getStandards().add(streamStandardRepository.save( new StreamStandard(stream.getName()+"_"+standard, stream)) );
				else {
					List<StreamStandard> ss = new ArrayList<StreamStandard>();
					ss.add(streamStandardRepository.save( new StreamStandard(stream.getName()+"_"+standard, stream)));
					stream.setStandards(ss);
				}
			});
		});
		
		return "done";
	}
	
	@GetMapping("/setStandardAndSubject1")
	public String setStandardAndSubject() {
		
		List<Subject> subjects = subjectRepository.findAll();
		
		List<StreamStandard> standards = streamStandardRepository.findAll();
		
		standards.forEach((standard)->{
			String arr[] = standard.getName().split("_");
			String code = "";
			
			for (int i = 0; i < arr.length; i++) {
				code = code + arr[i].charAt(0);
				if(i==0)
					code = code + arr[i].charAt(1);
			}
			final String str = code;
			int len = code.length();
			
			subjects.forEach((subject)->{
				if(subject.getStreamStandard()==null) {
					if(subject.getSubjectCode().contains(str.subSequence(0, len))) {
						System.out.println(subject.getName());
						System.out.println(str);
						subject.setStreamStandard(standard);
						
						
						if(standard.getSubjects()!=null)
							standard.getSubjects().add(subjectRepository.save(subject));
						else {
							List<Subject> list = new ArrayList<Subject>();
							list.add(subjectRepository.save(subject));
							standard.setSubjects(list);
						}
						
						streamStandardRepository.save(standard);
						
					}
				}
			});
			
			
		});
		
		
		return "done";
	}
	
	@GetMapping("/addBatch1")
	public String addBatch() {
		
		streamStandardRepository.findAll().forEach((standard)->{
			for (int i = 1; i <= 3; i++) {
				if(standard.getBatchs()!=null) 
					standard.getBatchs().add(batchRepository.save( new Batch(standard.getName()+"_Batch_"+i, standard) ));
				else {
					List<Batch> batches = new ArrayList<>();
					batches.add(batchRepository.save( new Batch(standard.getName()+"_Batch_"+i, standard) ));
					standard.setBatchs(batches);
				}  
				streamStandardRepository.save(standard);
			}
			
		});
		
		return "done";
	}
	
	@GetMapping("/testData2")
	public College testData() {
		
		College college =  collegeRepository.getOne(new Long("188"));
		
		college.getStreams().forEach((stream)->{
			stream.setCollege(null);
			stream.getStandards().forEach((standard)->{
				standard.setStream(null);
				standard.getSubjects().forEach((subject)->{
					subject.setStreamStandard(null);
				});
			});
		});
		
		college.getFaculties().forEach((faculty)->{
			System.out.println(faculty.getName());
			faculty.setCollege(null);
		});
		
		return college;
	}
	
	@GetMapping("/testData1")
	public StreamStandard giveMeLab() {
		
		StreamStandard s = streamStandardRepository.getOne(new Long("259"));
		
		s.setStream(null);
		s.setSubjects(null);
		s.setBatchs(null);
		s.getPracticals().forEach((p)->{
			p.setStreamStandard(null);
		});
		
		
		return s;
	}
	
	String[][] labs = { 
						{"206", "207", "208", "209", "210", "211"},
						{"306", "307", "308", "309", "310", "311"},
						{"406", "407", "408", "409", "410", "411"},
						{"506", "507", "508", "509", "510", "511"}
					  };
	
	Random random = new Random();
	
	int incrementInLab(int index) {
		if(index==1) {
			int cpy = lab1;
			lab1 ++;
			if(lab1>211)
				lab1 = 206;
			return cpy;
		}
		if(index==2) {
			int cpy = lab2;
			lab2 ++;
			if(lab2>311)
				lab2 = 306;
			return cpy;
		}
		if(index==3) {
			int cpy = lab3;
			lab3 ++;
			if(lab3>411)
				lab3 = 406;
			return cpy;
		}
		if(index==4) {
			int cpy = lab4;
			lab4 ++;
			if(lab4>511)
				lab4 = 506;
			return cpy;
		}
		return 206;
	}
	
	int lab1 = 206;
	int lab2 = 306;
	int lab3 = 406;
	int lab4 = 506;
	@GetMapping("/addPractical1")
	public String addPractical() {
		
		
		List<Stream> streams = streamRepository.findAll();
		
		
		streams.forEach((stream)->{
			stream.getStandards().forEach((standard)->{
				
				if(standard.getName().contains("1")) {
					for (int i = 1; i <= 3; i++) {
						String practName = standard.getName()+"_"+"Pract_"+i;
						Integer labNo = incrementInLab(1);
						String[] practCodeArr = practName.split("_");
						String practCode = "";
						for (int j = 0; j < practCodeArr.length; j++)
							practCode = practCode + practCodeArr[j].charAt(0);
						
						Practical p = new Practical(practName, 
														practCode, 
															sessions[random.nextInt(4)], 
																new Double("2"), 
																	new Double("2"), 
																		labNo.toString(), 
																			true, 
																				standard);
						
						System.out.println(p.toString());
						System.out.println("Name: "+p.getName());
						System.out.println("PractCode: "+p.getPracticalCode());
						System.out.println("Lab no: "+p.getLab());
						
						
						if(standard.getPracticals()!=null)
							standard.getPracticals().add(practicalRepository.save(p));
						else {
							List<Practical> pList = new ArrayList<Practical>();
							pList.add(practicalRepository.save(p));
							standard.setPracticals(pList);
						}
						
						streamStandardRepository.save(standard);
						
					}
					
				}
				
				if(standard.getName().contains("2")) {
					for (int i = 1; i <= 3; i++) {
						String practName = standard.getName()+"_"+"Pract_"+i;
						Integer labNo = incrementInLab(2);
						String[] practCodeArr = practName.split("_");
						String practCode = "";
						for (int j = 0; j < practCodeArr.length; j++)
							practCode = practCode + practCodeArr[j].charAt(0);
						
						Practical p = new Practical(practName, 
														practCode, 
															sessions[random.nextInt(4)], 
																new Double("2"), 
																	new Double("2"), 
																		labNo.toString(), 
																			true, 
																				standard);
						
						System.out.println(p.toString());
						System.out.println("Name: "+p.getName());
						System.out.println("PractCode: "+p.getPracticalCode());
						System.out.println("Lab no: "+p.getLab());
						
						if(standard.getPracticals()!=null)
							standard.getPracticals().add(practicalRepository.save(p));
						else {
							List<Practical> pList = new ArrayList<Practical>();
							pList.add(practicalRepository.save(p));
							standard.setPracticals(pList);
						}
						
						streamStandardRepository.save(standard);
					
					}
				}
				if(standard.getName().contains("3")) {
					for (int i = 1; i <= 3; i++) {
						String practName = standard.getName()+"_"+"Pract_"+i;
						Integer labNo = incrementInLab(3);
						String[] practCodeArr = practName.split("_");
						String practCode = "";
						for (int j = 0; j < practCodeArr.length; j++)
							practCode = practCode + practCodeArr[j].charAt(0);
						
						Practical p = new Practical(practName, 
														practCode, 
															sessions[random.nextInt(4)], 
																new Double("2"), 
																	new Double("2"), 
																		labNo.toString(), 
																			true, 
																				standard);
						
						System.out.println(p.toString());
						System.out.println("Name: "+p.getName());
						System.out.println("PractCode: "+p.getPracticalCode());
						System.out.println("Lab no: "+p.getLab());
						
						if(standard.getPracticals()!=null)
							standard.getPracticals().add(practicalRepository.save(p));
						else {
							List<Practical> pList = new ArrayList<Practical>();
							pList.add(practicalRepository.save(p));
							standard.setPracticals(pList);
						}
						
						streamStandardRepository.save(standard);
					
					}
				}
				if(standard.getName().contains("4")) {
					for (int i = 1; i <= 3; i++) {
						String practName = standard.getName()+"_"+"Pract_"+i;
						Integer labNo = incrementInLab(4);
						String[] practCodeArr = practName.split("_");
						String practCode = "";
						for (int j = 0; j < practCodeArr.length; j++)
							practCode = practCode + practCodeArr[j].charAt(0);
						
						Practical p = new Practical(practName, 
														practCode, 
															sessions[random.nextInt(4)], 
																new Double("2"), 
																	new Double("2"), 
																		labNo.toString(), 
																			true, 
																				standard);
						
						System.out.println(p.toString());
						System.out.println("Name: "+p.getName());
						System.out.println("PractCode: "+p.getPracticalCode());
						System.out.println("Lab no: "+p.getLab());
						
						if(standard.getPracticals()!=null)
							standard.getPracticals().add(practicalRepository.save(p));
						else {
							List<Practical> pList = new ArrayList<Practical>();
							pList.add(practicalRepository.save(p));
							standard.setPracticals(pList);
						}
						
						streamStandardRepository.save(standard);
					
					}
				}
				
				
			});
		});
		
		/*		
		List<StreamStandard> standards = streamStandardRepository.findAll();
	
		standards.forEach((standard)->{
			
			String[] pracs = new String[]{"Pract_1", "Pract_2", "Pract_3"};
			
			for (int i = 0; i < pracs.length; i++) {
				
				String practName = standard.getName()+"_"+pracs[i];
				String[] practCodeArr = practName.split("_");
				String practCode = "";
				for (int j = 0; j < pracs.length; j++) {
					practCode = practCode + practCodeArr[j];
				}
				
				new Practical(practName, practCode, sessions[random.nextInt(4)], 
								new Double("2"), new Double("2"), lab, true, batches, standard);
			}
			
		});
		*/
		
		return "done";
	}
	  
	Faculty returnFaculty(List<Faculty> faculties) {
		Faculty faculty = faculties.get( random.nextInt(faculties.size()) );
		if( faculty.getPracticals()==null )
			return faculty;
		else {
			if(faculty.getPracticals().size()>=2)
				return returnFaculty(faculties);
			else
				return faculty;
		}
	}
	
	@GetMapping("/assignPractical1")
	public String assignPractical() {
		List<Practical> practicals = practicalRepository.findAll();
		
		List<Faculty> faculties = facultyRepository.findAll();
		
		practicals.forEach((practical)->{
			Faculty faculty = returnFaculty(faculties);
			if(faculty.getPracticals()==null) {
				faculty.setPracticals( new ArrayList<Practical>() );
				faculty = facultyRepository.save( faculty );
			}
			practical.setFaculty(faculty);
			faculty.getPracticals().add(practicalRepository.save(practical));
			facultyRepository.save( faculty );
			
		});
		
		
		
		
		return "done";
	}
	
	int count1 = 0;
	int count2 = 0;
	int count0 = 0;
	int otherCount = 0;
	@GetMapping("/testData3")
	public String testData3() {
		
		List<Faculty> faculties = facultyRepository.findAll();
		
		faculties.forEach((faculty)->{
		
			if(faculty.getPracticals().size()==0) 
				count0++;
			else if(faculty.getPracticals().size()==1)
				count1++;
			else if(faculty.getPracticals().size()==2)
				count2++;
			else if(faculty.getPracticals().size()>2)
				otherCount++;
			
		});
		
		System.out.println("count0: "+count0);
		System.out.println("count1: "+count1);
		System.out.println("count2: "+count2);
		System.out.println("otherCount: "+otherCount);
		
		return "done";
	}
	
	Subject returnSubject(List<Subject> subjects) {
		Subject subject = subjects.get( random.nextInt(subjects.size()) );
		if( subject.getFaculty()==null )
			return subject;
		else
			return returnSubject(subjects);
	 }
	
	Faculty returnFacultyForSubject(List<Faculty> faculties) {
		Faculty faculty = faculties.get( random.nextInt(faculties.size()) );
		if( faculty.getPracticals().size()+faculty.getSubjects().size() >=3 ) {
			return returnFacultyForSubject(faculties);
		}
		else
			return faculty;
	 }
	
	@GetMapping("/facultyToSubject1")
	public String facultyToSubject() {
		
		List<Faculty> faculties = facultyRepository.findAll();
		List<Subject> subjects = subjectRepository.findAll();
		
		faculties.forEach((faculty)->{
			if(faculty.getPracticals()==null) {
				faculty.setPracticals(new ArrayList<Practical>());
				faculty = facultyRepository.save(faculty);
			}
			if(faculty.getSubjects()==null) {
				faculty.setSubjects(new ArrayList<Subject>());
				faculty = facultyRepository.save(faculty);
			}	
		});
		
		subjects.forEach((subject)->{
			Faculty faculty = returnFacultyForSubject(facultyRepository.findAll());
			subject.setFaculty(faculty);
			faculty.getSubjects().add(subjectRepository.save(subject));
			facultyRepository.save(faculty);
		});
		
		return "done";
	}
	
	@GetMapping("/testData4")
	public String testData4() {
	
		List<Subject> subjects = subjectRepository.findAll();
		subjects.forEach((subject)->{
			if(subject.getFaculty()==null)
					System.out.println("null is here");
		});
		List<Faculty> faculties = facultyRepository.findAll();
		count0 = 0;
		count1  = 0;
		count2  = 0; 
		faculties.forEach((faculty)->{
			if(faculty.getPracticals().size() + faculty.getSubjects().size() ==3 )	
				count2++;
			if(faculty.getPracticals().size() + faculty.getSubjects().size() ==2 )	
				count1++;
			if(faculty.getPracticals().size() + faculty.getSubjects().size() ==1 )	
				count0++;
			
		});
		
		System.out.println("total allotment for 3 lect are : "+count2);
		System.out.println("total allotment for 2 lect are : "+count1);
		System.out.println("total allotment for 1 lect are : "+count0);
		
		return "done";
	}
	
	
	
	@GetMapping("/changeSubjectAndFaculty1")
	public String changeSubjectAndFaculty() {
		List<Faculty> faculties = facultyRepository.findAll();
		faculties.forEach((faculty)->{
			
			Faculty fac = returnFacultyForSubject(facultyRepository.findAll());
			
			if(faculty.getPracticals().size() + faculty.getSubjects().size() >= 3 ) {
				if(faculty.getPracticals().size() >= 2) {
					
					List<Practical> tempPrac = faculty.getPracticals();
					Practical prac = tempPrac.remove(tempPrac.size()-1);
					faculty.setPracticals(tempPrac);
					facultyRepository.save(faculty);
					
					prac.setFaculty(fac);
					fac.getPracticals().add( practicalRepository.save(prac) );
					facultyRepository.save(fac);
				}
				if(faculty.getSubjects().size() >=2 ) {
					
					List<Subject> tempSubject = faculty.getSubjects();
					Subject sub = tempSubject.remove(tempSubject.size()-1);
					faculty.setSubjects(tempSubject);
					facultyRepository.save(faculty);
					
					sub.setFaculty(fac);
					fac.getSubjects().add( subjectRepository.save(sub) );
					facultyRepository.save(fac);
					
				}
			}
				
			
		});
		return "done";
	}
	
	
}
