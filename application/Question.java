package application;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////

//
//Title: (Question.java)
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

import java.util.ArrayList;

/*
 * Contains fields that each question holds
 */
public class Question implements QuestionInterface {

	String question; // the question asked during the quiz
	String topic; // the topic of the question
	String image; // image path, may be null
	String metaData; // meta-data, may be null
	ArrayList<String> options; // the different answers
	ArrayList<Boolean> isCorrect; // corresponding by index to options, determine correctness

	/**
	 * constructs the question class
	 */
	public Question(String meta, String text, String topic, String image, ArrayList<String> choices,
			ArrayList<Boolean> JsonCorrectCheck) {
		// initializes instance variables
		this.metaData = meta;
		this.question = text;
		this.topic = topic;
		this.image = image;
		this.options = choices;
		this.isCorrect = JsonCorrectCheck;
	}

	/**
	 * checks whether the given answer is a correct answer.
	 * 
	 * @param answer
	 *            - the users answer
	 * @return true if correct, false otherwise
	 */
	@Override
	public Boolean testAnswer(String answer) {
		for (int i = 0; i < options.size(); i++) {
			// if the answer is at an index in options, and if that index is true in
			// isCorrect, return true
			if (options.get(i).equals(answer) && isCorrect.get(i).equals(true))
				return true;
		}
		// else return false
		return false;
	}

	/**
	 * gets the question
	 * 
	 * @return question
	 */
	@Override
	public String getQuestion() {
		// TODO Auto-generated method stub
		return question;
	}

	/**
	 * gets the topic
	 * 
	 * @return topic
	 */
	@Override
	public String getTopic() {
		// TODO Auto-generated method stub
		return topic;
	}

	/**
	 * gets the image
	 * 
	 * @return image
	 */
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	/**
	 * gets the meta data
	 * 
	 * @return metadata
	 */
	@Override
	public String getMeta() {
		// TODO Auto-generated method stub
		return metaData;
	}

	/**
	 * creates a pseudo-random order of the list of options
	 * 
	 * @return a randomized list of the options
	 */
	public ArrayList<String> getOptions() {
		ArrayList<String> randomizedOptions = new ArrayList<String>();

		// this gets a random index to "randomize" where the correct answer appears
		int index = (int) (Math.random() * options.size() - 1);

		// "randomizes" the correct answers position
		for (int i = index; i < options.size(); i++)
			randomizedOptions.add(options.get(i));
		for (int i = 0; i < index; i++)
			randomizedOptions.add(options.get(i));

		return randomizedOptions;
	}

	/**
	 * returns the non-randomized options list
	 * 
	 * @return options
	 */
	public ArrayList<String> getNonRandomizedOptions() {
		return options;
	}

	/**
	 * returns a string array corresponding to the boolean array is correct
	 * 
	 * @return correctString containing T or F at each index
	 */
	public ArrayList<String> getIsCorrect() {
		ArrayList<String> correctString = new ArrayList<String>();
		for (int i = 0; i < isCorrect.size(); i++)
			if (isCorrect.get(i))
				correctString.add("T");
			else
				correctString.add("F");
		return correctString;
	}
}
