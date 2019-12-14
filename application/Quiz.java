package application;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////

//
//Title: (Quiz.java)
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
 * Runs a quiz, showing one question at a time. After each question, it will tell you 
 * whether you are right or not. At the end of the quiz, it will display your results.
 */

public class Quiz {

	private ArrayList<QuestionNode> questionNodes; // graphic representations of the List of Questions
	private ArrayList<Question> questionsOfTopic; // List of Questions
	private int counter; // keeps track of the current question
	private double numQ; // total number of questions
	private double numCorrect; // the number of questions the user has answered correctly
	private int numAnswered; // the number of questions the user has answered in total
	private Stage newWindow = new Stage(); // this is the quiz window

	/**
	 * Constructs the Quiz class
	 * 
	 * @param questionNodes
	 *            - graphic representations of the List of Questions
	 * @param questionsOfTopic
	 *            - List of Questions
	 * @param primaryStage
	 */
	public Quiz(ArrayList<QuestionNode> questionNodes, ArrayList<Question> questionsOfTopic, Stage primaryStage) {
		// initializes instance variables
		this.questionNodes = questionNodes;
		this.questionsOfTopic = questionsOfTopic;
		counter = 0;
		numQ = questionsOfTopic.size();

		// Window settings
		newWindow.setTitle("Start Quiz");

		// Specifies the owner Window (parent) for new window
		newWindow.initOwner(primaryStage);

		// Set position of second window, related to primary window.
		newWindow.setX(primaryStage.getX() + 200);
		newWindow.setY(primaryStage.getY() + 100);
	}

	/**
	 * Recursively displays the next question
	 */
	public void displayNextQuestions() {
		// just displays what number question you are on
		Label questionNum = new Label("Question " + (counter + 1) + ":");
		// questionNum formatting
		questionNum.setTextFill(Color.web("#fff0f5"));
		questionNum.setPadding(new Insets(20, 0, 0, 0));

		// Creates button to move to next question
		Button next = new Button("Next");
		// sets up event for when next is clicked
		next.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (counter < numQ) // if last question is not reached, go to next questions
					displayNextQuestions();
				else // is last question is reached, display results
					results();
			}
		});

		// creates button to check your answer
		Button check = new Button("Submit Quiz");
		// wraps either next or check
		VBox buttonWrapper = new VBox(10, check);
		// formats the buttonWrapper class
		buttonWrapper.setPadding(new Insets(20, 0, 20, 0));
		buttonWrapper.setAlignment(Pos.CENTER);
		// sets even on check click
		check.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// checks the answer
				checkQuestion();
				counter++;
				// changes the button to next instead of check
				buttonWrapper.getChildren().add(next);
				buttonWrapper.getChildren().remove(check);
			}
		});

		// adds the next question to the layout
		VBox questionLayout = new VBox(10);
		questionLayout.getChildren().add(questionNum);
		questionLayout.getChildren().add(questionNodes.get(counter).getFormatted());
		questionLayout.getChildren().add(buttonWrapper);
		questionLayout.setAlignment(Pos.CENTER);

		// displays the question
		showWindow(questionLayout);
	}

	/**
	 * resets the quiz window with the given parameter
	 * 
	 * @param questionLayout
	 *            - the new question to appear
	 */
	public void showWindow(VBox questionLayout) {
		// Sets up layouts
		//ScrollPane layout = new ScrollPane(questionLayout);
		questionLayout.setStyle("-fx-background: #778899"); // sets background color
		Scene secondScene = new Scene(questionLayout, 660, 800);

		// New window (Stage)
		newWindow.setScene(secondScene);

		newWindow.show();
	}

	/**
	 * checks whether or not the users answer is correct for the current question
	 */
	public void checkQuestion() {
		try {
			// gets the selected radio button
			RadioButton selected = (RadioButton) questionNodes.get(counter).getChoices().getSelectedToggle();
			String answer = selected.getText(); // gets the string of the selected radio button
			// CASE 1: answer is correct
			if (questionsOfTopic.get(counter).testAnswer(answer)) {
				// run method to display that correct answer was given
				questionNodes.get(counter).setStyle(true);
				numCorrect++;
			} else // CASE 2: answer is wrong
					// run method to display that wrong answer was given
				questionNodes.get(counter).setStyle(false);
			numAnswered++;
		} catch (NullPointerException e) {
			// no answer given, therefore wrong
			questionNodes.get(counter).setStyle(false);
		}
	}

	/**
	 * calculates and displays the results of the quiz
	 */
	private void results() {
		VBox dialogVbox = new VBox(5); // creates main layout for results

		// formats the main VBox
		dialogVbox.setStyle("-fx-background-color: #778899");
		dialogVbox.setAlignment(Pos.CENTER);

		// creates the top header of the page
		Label result = new Label("Quiz Results:");
		result.setTextFill(Color.web("#fff0f5"));

		// Calculates percentage right
		double percentage = (double) ((int) (numCorrect / numQ * 100000)) / 1000;
		Label percentScore = new Label("Percent: " + percentage + "%");
		percentScore.setTextFill(Color.web("#fff0f5"));

		// Calculates letter grade
		String letter = "";
		if (percentage < 60)
			letter = "F";
		else if (percentage < 70)
			letter = "D";
		else if (percentage < 85)
			letter = "C";
		else if (percentage < 90)
			letter = "B";
		else
			letter = "A";
		Label grade = new Label("Letter Grade: " + letter);
		grade.setTextFill(Color.web("#fff0f5"));

		// calculates number of questions answered
		Label numberAnswered = new Label("Questions answered: " + numAnswered);
		numberAnswered.setTextFill(Color.web("#fff0f5"));

		// Adds all elements to the main layout manager and sets the scene
		dialogVbox.getChildren().addAll(result, numberAnswered, percentScore, grade);
		dialogVbox.setAlignment(Pos.CENTER);
		showWindow(dialogVbox);
	}
}
