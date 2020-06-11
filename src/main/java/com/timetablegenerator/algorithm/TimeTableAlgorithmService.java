package com.timetablegenerator.algorithm;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timetablegenerator.entity.Batch;
import com.timetablegenerator.entity.College;
import com.timetablegenerator.entity.Faculty;
import com.timetablegenerator.entity.Practical;
import com.timetablegenerator.entity.Stream;
import com.timetablegenerator.entity.StreamStandard;
import com.timetablegenerator.entity.Subject;
import com.timetablegenerator.service.CollegeService;

@Component
public class TimeTableAlgorithmService {

	@Autowired
	private CollegeService collegeService;
	
	Subject[][][] subjects;
	
	Faculty[][][] faculties;
	
	Stream[] streams;
	
	StreamStandard[][] streamStandards;
	
	Practical[][][] practicals;
	
	public void hello() {
		System.out.println("Hello World");
	}
	
	public void getStreamStandard(Long collegeId) {
		College college = collegeService.getCollegeWithId(collegeId);
		
		Stream[] streams = new Stream[college.getStreams().size()];
		
		for (int i = 0; i < streams.length; i++) {
			streams[i] = college.getStreams().get(i);
		}
		
		this.streams = streams;
		
		StreamStandard[][]  streamStandards = new StreamStandard[college.getStreams().size()][];
		
		for (int i = 0; i < streamStandards.length; i++) {
			streamStandards[i] = new StreamStandard[college.getStreams().get(i).getStandards().size()];
			for (int j = 0; j < streamStandards[i].length; j++) {
				streamStandards[i][j] = college.getStreams().get(i).getStandards().get(j);
			}
		}
		
		this.streamStandards = streamStandards;
	}
	
	public void getStreamStandardPracticalArray(Long collegeId) {
		College college = collegeService.getCollegeWithId(collegeId);
		
		Stream[] streams = new Stream[college.getStreams().size()];
		
		for (int i = 0; i < streams.length; i++) {
			streams[i] = college.getStreams().get(i);
		}
		
		this.streams = streams;
		
		StreamStandard[][]  streamStandards = new StreamStandard[college.getStreams().size()][];
		
		for (int i = 0; i < streamStandards.length; i++) {
			streamStandards[i] = new StreamStandard[college.getStreams().get(i).getStandards().size()];
			for (int j = 0; j < streamStandards[i].length; j++) {
				streamStandards[i][j] = college.getStreams().get(i).getStandards().get(j);
			}
		}
		
		this.streamStandards = streamStandards;
	
		Practical[][][] practicals = new Practical[college.getStreams().size()][1][1];
		
		for (int i = 0; i < practicals.length; i++) {
			practicals[i] = new Practical[college.getStreams().get(i).getStandards().size()][];
			for (int j = 0; j < practicals[i].length; j++) {
				practicals[i][j] = new Practical[college.getStreams().get(i).getStandards().get(j).getPracticals().size()];
				for (int k = 0; k < practicals[i][j].length; k++) {
					
					if( college.getStreams().get(i).getStandards().get(j).getPracticals().get(k).getAccordingToBatch() ) {
						
						HashMap<Batch, Boolean> batches = new HashMap<Batch, Boolean>();
						college.getStreams().get(i).getStandards().get(j).getBatchs().forEach((batch)->{
							batches.put(batch, false);
						});
						college.getStreams().get(i).getStandards().get(j).getPracticals().get(k).setBatches(batches);
						practicals[i][j][k] = college.getStreams().get(i).getStandards().get(j).getPracticals().get(k);
						
					}
					
					
				}
			}
		}
		
		
		
		
		this.practicals = practicals;
		
		Faculty[][][] faculties = new Faculty[college.getStreams().size()][1][1];
		
		for (int i = 0; i < faculties.length; i++) {
			faculties[i] = new Faculty[college.getStreams().get(i).getStandards().size()][];
			
			for (int j = 0; j < faculties[i].length; j++) {
				faculties[i][j] = new Faculty[college.getStreams().get(i).getStandards().get(j).getPracticals().size()];
				
				for (int k = 0; k < faculties[i][j].length; k++) {
					faculties[i][j][k] = practicals[i][j][k].getFaculty();
				}
			}
		}
		
		this.faculties = faculties;
		
		
	}
	
	
	public void getStreamStandardSubjectArray(Long collegeId) {
		
		College college = collegeService.getCollegeWithId(collegeId);
		
		Stream[] streams = new Stream[college.getStreams().size()];
		
		for (int i = 0; i < streams.length; i++) {
			streams[i] = college.getStreams().get(i);
		}
		
		this.streams = streams;
		
		StreamStandard[][]  streamStandards = new StreamStandard[college.getStreams().size()][];
		
		for (int i = 0; i < streamStandards.length; i++) {
			streamStandards[i] = new StreamStandard[college.getStreams().get(i).getStandards().size()];
			for (int j = 0; j < streamStandards[i].length; j++) {
				streamStandards[i][j] = college.getStreams().get(i).getStandards().get(j);
			}
		}
		
		this.streamStandards = streamStandards;
		
		Subject[][][] subjects = new Subject[college.getStreams().size()][1][1];
		
		Faculty[][][] faculties = new Faculty[college.getStreams().size()][1][1];
		
		//Below three nested loop will create a 3-D array for subject
		//Structure for this 3D array is
		/*
		 *	Stream
		 *	| 
		 *  --Standard
		 *  	|
		 *  	--Subject
		 */
		for (int i = 0; i < subjects.length; i++) {
			subjects[i] = new Subject[college.getStreams().get(i).getStandards().size()][];
			
			for (int j = 0; j < subjects[i].length; j++) {
				subjects[i][j] = new Subject[college.getStreams().get(i).getStandards().get(j).getSubjects().size()];
				
				for (int k = 0; k < subjects[i][j].length; k++) {
					subjects[i][j][k] = college.getStreams().get(i).getStandards().get(j).getSubjects().get(k);
				}
			}
		}
		
		/*
		for (int i = 0; i < subjects.length; i++) {
			System.out.println("Stream No: "+ i);
			for (int j = 0; j < subjects[i].length; j++) {
				System.out.println("Stream Standard No: "+ j);
				for (int k = 0; k < subjects[i][j].length; k++) {
					System.out.println("Subject Name: "+ subjects[i][j][k].getName());
				}
			}
		}
		*/
		this.subjects = subjects;
		
		//Below three nested loop will create a 3-D array for Faculties
		//Structure for this 3D array is
		/*
		 *	Stream
		 *	| 
		 *  --Standard
		 *  	|
		 *  	--Faculty
		 */
		for (int i = 0; i < faculties.length; i++) {
			faculties[i] = new Faculty[college.getStreams().get(i).getStandards().size()][];
			
			for (int j = 0; j < faculties[i].length; j++) {
				faculties[i][j] = new Faculty[college.getStreams().get(i).getStandards().get(j).getSubjects().size()];
				
				for (int k = 0; k < faculties[i][j].length; k++) {
					faculties[i][j][k] = subjects[i][j][k].getFaculty();
				}
			}
		}
		/*
		for (int i = 0; i < subjects.length; i++) {
			System.out.println("Stream No: "+ i);
			for (int j = 0; j < subjects[i].length; j++) {
				System.out.println("Stream Standard No: "+ j);
				for (int k = 0; k < subjects[i][j].length; k++) {
					System.out.println("Faculty Name: "+ faculties[i][j][k].getName());
				}
			}
		}
		*/
		this.faculties = faculties;
		
		
	}
	
	
	
}
