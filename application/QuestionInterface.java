package application;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title: (QuestionInterface.java)
//Files: .java
//Semester: CS 400
//Author: (Ateam14)
//Email: (ydong65@wisc.edu)
//CS Login: (Katy)
//Lecturer's Name: (Deb Deppeler)
//Lecture Section: 001
//Due Date: 03/14/2019
//Known bug:
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully
//acknowledge and credit those sources of help here. Instructors and TAs do
//not need to be credited here, but tutors, friends, relatives, room mates
//strangers, etc do. If you received no outside help from either type of
//source, then please explicitly indicate NONE.
//
//Persons: (identify each person and describe their help in detail)
//Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/*
 * Defines the public methods within Question
 */
public interface QuestionInterface {
	/**
	 * Checks if an answer is correct
	 * 
	 * @param answer
	 * @return true if correct, false otherwise
	 */
	Boolean testAnswer(String answer);

	/**
	 * getter for the question
	 * 
	 * @return the question
	 */
	String getQuestion();

	/**
	 * getter for the topic of the question
	 * 
	 * @return the topic
	 */
	String getTopic();

	/**
	 * getter for the image of the question
	 * 
	 * @return the image, or null if there is no image
	 */
	String getImage();

	/**
	 * getter for the meta data of the question
	 * 
	 * @return metadata, or null if there isn't any
	 */
	String getMeta();
}
