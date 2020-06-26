package com.timetablegenerator.algorithm;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timetablegenerator.entity.Batch;
import com.timetablegenerator.entity.Faculty;
import com.timetablegenerator.entity.Lecture;
import com.timetablegenerator.entity.Practical;
import com.timetablegenerator.entity.Subject;
import com.timetablegenerator.enums.PreferredSession;

@Component
public class TimeTableAlgorithm {

	@Autowired        
	TimeTableAlgorithmService timeTableAlgorithmService;
	
	final Integer TOTAL_NO_OF_DAYS = 5;
	
	final Integer lectureStartsFrom = 9; 
	
	final Integer TOTAL_NO_OF_LECTURES_IN_DAY = 7;

	final Integer TOTAL_NO_OF_STREAM_STANDARDS = 4;
	
	final Integer TOTAL_NO_OF_STREAM_STANDARD_SUBJECTS = 6;
	
	final Double oneLectureHrs = 1.0;
	
	Lecture[][][][] lectures;
	
	String[] days = {
			DayOfWeek.MONDAY.toString(),	
			DayOfWeek.TUESDAY.toString(),
			DayOfWeek.WEDNESDAY.toString(),
			DayOfWeek.THURSDAY.toString(),
			DayOfWeek.FRIDAY.toString(),
			DayOfWeek.SATURDAY.toString(),
			DayOfWeek.SUNDAY.toString(),
		};
	
	public void setUpPreLectureDetails() {
		
		Integer tempLectureTimming = lectureStartsFrom;
		
		for (int day = 0; day < lectures.length; day++) {
			
			for (int stream = 0; stream < lectures[day].length; stream++) {
				for (int streamYears = 0; streamYears < lectures[day][stream].length; streamYears++) {
					tempLectureTimming = lectureStartsFrom;
					for (int lecture = 0; lecture < lectures[day][stream][streamYears].length; lecture++) {
						
						Lecture lectureObj = new Lecture();
						
						if(tempLectureTimming>=6 && tempLectureTimming<12) 
							lectureObj.setLectureSession( PreferredSession.Morning.toString() );
						else if(tempLectureTimming==12) 
							lectureObj.setLectureSession( PreferredSession.Noon.toString() );
						else if(tempLectureTimming>12 && tempLectureTimming<=16) 
							lectureObj.setLectureSession( PreferredSession.Afternoon.toString() );
						else if(tempLectureTimming>16 && tempLectureTimming<=20) 
							lectureObj.setLectureSession( PreferredSession.Evening.toString() );
						else if(tempLectureTimming>20 && tempLectureTimming<=24) 
							lectureObj.setLectureSession( PreferredSession.Night.toString() );
						
						lectureObj.setLectureDay(days[0]);
						
						lectureObj.setLectureHour(oneLectureHrs.toString());
						
						lectureObj.setLectureNo(lecture+1);
						
						lectureObj.setTimming(tempLectureTimming.toString());
						
						lectureObj.setIsBatchPractical(false);
						
						lectureObj.setIsSubject(false);
		
						lectureObj.setPractical(null);
						
						lectureObj.setSubject(null);
						
						tempLectureTimming++;
						
						lectures[day][stream][streamYears][lecture] = lectureObj;
					}
				}
			}
		}
		
		
	}
	
	public void createTable() {
		
		timeTableAlgorithmService.getStreamStandard(new Long("188"));

		final Integer TOTAL_NO_OF_STREAMS = timeTableAlgorithmService.streams.length;

		/*
		 * Days
		 * |
		 * --streams (in a single day there are n no of streams)
		 * 	 |
		 * 	 --courses(stream_years) (in a single stream there are m no of courses/standard/years)
		 * 	   |
		 * 	   --lectures (in a single courses/standard/years there are k no of lectures taken )
		 * 
		 */
		lectures= new Lecture[TOTAL_NO_OF_DAYS][TOTAL_NO_OF_STREAMS][][];

		for (int i = 0; i < TOTAL_NO_OF_DAYS; i++) {
			for (int j = 0; j < timeTableAlgorithmService.streams.length; j++) {
				lectures[i][j] = new Lecture[timeTableAlgorithmService.streamStandards[i].length][TOTAL_NO_OF_LECTURES_IN_DAY];
			}
		}
		
		setUpPreLectureDetails();
		
		timeTableAlgorithmService.getStreamStandardPracticalArray(new Long("188"));

		addPracticalToTimeTable();
	
		//from the below program we will start add Subejcts to the timeTable
		timeTableAlgorithmService.getStreamStandardSubjectArray(new Long("188"));
		
		int countTheCall = 0;
		
		for (int day = 0; day < TOTAL_NO_OF_DAYS; day++) {
			
			for (int lecture = 0; lecture < TOTAL_NO_OF_LECTURES_IN_DAY; lecture++) {
				
				for (int stream = 0; stream < lectures[day].length; stream++) {
					
					for (int standard = 0; standard < lectures[day][stream].length; standard++) {
			
						Lecture setUplecture = lectures[day][stream][standard][lecture];
						
						if(setUplecture.getPractical()!=null || setUplecture.getPracticalBatchWise()!=null)	continue;
						
						List<Subject> subjectList = setSubjectListForStreamStandard(stream, standard, setUplecture.getLectureSession());
						List<Faculty> facultyTeachesSubjectList = setFacultyTeachesSubjectList(stream, standard, subjectList);				
						List<Faculty> tempFacultyToRemove = setTempFacultyToRemove(day, lecture);
			 			
						facultyTeachesSubjectList = removeFacultyIfBusyFrom_subjectList_facultyTeachesSubjectList(subjectList, facultyTeachesSubjectList, tempFacultyToRemove);
						
						
							if(subjectList.size()==0)	continue;
							
						
							if(checkIfAllSubjectsAreFinished(subjectList))	continue;
						
							Subject selectedSubject = selectTheSubject(subjectList, null);
							
							if( checkIfSelectedSubjectIsNotRepeatedTwiceInADay(day, stream, standard, lecture, selectedSubject) ) {
								
								if(!subjectListHaveOnlyOneSubjectLeft(subjectList)) {
									
									selectedSubject = selectTheSubject(subjectList, selectedSubject);
									if(selectedSubject==null)
										continue;
									countTheCall++;	
									
								}else {
									
									if(checkIfSelectSubjectIsRepeatedConsecutively(day, stream, standard, lecture, selectedSubject)) {
										
										selectedSubject = selectTheSubject(subjectList, selectedSubject);
										if(selectedSubject==null)
											continue;
										countTheCall++;	
										
									}
								}
								
							}
							
							decreaseTheSessionByAnHour(stream, standard, selectedSubject, oneLectureHrs);
		
							setUplecture.setIsSubject(true);
							setUplecture.setSubject( new Subject(selectedSubject) );
							setUplecture.setFaculty( new Faculty(selectedSubject.getFaculty()) );
		
							lectures[day][stream][standard][lecture] = setUplecture;
						  
					}
				}
			}
		}
	
		countTheCall = 0;
		for (int i = 0; i <timeTableAlgorithmService.subjects.length; i++) {
			for (int j = 0; j < timeTableAlgorithmService.subjects[i].length; j++) {
				for (int j2 = 0; j2 < timeTableAlgorithmService.subjects[i][j].length; j2++) {
					if(timeTableAlgorithmService.subjects[i][j][j2].getSessionHours()>0)
						countTheCall++;
				}
			}
		}
		
		System.out.println("count for the subject is session is empty: "+countTheCall);
		
		printTheTable();
		
	}
	
	private boolean subjectListHaveOnlyOneSubjectLeft(List<Subject> subjectList) {
		int countNonZeroSessionSubject = 0;
		for (Subject subject : subjectList) {
			if(subject.getSessionHours()>0) {
				countNonZeroSessionSubject++;
			}
		}
		
		if(countNonZeroSessionSubject==1)
			return true;
		
		return false;
	}

	private boolean checkIfSelectSubjectIsRepeatedConsecutively(int day, int stream, int standard, int lecture,
			Subject selectedSubject) {
		int countLec = 0;
		
		for (int prevLectures = lecture-1; prevLectures >= 1 ; prevLectures--) {
			if(lectures[day][stream][standard][prevLectures].getSubject()!=null &&
					lectures[day][stream][standard][prevLectures-1].getSubject()!=null)
				if(lectures[day][stream][standard][prevLectures].getSubject().getId()==selectedSubject.getId() && 
						lectures[day][stream][standard][prevLectures-1].getSubject().getId()==selectedSubject.getId()) 
					countLec++;
		}
		
		if(countLec>=2)
			return true;
		
		return false;
	}

	public void addPracticalToTimeTable() {
	
		for (int stream = 0; stream < timeTableAlgorithmService.practicals.length; stream++) {
			for (int standard = 0; standard < timeTableAlgorithmService.practicals[stream].length; standard++) {
				for (int practical = 0; practical < timeTableAlgorithmService.practicals[stream][standard].length; practical++) {
					
					Practical practicalObj = timeTableAlgorithmService.practicals[stream][standard][practical];
					
					boolean setPracticalWithSession = setThePractical(stream, standard, practicalObj, false);
					if(setPracticalWithSession==false) {
						boolean setPracticalWithoutSession = setThePractical(stream, standard, practicalObj, true);
						if(setPracticalWithoutSession==false) {
							System.out.println("NOt able to assign"+ practicalObj.getName());
						}
					}
						
				}
			}
		}
		
	}

	private boolean setThePractical(int stream, int standard, Practical practicalObj, boolean withoutSession) {
		
		
		for (int day = 0; day < lectures.length; day++) {


			if(checkIfPracticalsAreAlreadyTakenOnSameDay(day, stream, standard)) {
				continue;
			}
			
			for (int lecture = 0; lecture < lectures[day][stream][standard].length-1; lecture++) {
				
				//i have change here
				if( lectures[day][stream][standard][lecture].getLectureSession().equalsIgnoreCase(practicalObj.getPreferredSession()) ||
						withoutSession){
				//if( !lectures[day][stream][standard][lecture].getLectureSession().isEmpty()){
					
					if( ( lectures[day][stream][standard][lecture].getIsSubject() )==true || 
							( lectures[day][stream][standard][lecture+1].getIsSubject() )==true) {
						continue;
					}
					else if(lectures[day][stream][standard][lecture].getPractical()!=null ||
							lectures[day][stream][standard][lecture+1].getPractical()!=null) {
						continue;
					}else if(lectures[day][stream][standard][lecture].getIsBatchPractical() ||
								lectures[day][stream][standard][lecture+1].getIsBatchPractical()) {
						
						continue;
					}
					else {
						
						/*
						 * 1. find a lecture whose session is equal to the practical session
						 * 2. check if the room no if free or not
						 * 3. check if the teacher is free or not
						 * 4. check if batch wise or not if not then assign the practical to lecture
						 * 5. if batch wise then find the 
						 * 6. how many batches are there find practical for them set practicals
						 * 7. assign the practical to batch
						*/
						
						
						if( (practicalObj.getAccordingToBatch()) == true ) {
							
							int tryCounter = 0;
							
							List<Batch> batchs = new ArrayList<Batch>();
							
							practicalObj.getBatches().keySet().forEach((b)->{
								batchs.add(b);
							});;
							
							Practical[] practicalList = timeTableAlgorithmService.practicals[stream][standard];
							
							HashMap<Practical, Batch> practicalBatchWise = new HashMap<Practical, Batch>();
							
							
							practicalBatchWise = 
									setTheBatcheAndPracticalForFirstTime(
											practicalObj, stream, standard, day, lecture, practicalBatchWise, batchs, practicalList );
							
							if(practicalBatchWise.size()==0) continue;
							
							practicalBatchWise = 
									setTheBatchesAndPracticalsAfterFirstTime(
											practicalObj, stream, standard, day, lecture, practicalBatchWise, batchs, practicalList);
							
							
							while(practicalBatchWise.size()!=batchs.size() && tryCounter==0 && practicalList.length==batchs.size()) {
								
								
								tryCounter++;
								
								//since size of the batch and practical doesn't match because of special condition
								//reset all the value that has been changed
								if(practicalBatchWise.size()!=batchs.size()) {
									
									for (Practical practical : practicalBatchWise.keySet()) {
								
										for (int i = 0; i < practicalList.length; i++) {
											if(practicalList[i].getId()==practical.getId()) {
												practicalList[i].getBatches().put(practicalBatchWise.get(practical), false);
											}
										}
									}
									
								}
								
								
								practicalBatchWise = 
										setTheBatcheAndPracticalForFirstTime(
												practicalObj, stream, standard, day, lecture, practicalBatchWise, batchs, practicalList );
								
								if(practicalBatchWise.size()==0) continue;
								
								practicalBatchWise = 
										setTheBatchesAndPracticalsAfterFirstTime(
												practicalObj, stream, standard, day, lecture, practicalBatchWise, batchs, practicalList);
								
								
							}
							
							
							for (int noOfLec = 0; noOfLec < practicalObj.getSingleSession(); noOfLec++) {
								
								Lecture newLecture = lectures[day][stream][standard][lecture+noOfLec];
								newLecture.setIsBatchPractical(true);
								newLecture.setPracticalBatchWise(practicalBatchWise);
								lectures[day][stream][standard][lecture+noOfLec] = newLecture;
							}
						
							return true;
							
						}else {
							
							if(isFacultyFree(day, stream, standard, lecture, practicalObj.getFaculty().getId()) && 
									isLabFree(day, stream, standard, lecture, practicalObj.getLab()) &&
									!isPracticalSessionOver(stream, standard, practicalObj)) {
								
								for (int noOfLec = 0; noOfLec < practicalObj.getSingleSession(); noOfLec++) {
									Lecture newLecture = lectures[day][stream][standard][lecture+noOfLec] ;
									newLecture.setPractical(practicalObj);
									lectures[day][stream][standard][lecture+noOfLec] = newLecture;
								}
								
								decreaseTheSessionOfPractical(stream, standard, practicalObj);
								return true;
								
							}
							
						}
						
						
					}
				}
			}
		}
		
		return false;
		
	}

	private HashMap<Practical, Batch> setTheBatchesAndPracticalsAfterFirstTime(Practical practicalObj, int stream,
			int standard, int day, int lecture, HashMap<Practical, Batch> practicalBatchWise, List<Batch> batchs, Practical[] practicalList) {
		
		
		
		for (int i = 0; i < practicalList.length ; i++) {
			practicalList[i].setBatches( sortBatchsStartFromTrue(practicalList[i].getBatches()));
			
			if( (practicalObj.getId()!=practicalList[i].getId())) {
				for (int j = 0; j < batchs.size() ; j++) {
					
					if(containsTheBatch(practicalBatchWise, batchs.get(j)))	continue;
					
					if( (practicalList[i].getBatches().get(batchs.get(j)) )==false) {
						/*
						Batch newBatch = batchs.get(j);
						
						for (Practical p : practicalBatchWise.keySet()) {
							Batch usedBatch = practicalBatchWise.get(p);
							if( (usedBatch.getId() == newBatch.getId()) ){
								
								batchRepeated = 1;
							}
						}
						
						if(batchRepeated==1)
							continue;
						*/
						if(isFacultyFree(day, stream, standard, lecture, practicalList[i].getFaculty().getId()) && 
								isLabFree(day, stream, standard, lecture, practicalList[i].getLab()) &&
									!isPracticalSessionOver(stream, standard, practicalList[i])) {
							
							practicalBatchWise.put(practicalList[i], batchs.get(j));
							practicalList[i].getBatches().put(batchs.get(j), true);
							
							break;
						}
					}
				}
			}
			
		}
		
		return practicalBatchWise;
	}

	private HashMap<Practical, Batch> setTheBatcheAndPracticalForFirstTime(Practical practicalObj,
												int stream,
													int standard, 
														int day, 
															int lecture, 
																HashMap<Practical, Batch> practicalBatchWise, List<Batch> batchs, Practical[] practicalList) {
		
		if(practicalBatchWise.size()==0) {
			
			for (int i = 0; i <practicalList.length  ; i++) {
				practicalList[i].setBatches( sortBatchsStartFromTrue(practicalList[i].getBatches()));
				if(practicalObj.getId()==practicalList[i].getId()) {
					for (int j = 0; j < batchs.size(); j++) {
						
						if( (practicalList[i].getBatches().get(batchs.get(j)))==false ) {
						
							if(isFacultyFree(day, stream, standard, lecture, practicalList[i].getFaculty().getId()) && 
									isLabFree(day, stream, standard, lecture, practicalList[i].getLab()) &&
										!isPracticalSessionOver(stream, standard,practicalList[i])) {

								practicalBatchWise.put(practicalList[i], batchs.get(j));
								practicalList[i].getBatches().put(batchs.get(j), true);
								
								break;
							}
						}
					}	
				}
			}
			
		}
		else {
			//System.out.println("Entered");
			for (int i = 0; i <practicalList.length  ; i++) {
				practicalList[i].setBatches( sortBatchsStartFromTrue(practicalList[i].getBatches()));
				if(practicalObj.getId()==practicalList[i].getId()) {
					for (int j = 0; j < batchs.size(); j++) {
						
						if( (practicalList[i].getBatches().get(batchs.get(j)))==false ) {
						
							if(isFacultyFree(day, stream, standard, lecture, practicalList[i].getFaculty().getId()) && 
									isLabFree(day, stream, standard, lecture, practicalList[i].getLab()) &&
										!isPracticalSessionOver(stream, standard,practicalList[i]) 
											) {
								
									if(practicalBatchWise.get(practicalList[i]).getId()==batchs.get(j).getId()) {
										//System.out.println("Encountered");
										continue;

									}
																		
									practicalBatchWise.put(practicalList[i], batchs.get(j));
									practicalList[i].getBatches().put(batchs.get(j), true);
									
									break;
								
								
							}
						}  
					}	
				}
			}
			
		}
		
		return practicalBatchWise;
		
	}
	
	private boolean checkIfPracticalIsAlreadyTakenInPrevious(Practical practicalObj) {
		
		return practicalObj.getBatches().containsValue(true);
	}

	private boolean checkIfPracticalsAreAlreadyTakenOnSameDay(int day, int stream, int standard) {
		
		for (int lecture = 0; lecture < lectures[day][stream][standard].length; lecture++) {
		
			if(lectures[day][stream][standard][lecture].getPracticalBatchWise()!=null) {
				return true;
			}
			
		}
		
		return false;
		
	}

	private HashMap<Batch, Boolean> sortBatchsStartFromTrue(HashMap<Batch, Boolean> batches) {
		
		HashMap<Batch, Boolean> b = new HashMap<Batch, Boolean>();
		
		for (Batch batch : batches.keySet()) {
			if(!batches.get(batch))
				b.put(batch, batches.get(batch));
		}
		
		for (Batch batch : batches.keySet()) {
			if(batches.get(batch))
				b.put(batch, batches.get(batch));
		}
		
		return batches;
		
	}

	private boolean containsTheBatch(HashMap<Practical, Batch> practicalBatchWise, Batch batch) {
		
		for (Practical practical : practicalBatchWise.keySet()) {
			if(practicalBatchWise.get(practical).getId() == batch.getId())
				return true;
		}
		
		return false;
	}

	private void decreaseTheSessionOfPractical(int stream, int standard, Practical practical) {
		for (int i = 0; i < timeTableAlgorithmService.practicals[stream][standard].length; i++) {
			if(timeTableAlgorithmService.practicals[stream][standard][i].getId()==practical.getId()) {
				if(timeTableAlgorithmService.practicals[stream][standard][i].getSessionHours()<=0) {
					timeTableAlgorithmService.practicals[stream][standard][i].setSessionHours(
															timeTableAlgorithmService.practicals[stream][standard][i].getSessionHours() - 
																timeTableAlgorithmService.practicals[stream][standard][i].getSingleSession()
														);
				}
					
			}
		}
	}

	private boolean isPracticalSessionOver(int stream, int standard, Practical practical) {
		
		int countIfPracticalForAllBatchesAreOver= 0; 
		
		if(practical.getAccordingToBatch()) {
			for (Batch batch : practical.getBatches().keySet()) {
				if(practical.getBatches().get(batch)) {
					countIfPracticalForAllBatchesAreOver++;
				}
			}
			if(countIfPracticalForAllBatchesAreOver==practical.getBatches().size()) {
				return true;
			}
		
		}else {
			
			for (int i = 0; i < timeTableAlgorithmService.practicals[stream][standard].length; i++) {
				if(timeTableAlgorithmService.practicals[stream][standard][i].getId()==practical.getId()) {
					if(timeTableAlgorithmService.practicals[stream][standard][i].getSessionHours()<=0)
						return true;
				}
			}
		}
	
		
		return false;
	}   

	private boolean isLabFree(int day, int streamIndex, int standardIndex, int lectureStart, String lab) {
	
		boolean b = true;
		
		for (int lecture = lectureStart; lecture <= lectureStart+1; lecture++) {
		
			for (int stream = 0; stream < lectures[day].length; stream++) {
				for (int standard = 0; standard < lectures[day][stream].length; standard++) {
					
					
					Lecture templec = lectures[day][stream][standard][lecture];
					
					
					if(templec.getIsSubject())
						if( templec.getSubject().getRoomNo().equalsIgnoreCase(lab)) {
							
							b= false;
						}
					if(templec.getPractical()!=null) {
						if(templec.getPractical().getLab().equalsIgnoreCase(lab)) 
							b= false;
					}
					if( (templec.getIsBatchPractical())==true) {
						
						for (Practical practical : templec.getPracticalBatchWise().keySet()) {
							if(practical.getLab().equalsIgnoreCase(lab))
								b = false;
						
						}
					}
					
				}
			}
		
		}
		
		return b;
	}

	private boolean isFacultyFree(int day, int streamIndex, int standardIndex, int lectureStart, Long id) {
		
		boolean b = true;
		
		for (int lecture = lectureStart; lecture <= lectureStart+1; lecture++) {
			
			for (int stream = 0; stream < lectures[day].length; stream++) {
				for (int standard = 0; standard < lectures[day][stream].length; standard++) {
					//System.out.println(lectures[day][stream][standard][lecture]);
					Lecture templec = lectures[day][stream][standard][lecture];
					
					if(templec.getIsSubject()==true)
						if( templec.getSubject().getFaculty().getId()==id) {
							b= false;
						}
					else if(templec.getPractical()!=null) {
						if(templec.getPractical().getFaculty().getId()==id) b= false;
					}
					else if(templec.getIsBatchPractical()) {
	
						for (Practical practical : templec.getPracticalBatchWise().keySet()) {
							if(practical.getFaculty().getId()==id)
								b = false;
						}
					}
					
				}
			}
		
		}
		
		return b;
	}

	private void printTheTable() {
		Scanner sc = new Scanner(System.in);
		
		
		int read = 0;
		
		while(read!=1) {

			System.out.println("enter stream and standard");
			
			int streamIndex = Integer.parseInt( sc.next() );
			int standardIndex = Integer.parseInt( sc.next() );
			
			
			System.out.print("Lecture Nos: ");
			
			for (int day = 0; day < days.length; day++) {
				System.out.print("\t\t\t\t\t"+days[day]);
			}
			System.out.println("");
			//timeTableAlgorithmService.streams.length
			//timeTableAlgorithmService.streamStandards[stream].length
			for (int stream = streamIndex; stream < streamIndex+1; stream++) {
				for (int standard = standardIndex; standard < standardIndex+1; standard++) {
					for (int lecture = 0; lecture < 7; lecture++) {
						System.out.print("Lecture No: "+(lecture+1));
						for (int day = 0; day < lectures.length; day++) {
							if(lectures[day][stream][standard][lecture].getSubject()!=null)
								System.out.print("\t\t\t|"+lectures[day][stream][standard][lecture].getSubject().getName()+"--"+lectures[day][stream][standard][lecture].getSubject().getPreferredSession());
							else if(lectures[day][stream][standard][lecture].getPractical()!=null)
								System.out.print("\t\t\t|"+lectures[day][stream][standard][lecture].getPractical().getName()+"--"+lectures[day][stream][standard][lecture].getPractical().getPreferredSession());
							else if(lectures[day][stream][standard][lecture].getIsBatchPractical()) {
								System.out.print("\t|");
								for (Practical p : lectures[day][stream][standard][lecture].getPracticalBatchWise().keySet()) {
								
									
									System.out.print("Day: "+days[day]+"--"+p.getName()+"--"+lectures[day][stream][standard][lecture].getPracticalBatchWise().get(p).getName());
									
								}
							}
							else
								System.out.print("\t\t\t\t\t|null");
						}
						System.out.println("");
					}
				}
			}
			
			
			for (int i = 0; i < timeTableAlgorithmService.practicals[streamIndex][standardIndex].length; i++) {
				System.out.println(timeTableAlgorithmService.practicals[streamIndex][standardIndex][i].getBatches());
				}
			
			System.out.println("enter 1 to end");
			read = Integer.parseInt( sc.next() );
			
			
		}
		
		
		
	}

	private Boolean checkIfSelectedSubjectIsNotRepeatedTwiceInADay(int day, int stream, int standard, int lecture, Subject selectedSubject) {
		
		int countLec = 0;
		
		for (int prevLectures = lecture-1; prevLectures >= 0 ; prevLectures--) {
			if(lectures[day][stream][standard][prevLectures].getSubject()!=null)
				if(lectures[day][stream][standard][prevLectures].getSubject().getId()==selectedSubject.getId()) 
					countLec++;
		}
		
		if(countLec>=2)
			return true;
		
		return false;
	}

	private Subject selectTheSubject(List<Subject> subjectList, Subject next) {
		if(next==null) {
			for (Subject subject : subjectList) {
				if(subject.getSessionHours()>0) 
					return subject;
			}
		}	
		else if(next!=null){
			for (Subject subject1 : subjectList) {
				if(subject1.getSessionHours()>0) {
					if(subject1.getId()!=next.getId()) {
						//System.out.println("*******");
						return subject1;
					}	
				}
			}
		}
	
		//System.out.println("###########");
//		for (Subject subject1 : subjectList) {
//			System.out.println(subject1.getSessionHours());
//		}
		
		return null;
	}

	private Boolean checkIfAllSubjectsAreFinished(List<Subject> subjectList) {
		int count = 0;
		for (Subject subject : subjectList) {
			if(subject.getSessionHours()<=0) {
				count++;
			}
		}
		if(count==subjectList.size()) return true;
		
		return false;
			
	}

	private Boolean decreaseTheSessionByAnHour(int stream, int standard, Subject selectedSubject, Double oneLectureHrs) {
		
		for (int i = 0; i < timeTableAlgorithmService.subjects[stream][standard].length; i++) {
			//System.out.println(timeTableAlgorithmService.subjects[stream][standard][i]);
			//System.out.println(selectedSubject);
			if(timeTableAlgorithmService.subjects[stream][standard][i].getId()==selectedSubject.getId()) {
				timeTableAlgorithmService.subjects[stream][standard][i].setSessionHours(
						timeTableAlgorithmService.subjects[stream][standard][i].getSessionHours() - oneLectureHrs
					);
				if(timeTableAlgorithmService.subjects[stream][standard][i].getSessionHours()<=0) {
					
					return true;
					
				}
				break;
			}
		}
		
		return false;
	}

	private List<Faculty> removeFacultyIfBusyFrom_subjectList_facultyTeachesSubjectList(List<Subject> subjectList,
			List<Faculty> facultyTeachesSubjectList, List<Faculty> tempFacultyToRemove) {
		
		tempFacultyToRemove.forEach((removeFaculty)->{
			if( facultyTeachesSubjectList.contains(removeFaculty) ) {
				
				Faculty tempFaculty = facultyTeachesSubjectList.get( 
											facultyTeachesSubjectList.indexOf(removeFaculty)
										);
				int subjectIndex = -1;
				for (Subject subject : subjectList) {
					if (subject.getFaculty().getId() == tempFaculty.getId()) {
						subjectIndex = subjectList.indexOf(subject);
						break;
					}
				}
				if(subjectIndex!=-1)
					subjectList.remove(subjectIndex);
				
				facultyTeachesSubjectList.remove(tempFaculty);
			}
		});
		
		return facultyTeachesSubjectList;
	}

	public List<Faculty> setTempFacultyToRemove(int day, int lecture){
		List<Faculty> tempFacultyToRemove = new ArrayList<Faculty>();
		for (int diffStream = 0; diffStream < lectures[day].length; diffStream++) {
			for (int diffStanardInStream = 0; diffStanardInStream < lectures[day][diffStream].length; diffStanardInStream++) {
			
				if(lectures[day][diffStream][diffStanardInStream][lecture].getFaculty()!=null) {
					tempFacultyToRemove.add( lectures[day][diffStream][diffStanardInStream][lecture].getFaculty());
				}
			}
		}
		return tempFacultyToRemove;
	}
	
	public List<Faculty> setFacultyTeachesSubjectList(int stream, int standard, List<Subject> subjectList){
		
		List<Faculty> faculties = new ArrayList<Faculty>();
		
		for (int i = 0; i < timeTableAlgorithmService.faculties[stream][standard].length; i++) {
			faculties.add(timeTableAlgorithmService.faculties[stream][standard][i]);
		}
		
		return faculties;
	}
	
	public List<Subject> setSubjectListForStreamStandard(int stream, int standard, String prioritySession) {
		List<Subject> subjectList = new ArrayList<Subject>();
		Subject[] subjectArr = new Subject[timeTableAlgorithmService.subjects[stream][standard].length];
		for (int i = 0; i < timeTableAlgorithmService.subjects[stream][standard].length; i++) {
			subjectArr[i] = timeTableAlgorithmService.subjects[stream][standard][i];
		}
		for (int i = 0; i < subjectArr.length; i++) {
			subjectList.add( subjectArr[i] );
		}
		Collections.sort(subjectList, Comparator.comparing(Subject:: getSessionHours).reversed());
		int j = 0;
		for (Subject subject : subjectArr) {
			subjectArr[j] = subject;
			j++;
		}
		subjectArr = new Subject().sortAccordingToPreferredSession(subjectArr, prioritySession);
		subjectList = new ArrayList<Subject>();
		for (int i = 0; i < subjectArr.length; i++) {
			subjectList.add( subjectArr[i] );
		}
		
		return subjectList;
	}

	public Lecture[][][][] generateTimeTableForCollege(Long collegeId) {
		

		timeTableAlgorithmService.getStreamStandard(collegeId);

		final Integer TOTAL_NO_OF_STREAMS = timeTableAlgorithmService.streams.length;

		/*
		 * Days
		 * |
		 * --streams (in a single day there are n no of streams)
		 * 	 |
		 * 	 --courses(stream_years) (in a single stream there are m no of courses/standard/years)
		 * 	   |
		 * 	   --lectures (in a single courses/standard/years there are k no of lectures taken )
		 * 
		 */
		lectures= new Lecture[TOTAL_NO_OF_DAYS][TOTAL_NO_OF_STREAMS][][];

		for (int i = 0; i < TOTAL_NO_OF_DAYS; i++) {
			for (int j = 0; j < timeTableAlgorithmService.streams.length; j++) {
				lectures[i][j] = new Lecture[timeTableAlgorithmService.streamStandards[i].length][TOTAL_NO_OF_LECTURES_IN_DAY];
			}
		}
		
		setUpPreLectureDetails();
		
		timeTableAlgorithmService.getStreamStandardPracticalArray(new Long("188"));

		addPracticalToTimeTable();
	
		//from the below program we will start add Subejcts to the timeTable
		timeTableAlgorithmService.getStreamStandardSubjectArray(new Long("188"));
		
		int countTheCall = 0;
		
		for (int day = 0; day < TOTAL_NO_OF_DAYS; day++) {
			
			for (int lecture = 0; lecture < TOTAL_NO_OF_LECTURES_IN_DAY; lecture++) {
				
				for (int stream = 0; stream < lectures[day].length; stream++) {
					
					for (int standard = 0; standard < lectures[day][stream].length; standard++) {
			
						Lecture setUplecture = lectures[day][stream][standard][lecture];
						
						if(setUplecture.getPractical()!=null || setUplecture.getPracticalBatchWise()!=null)	continue;
						
						List<Subject> subjectList = setSubjectListForStreamStandard(stream, standard, setUplecture.getLectureSession());
						List<Faculty> facultyTeachesSubjectList = setFacultyTeachesSubjectList(stream, standard, subjectList);				
						List<Faculty> tempFacultyToRemove = setTempFacultyToRemove(day, lecture);
			 			
						facultyTeachesSubjectList = removeFacultyIfBusyFrom_subjectList_facultyTeachesSubjectList(subjectList, facultyTeachesSubjectList, tempFacultyToRemove);
						
						
							if(subjectList.size()==0)	continue;
							
						
							if(checkIfAllSubjectsAreFinished(subjectList))	continue;
						
							Subject selectedSubject = selectTheSubject(subjectList, null);
							
							if( checkIfSelectedSubjectIsNotRepeatedTwiceInADay(day, stream, standard, lecture, selectedSubject) ) {
								
								if(!subjectListHaveOnlyOneSubjectLeft(subjectList)) {
									
									selectedSubject = selectTheSubject(subjectList, selectedSubject);
									if(selectedSubject==null)
										continue;
									countTheCall++;	
									
								}else {
									
									if(checkIfSelectSubjectIsRepeatedConsecutively(day, stream, standard, lecture, selectedSubject)) {
										
										selectedSubject = selectTheSubject(subjectList, selectedSubject);
										if(selectedSubject==null)
											continue;
										countTheCall++;	
										
									}
								}
								
							}
							
							decreaseTheSessionByAnHour(stream, standard, selectedSubject, oneLectureHrs);
		
							setUplecture.setIsSubject(true);
							setUplecture.setSubject( new Subject(selectedSubject) );
							setUplecture.setFaculty( new Faculty(selectedSubject.getFaculty()) );
		
							lectures[day][stream][standard][lecture] = setUplecture;					  
					}
				}
			}
		}
	
		return lectures;
	}
	
}
