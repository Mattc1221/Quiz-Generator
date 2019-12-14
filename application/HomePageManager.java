package application;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////

//
//Title: (HomePageManager.java)
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
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomePageManager {

	private Stage primaryStage; // the main stage
	private BorderPane root; // the layout manager for the main stage
	private MenuBar mb; // the menu bar across most view
	private Quiz quiz; // stores a quiz based on user input

	/**
	 * constructs HomePageManager
	 */
	public HomePageManager(Stage primaryStage, BorderPane root, MenuBar mb) {
		// initializes instance variables
		this.primaryStage = primaryStage;
		this.root = root;
		this.mb = mb;
	}

	/**
	 * loads the home page (default page)
	 * 
	 * @param questionList
	 *            - a list of all the questions uploaded to the application
	 */
	public void loadHomePage(List<Question> questionList) {
		// Creates and formats the label for topics
		Label topicLabel = new Label("      Topics: ");
		topicLabel.setTextFill(Color.web("#fff0f5"));
		ArrayList<String> names = new ArrayList<String>(); // used to store topic names.

		// fills names will all topic names
		for (int i = 0; i < questionList.size(); ++i)
			if (!names.contains(questionList.get(i).getTopic())) // stops duplicates
				names.add(questionList.get(i).getTopic());

		// sorts the topic names
		names.sort(String::compareToIgnoreCase); 

		// creates a combo box of the topic names
		ObservableList<String> options = FXCollections.observableArrayList(names);
		ComboBox<String> box = new ComboBox<String>(options);
		HBox hb = new HBox(10, topicLabel, box); // groups topic label and options together

		// creates and formats label for # of questions to be in quiz
		Label numQuestions = new Label("# Questions? ");
		numQuestions.setTextFill(Color.web("#fff0f5"));

		// Spinner to specify the number of questions
		final Spinner<Integer> numQuestionsSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);

		// formats spinner
		numQuestionsSpinner.setValueFactory(valueFactory);
		numQuestionsSpinner.setPrefWidth(60);

		// groups spinner and # questions label
		HBox hb2 = new HBox(10, numQuestions, numQuestionsSpinner);

		// formats the quiz settings
		VBox quizOptions = new VBox(10, hb2, hb);

		// sets up label for total number of questions
		Label totalQLabel = new Label("Total Questions: ");
		totalQLabel.setTextFill(Color.web("#fff0f5"));

		// update the size of questionList
		Label totalQ = new Label("" + questionList.size());
		totalQ.setTextFill(Color.web("#fff0f5"));

		// groups the total number of questions label and the number of questions
		// together
		HBox hb3 = new HBox(10, totalQLabel, totalQ);

		// adds padding
		hb.setPadding(new Insets(10, 10, 10, 10));

		// create a VBox
		VBox vb = new VBox(mb);

		// creates a drop shadow for the main label
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(5.0);
		dropShadow.setOffsetY(5.0);
		dropShadow.setColor(Color.web("#1f1f2e"));

		// creates the main label for the home page and formats it
		Label mainLabel = new Label("Quiz Generator");
		mainLabel.setStyle("-fx-font-size: 50");
		mainLabel.setTextFill(Color.web("#fff0f5"));
		mainLabel.setEffect(dropShadow);

		// Created Button
		Button startButton = new Button("Start Quiz");
		startButton.setStyle("");
		startButton.setPrefHeight(40);
		startButton.setPrefWidth(100);

		// click button will open a new window.
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// starts the quiz
				startQuiz(numQuestionsSpinner, box, questionList);
			}
		});

		// formatting for the top of the page
		BorderPane top = new BorderPane();
		top.setTop(vb);
		top.setLeft(quizOptions);
		top.setRight(hb3);

		// formats the root
		root.setPadding(new Insets(0, 0, 20, 0));
		root.setStyle("-fx-background-color: #778899");

		// groups everything together
		root.setTop(top);
		root.setCenter(mainLabel);
		root.setBottom(startButton);
		BorderPane.setAlignment(startButton, Pos.CENTER);
	}

	/**
	 * Starts the quiz if the user has entered enough data
	 * 
	 * @param numQuestionsSpinner
	 *            - spinner containing the number of questions to be in the quiz
	 * @param box
	 *            - combo box, with the topic selected
	 * @param questionList
	 *            - list of all questions
	 */
	private void startQuiz(Spinner<Integer> numQuestionsSpinner, ComboBox<String> box, List<Question> questionList) {
		try {
			// gets the number of questions and topic
			int numQ = numQuestionsSpinner.getValue();
			String topic = box.getValue().toString();

			// gets all questions of the desired topic
			ArrayList<Question> questionsOfTopic = new ArrayList<Question>();
			for (Question q : questionList)
				if (q.getTopic().equals(topic))
					questionsOfTopic.add(q);

			// Removes random nodes until the number of questions is appropriate
			while (numQ < questionsOfTopic.size()) {
				int index = (int) (Math.random() * questionsOfTopic.size() - 1);
				questionsOfTopic.remove(index);
			}

			// converts questions into javafx counterpart - questionNode
			ArrayList<QuestionNode> questionNodes = new ArrayList<QuestionNode>();
			for (Question q : questionsOfTopic) {
				VBox nodes; // the structure of the question
				ImageView selectedImage = new ImageView();

				try {
					// checks to see if image exists within question
					if (q.getImage() != null && !q.getImage().equals("none")) {
						Image image = new Image("application/" + q.getImage());
						selectedImage.setImage(image);
					}
				} catch (Exception e) {
					// image could not be found
				}

				// changes the question from one really long string to one with some breaks
				String longQ = q.getQuestion();
				String shortQ = "";
				while (longQ.length() >= 100) { // adds breaks every 100 characters
					shortQ += longQ.substring(0, 100) + System.lineSeparator();
					longQ = longQ.substring(100);
				}
				shortQ += longQ; // adds the last of the characters

				// creates and styles the question label
				Label question = new Label(shortQ);
				question.setTextFill(Color.web("#fff0f5"));

				// detects for image and formats appropriately
				if (selectedImage != null)
					nodes = new VBox(20, selectedImage, question);
				else
					nodes = new VBox(20, question);

				// contains all of the choices for the current topic
				ArrayList<String> choices = q.getOptions();

				// randomizes choices
				Collections.shuffle(choices);

				// VBox to format all of the choices
				VBox buttons = new VBox(10);

				// Create a ToggleGroup
				final ToggleGroup group = new ToggleGroup();

				// adds each choice to buttons and group
				for (String choice : choices) {
					RadioButton q1 = new RadioButton(choice);
					q1.setTextFill(Color.web("#fff0f5"));
					buttons.getChildren().add(q1);
					group.getToggles().add(q1);
				}

				// creates new QuestionNode
				questionNodes.add(new QuestionNode(nodes, group, buttons));
			}

			// creates a quiz and displays the first question
			quiz = new Quiz(questionNodes, questionsOfTopic, primaryStage);
			quiz.displayNextQuestions();
		} catch (Exception e) {
			// user entered something incorrectly,
		}
	}

}
