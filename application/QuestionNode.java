package application;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title: (QuestionNode.java)
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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class QuestionNode {

	// fields
	VBox nodes;
	VBox buttons;
	ToggleGroup choices;

	// constructor
	public QuestionNode(VBox nodes, ToggleGroup choices, VBox buttons) {
		// initializes instance variables
		this.nodes = nodes;
		this.buttons = buttons;
		this.choices = choices;
	}

	/**
	 * returns the choices
	 * @return choices
	 */
	public ToggleGroup getChoices() {
		return this.choices;
	}


	/**
	 * creates a formatted representation of question
	 * 
	 * @return VBox - the formatted question
	 */
	public VBox getFormatted() {
		VBox formatted = new VBox(10);
		// does some formatting
		formatted.getChildren().add(nodes);
		formatted.getChildren().add(buttons);
		formatted.setAlignment(Pos.CENTER);
		formatted.setPadding(new Insets(0, 10, 0, 10));
		return formatted;
	}

	public void setStyle(boolean correct) {
		if (correct) { // CASE 1: if correct
			// add correct in green to the screen 
			Label correctLabel = new Label("Correct!! :)");
			correctLabel.setTextFill(Color.web("#00ff00"));
			nodes.getChildren().add(correctLabel);
		} else { // CASE 2: if not correct
			// add wrong in red to the screen
			Label wrong = new Label("Wrong!! :(");
			wrong.setTextFill(Color.web("#ff0000"));
			nodes.getChildren().add(wrong);
		}
	}
}
