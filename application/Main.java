package application;
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////

//
// Title: (Main.java)
// Files: .java
// Semester: CS 400
// Author: (Ateam14)
// Email: (ydong65@wisc.edu)
// CS Login: (Katy)
// Lecturer's Name: (Deb Deppeler)
// Lecture Section: 001
// Due Date: 03/14/2019
// Known bug:
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.awt.Desktop;
import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.*;

/**
 * The Main.java class executes GUI functions and takes in user input. It is composed of the pages
 * for user to add a single question, upload json question file, select question numbers and topic,
 * the button for generating quiz and the options of saving question list.
 * 
 * 
 * @author Ateam 14
 *
 */
/**
 * @author YDONG
 *
 */
public class Main extends Application {
	// for file chooser operation.
	//private Desktop desktop = Desktop.getDesktop();
	private File file; // used in saveQuestionFile method with the goal of saving question lists.

	private List<Question> questionList = new ArrayList<Question>(); // an array list used as a
																		// database for storing all the
																		// questions entered by the user.
	private HomePageManager homePageManager; // the HomePageManager object used in the load homepage
												// method and
												// start quiz method.
	private ArrayList<Boolean> checkifCorrect = new ArrayList<>();
	// the arraylists responsible for checking if the answer the user chooses is
	// correct.

	/**
	 * Constructor of Main.java class
	 */
	public Main() {
		// Initially set the first choice enter by the user is correct.
		for (int i = 0; i < 4; ++i) {
			if (i == 0) {
				this.checkifCorrect.add(true);
			}
			this.checkifCorrect.add(false);
		}
	}

	@Override
	public void start(Stage primaryStage) {
		// if the user clicks "X" for closing the entire program, a window will pop up.
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				popUp();
			}
		});

		// create a menu with 3 options, homepage, Upload json file, and add a question
		// page.
		Label m1 = new Label("Home");
		Menu menu1 = new Menu("", m1);
		Label m2 = new Label("Upload Question File");
		Menu menu2 = new Menu("", m2);
		Label m3 = new Label("Add A Question");
		Menu menu3 = new Menu("", m3);
		Label m4 = new Label("Help");
		Menu menu4 = new Menu("", m4);

		// create a menu bar
		MenuBar mb = new MenuBar();

		// add menu to menu bar
		mb.getMenus().add(menu1);
		mb.getMenus().add(menu2);
		mb.getMenus().add(menu3);
		mb.getMenus().add(menu4);

		BorderPane root = new BorderPane();
		try {
			// creates the main scene and sets title
			primaryStage.setTitle("Ateam14 - Quiz Generator");
			Scene scene = new Scene(root, 800, 600);
			// loads home page as default page
			homePageManager = new HomePageManager(primaryStage, root, mb);
			homePageManager.loadHomePage(questionList);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// sets menu events
		m1.setOnMouseClicked(mouseEvent -> {
			// if home is clicked, load homepage.
			homePageManager.loadHomePage(questionList);
		});
		m2.setOnMouseClicked(mouseEvent -> {
			// if upload a json file is clicked, open a window for uplaoding the file.
			addQuestionPage(primaryStage, root, mb);
		});
		m3.setOnMouseClicked(mouseEvent -> {
			// if add a question is clicked, open a window for enter required fields
			// manually.

			EnterAQuestionPage(primaryStage, root, mb);
		});
		m4.setOnMouseClicked(mouseEvent -> help(primaryStage)); 
			// if help is clicked, open help.txt that has some explanations of the
			// application.
	}

	/**
	 * The method is used for uploading a json file.
	 * 
	 * @param primaryStage-the
	 *            stage is shown primarily.
	 * @param root
	 *            -the root of application
	 * @param mb
	 *            -menu bar.
	 */
	private void addQuestionPage(Stage primaryStage, BorderPane root, MenuBar mb) {
		// main layout
		VBox layout = new VBox(20);
		HBox labelFormatter = new HBox();
		labelFormatter.setAlignment(Pos.CENTER);

		// Handle the situation if the json file upload is failed.
		Label jsonLoadFailure = new Label("failed to retrieve file path");
		jsonLoadFailure.setTextFill(Color.web("#ff0000"));
		// print message to indicate the file is found.
		Label jsonLoadSuccess = new Label("File path found!");
		jsonLoadSuccess.setTextFill(Color.web("#fff0f5"));

		// file chooser,
		Button button = new Button("Upload JSON");// creating abutton for uploading.

		LoadquestionDataBase load = new LoadquestionDataBase();
		try {

			// create a File chooser
			FileChooser fil_chooser = new FileChooser();

			// create an Event Handler
			EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see javafx.event.EventHandler#handle(javafx.event.Event)
				 */
				public void handle(ActionEvent e) {
					// After showing the indication, remove these immediately.
					if (layout.getChildren().contains(labelFormatter))
						layout.getChildren().remove(labelFormatter);

					// get the file selected
					try {
						file = fil_chooser.showOpenDialog(primaryStage);
					} catch (Exception s) {
						file = null;
					}
					if (file != null && file.getAbsolutePath().contains(".json")) {
						// Check if the load being done successfully.
						if (labelFormatter.getChildren().contains(jsonLoadFailure))
							labelFormatter.getChildren().remove(jsonLoadFailure);// Ignore the failure case.
						labelFormatter.getChildren().add(jsonLoadSuccess);
						layout.getChildren().add(labelFormatter);

					} else {
						// Check the error message and print out some indication.
						if (labelFormatter.getChildren().contains(jsonLoadSuccess))
							labelFormatter.getChildren().remove(jsonLoadSuccess);
						labelFormatter.getChildren().add(jsonLoadFailure);
						layout.getChildren().add(labelFormatter);
					}
				}
			};
			// Enable the button, if upload json file is clicked, go to event handler.
			button.setOnAction(event);

		} catch (Exception e) {

		}
		// create a new button for sumbiting.
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// call LoadquestionDataBase class for parsing the json file and create many
				try {
					questionList.addAll(load.constructGraph(file.getAbsolutePath()));
				} catch (Exception e) {
					// Nothing should happen, they did not enter file path correctly
				}
				file = null;
				// after submit is clicked, go back to the home page.
				homePageManager.loadHomePage(questionList);
			}
		});
		// creates horizontal box for submit button.
		HBox buttons = new HBox(20, button, submitButton);
		layout.getChildren().add(buttons);
		// place these button at the buttom of stage.
		buttons.setAlignment(Pos.CENTER);
		layout.setAlignment(Pos.CENTER);
		root.setTop(mb);
		root.setCenter(layout);
		root.setBottom(null);

	}

	/**
	 * The methods for adding a single question.
	 * 
	 * @param primaryStage-the
	 *            stage is shown primarily.
	 * @param root
	 *            -the root of application
	 * @param mb
	 *            -menu bar.
	 */
	private void EnterAQuestionPage(Stage primaryStage, BorderPane root, MenuBar mb) {
		// add form
		// creating a string array that could all the necessary info entered by the
		// user.
		String[] questionInfo = new String[9];
		Button submitButton = new Button("Submit"); // a button for submitting the file, after the user
													// is finished
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean addQuestion = true;
				// heck if the question info the user entered contains empty fields.
				for (int i = 0; i < questionInfo.length; i++) {
					if (questionInfo[i] == null && i != 2 && i != 3)
						// if found an empty field, set the boolean to false.
						addQuestion = false;
					else if (i != 2 && i != 3 && questionInfo[i].equals(""))
						// if found an empty field, set the boolean to false.
						addQuestion = false;
				}
				if (addQuestion) {
					ArrayList<String> answers = new ArrayList<String>(Arrays.asList(questionInfo[4], questionInfo[5],
							questionInfo[6], questionInfo[7], questionInfo[8]));
					// call constructor to create a new question
					questionList.add(new Question(questionInfo[2], questionInfo[0], questionInfo[1], questionInfo[3],
							answers, checkifCorrect));
				}
				// call the method to reload the home page.
				homePageManager.loadHomePage(questionList);
			}
		});
		// reformat the menu bar and the button.
		root.setTop(mb);
		root.setCenter(addSingleQuestion(questionInfo));
		HBox bottom = new HBox(submitButton);
		bottom.setAlignment(Pos.CENTER);
		root.setBottom(bottom);
	}

	/**
	 * 
	 * Helper method for creating a adding question form.
	 * 
	 * @param questionInfo-
	 *            the string array contains all the info entered by the user.
	 * @return -return a horizontal box.
	 */
	private HBox addSingleQuestion(String[] questionInfo) {
		// creates labels and corresponding fields
		// creating the question text fields
		Label questionLabel = new Label("Question:");
		questionLabel.setTextFill(Color.web("#fff0f5"));
		TextField question = new TextField("Enter Question Here");
		question.textProperty().addListener((observable, oldValue, newValue) -> questionInfo[0] = question.getText());
		question.setPrefWidth(400);

		// creating the topic text fields
		Label topicLabel = new Label("Topic:");
		topicLabel.setTextFill(Color.web("#fff0f5"));
		TextField topic = new TextField("Enter Topic");
		topic.textProperty().addListener((observable, oldValue, newValue) -> questionInfo[1] = topic.getText());
		// creating the topic text fields
		Label metadataLabel = new Label("meta-data:");
		metadataLabel.setTextFill(Color.web("#fff0f5"));
		TextField metadata = new TextField("Enter meta-data");
		metadata.textProperty().addListener((observable, oldValue, newValue) -> {
			questionInfo[2] = metadata.getText();
		});
		// creating the imageLabel text fields
		Label imageLabel = new Label("Image:");
		imageLabel.setTextFill(Color.web("#fff0f5"));
		TextField image = new TextField("Enter image path");
		image.textProperty().addListener((observable, oldValue, newValue) -> {
			questionInfo[3] = image.getText();
		});
		// creating the answerLabel text fields
		Label answerLabel = new Label("Answers");
		answerLabel.setTextFill(Color.web("#fff0f5"));
		// The first answer should be the correct one.
		TextField correct = new TextField("Correct answer");
		correct.textProperty().addListener((observable, oldValue, newValue) -> {
			questionInfo[4] = correct.getText();
		});
		// The rest of answers should be the false.
		TextField wrong1 = new TextField("Wrong answer");
		wrong1.textProperty().addListener((observable, oldValue, newValue) -> {
			questionInfo[5] = wrong1.getText();
		});
		// The rest of answers should be the false.
		TextField wrong2 = new TextField("Wrong answer");
		wrong2.textProperty().addListener((observable, oldValue, newValue) -> {
			questionInfo[6] = wrong2.getText();
		});
		// The rest of answers should be the false.
		TextField wrong3 = new TextField("Wrong answer");
		wrong3.textProperty().addListener((observable, oldValue, newValue) -> {
			questionInfo[7] = wrong3.getText();
		});
		// The rest of answers should be the false.
		TextField wrong4 = new TextField("Wrong answer");
		wrong4.textProperty().addListener((observable, oldValue, newValue) -> {
			questionInfo[8] = wrong4.getText();
		});

		// formats labesl and text fields
		VBox fields = new VBox(10, question, topic, metadata, image, correct, wrong1, wrong2, wrong3, wrong4);
		VBox labels = new VBox(22, questionLabel, topicLabel, metadataLabel, imageLabel, answerLabel);
		// set the horizontal and vertical box for displaying the question form.
		HBox single = new HBox(50, labels, fields);
		single.setAlignment(Pos.CENTER);
		single.setPadding(new Insets(80, 0, 0, 0));
		return single;
	}

	/**
	 * the help method would display the help txt.
	 * 
	 * @param primaryStage-
	 *            the primary stage.
	 */
	private void help(Stage primaryStage) {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		// Create a vertical box for displaying the dialog.
		VBox dialogVbox = new VBox(5);
		ScrollPane layout = new ScrollPane(dialogVbox);
		// Display the help file for helping the users use the application.F
		File file = new File("application/Help.txt");
		List<String> text = read(file);
		for (String line : text) {
			dialogVbox.getChildren().add(new Text(line));
		}
		Scene dialogScene = new Scene(layout, 400, 300);
		dialog.setScene(dialogScene);
		// display the dialog.
		dialog.show();
	}

	/**
	 * The helper method for reading the txt file.
	 * 
	 * @param file
	 * @return
	 */
	private List<String> read(File file) {
		List<String> lines = new ArrayList<String>();
		String line;
		try {
			// enable the buffer reader.
			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
			// After finish, close buffer reader.
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	/**
	 * The method for saving the question list if the user proposed.
	 * 
	 * @param questionList
	 *            --contains all the questions upload by the user.
	 * @param filename
	 *            --the file name.
	 * @throws IOException
	 */
	public static void saveQuestionFile(List<Question> questionList, String filename) throws IOException {
		File file = new File("QuestionFile.json");

		// creates the file
		file.createNewFile();

		// creates a FileWriter Object
		FileWriter writer = new FileWriter(file);

		// the header.
		writer.write("{\r\n" + "    \"questionArray\": \r\n" + "    [");
		// Writes the content to the file
		for (int i = 0; i < questionList.size(); ++i) {
			// Formating the json file. Printing the required format.
			if (i != 0)
				writer.write(",");

			writer.write("        {");

			writer.write(" \"meta-data\": \"" + questionList.get(i).getMeta() + "\",\n");
			writer.write("\"questionText\": \"" + questionList.get(i).getQuestion() + "\",\n");
			writer.write("\"topic\": \"" + questionList.get(i).getTopic() + "\",\n");
			if (questionList.get(i).getImage() == null)
				writer.write("\"image\": \"" + "none" + "\",\n");
			else
				writer.write("\"image\": \"" + questionList.get(i).getImage() + "\",\n");
			writer.write(" \"choiceArray\": \r\n" + "            [\r\n" + "                ");

			for (int j = 0; j < questionList.get(i).getIsCorrect().size(); j++) {
				writer.write("  {\"isCorrect\":\"" + questionList.get(i).getIsCorrect().get(j) + "\",\"choice\":\""
						+ questionList.get(i).getNonRandomizedOptions().get(j) + "\"},\r\n" + "                ");

			}
			writer.write("            ]\r\n" + "        }");
		}
		writer.write(" ] \r\n" + "}");
		writer.close();

	}

	/**
	 * The method for showing the pop up window.
	 */
	private void popUp() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		HBox dialogVbox = new HBox(5);
		// creating a button for saving the file.
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					// call saveQuestionFile for writing a file.
					saveQuestionFile(questionList, "hello");
				} catch (IOException e) {
					e.printStackTrace();
				}
				dialog.close();
			}
		});
		// if the user clicks noSave button. Exit the app without saving anything.
		Button noSave = new Button("Exit Without Save");
		noSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		// Formating the dialog in an uniform way.
		dialogVbox.getChildren().addAll(save, noSave);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.setStyle("-fx-background-color: #778899");

		// Display the dialog scene
		Scene dialogScene = new Scene(dialogVbox, 400, 300);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	public static void main(String[] args) {
		launch(args);

	}
}
