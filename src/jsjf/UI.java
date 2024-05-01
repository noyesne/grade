package jsjf;

//Imports
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font; 
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
//import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class UI extends Application
{
   
	public void start(Stage primaryStage) 
	{
      //Graphic Methods
     
      
      //Label, instructions on how to work the program
      Label instructions = new Label("\t \t Welcome! Please enter the RUN file path into the textfield! \n \tAlternatively, use the File Explore Button to navigate to the location of the Run File!");
      instructions.setFont(Font.font("Verdana",FontWeight.BOLD,24));
      

      
      //Label, Indicates the file Type as the input:
      Label run = new Label("RUN File:");
      run.setFont(Font.font("Verdana",FontWeight.BOLD,28));
      
      
      //Textfield, shows the file path to the run file
      TextField link = new TextField();
      link.setPrefWidth(500);
      link.setPrefHeight(30);
      
      
      //TextField, prints the progress of the calculations
      TextField results = new TextField();
      results.setAlignment(Pos.TOP_LEFT);
      results.setFont(Font.font("Verdana",22));
      results.setPrefWidth(100);
      results.setPrefHeight(325);
      
      //Initial Prompt
      results.setText("Please enter the .RUN file you wish to run");
      
      
      //Button, starts calculations
      Button calculate = new Button("Calculate");
      calculate.setMinWidth(100);
      calculate.setMinHeight(50);
      calculate.setFont(Font.font("Verdana",FontWeight.BOLD,28));

      //NEEDS TO BE FINISHED!!
      calculate.setOnAction(  e  -> {
      //Calculate button will NOT do anything unless there is a file path in "link"
      
      //Let's the user know it's moving fine    
      results.setText("WORKING...");
              
      // Whatever gets put into link, will be placed into string array, and the passed through the main method
      String[] arr = {link.getText()};    
      if(arr[0].contains(".run")){
      try{
         Main.main(arr);
         }catch(Exception ex){
            System.out.println("SOMETHING WENT WRONG:" + ex);
         }
         results.setText(Main.getSendOver());  
      }
      else{
          results.setText("PLEASE ENSURE THAT THE FILE CHOSEN IS A RUN FILE");
      }
      });
      
      

      //Opens what is essentially File Explorer
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("File Explorer. Remember, only RUN Files");
      
      
      //Button, Opens file explorer to gain access to RUN file
      Button fileExplore = new Button("Open File Explorer");
      fileExplore.setMinWidth(100);
      fileExplore.setMinHeight(50);
      fileExplore.setFont(Font.font("Verdana",FontWeight.BOLD,28));

      //Need to implement what the button does
      //When you press the button...
      fileExplore.setOnAction(  e  -> {
      
         //File class for getting the file path + File Explorer Opens
         File selectedFile = fileChooser.showOpenDialog(primaryStage);

         
         //Gets the file path of the desired file
         link.setText(selectedFile.getAbsolutePath());  //Look at this later
         
         //If there is already something in the textfield link, replace it
         
         if(link.getText() != null)
         {
            link.setText(null);
            link.setText(selectedFile.getAbsolutePath());
         }
         
         
         //"Link" textfield inputs in the chosen file's path
         
      }  ); //End of the button actions
           
     
      //Create the VBox, organizes the information
      HBox hBoxBut = new HBox(50);
      hBoxBut.setAlignment(Pos.BOTTOM_CENTER);
      hBoxBut.getChildren().add(calculate);
      hBoxBut.getChildren().add(fileExplore);
      
      
      //Create the HBox, has the RUN file insertion info here
      HBox hBox1 = new HBox(10);
      hBox1.getChildren().add(run);
      hBox1.getChildren().add(link);
      
      
      //Create the Vbox to keep everything organized
      VBox vBoxOrg = new VBox(30);
      vBoxOrg.getChildren().add(instructions);
      vBoxOrg.getChildren().add(hBox1);
      vBoxOrg.getChildren().add(hBoxBut);
      vBoxOrg.getChildren().add(results);
      
      
      //Pane that has the information on it
		Pane pane = new Pane();
      pane.getChildren().add(vBoxOrg);
      
      
      //Info to initialize the program
		Scene scene = new Scene(pane, 500, 500);
      
      
      //The window itself
		primaryStage.setTitle("GPA Statistic Calculator");
	    primaryStage.setScene(scene);
	    primaryStage.show();
       
	}
   

	public static void main(String[] args)
	   {
	      //args: argument passed in through the command line
         Application.launch(args);//Launches program
	   }  //End of main
   
}
