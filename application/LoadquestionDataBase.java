package application;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title: (LoadquestionDataBase.java)
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoadquestionDataBase {
  // after parse the json, where to put, how to create a database?

  // fields
  ArrayList<Question> storeQuestion = new ArrayList<Question>(); // creating a data field for
   ArrayList<Boolean> isCorrect = new ArrayList<>();                                                        // storing the questions.


  /**
   * Reading the json file and parsing the information it contains
   * 
   * @param jsonFilepath
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   */
  public List<Question> constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {

    // adding them
    Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
    JSONObject jo = (JSONObject) obj;
    JSONArray packages = (JSONArray) jo.get("questionArray");

    // parse the json file based on info in each array.
    for (int i = 0; i < packages.size(); ++i) {
      JSONObject jsonpackage = (JSONObject) packages.get(i);

      JSONArray choiceArray = (JSONArray) jsonpackage.get("choiceArray");


      // Store the element in json array into Arraylist.
      ArrayList<String> choices = new ArrayList<String>();

      ArrayList<Boolean> isCorrect = new ArrayList<>();

      // Add every predecessor vertex of packageName2 and the edges between them into the graph.
      for (int j = 0; j < choiceArray.size(); ++j) {
        // add the vertex into the adjacency list.
        JSONObject jsonChoicePackage =(JSONObject) choiceArray.get(j);
         String Singlechoice = (String)jsonChoicePackage.get("choice");
          // Adjacency lists add all depends no need to check duplication.
        choices.add(Singlechoice);
        String TrueorFalse = (String)jsonChoicePackage.get("isCorrect");
        if (TrueorFalse.equals("T")) 
          isCorrect.add(true);
        else 
        	isCorrect.add(false);
      }

      // sets actual variables
      String questionMetaData = (String) jsonpackage.get("meta-data");
      String questionText = (String) jsonpackage.get("questionText");
      String topic = (String) jsonpackage.get("topic");
      String image = (String) jsonpackage.get("image");

      // add the successor vertices into graph.
      // create the array list hold the dependency list (value) list.
      storeQuestion
          .add(new Question(questionMetaData, questionText, topic, image, choices, isCorrect));

      // System.out.println("LOAD" + storeQuestion.get(0).topic);
    }
    return storeQuestion;
  }

  public int getNumQuestion(List<Question> storeQuestion) {
    return storeQuestion.size();
  }
}
