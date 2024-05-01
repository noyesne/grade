package jsjf;

import java.io.*;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

/*
 * MAIN FILE FOR PROJECT
 * 
 * ADD MORE COMMENTS HERE
 * 
 */


public class Main {
	/*
	 * These are the global Variables used through this class and are at subject to change
	 */

    private static String groupName = ""; 
    private static String fileParent = "";
    private static String sectionName ="";
    private static String runName = ""; 
    private static double[][] sec_avg; //stores the section gpas in a column and the next column is for the next group
    private static LinkedList<Double> individual; //this gets the individual students grade (has to be reset after every run
    public static LinkedList<StudentNode> firstaList = new LinkedList<StudentNode>(); //stores all students that have an A in a group
    public static LinkedList<StudentNode> firstfList = new LinkedList<StudentNode>(); //stroes all Students that have an F in a group
    public static LinkedList<StudentNode> aList = new LinkedList<StudentNode>(); //stores all students that have more than one A in a Group
    public static LinkedList<StudentNode> fList = new LinkedList<StudentNode>(); //stores all students that have more than one F in a Group
    private static String [] studentNums; //Stores Section Names
    private static double sectionGPA;
    private static double sectionCH;
    private static double groupGPA;
    private static int totalStudents;
    private static double totalCH;
    private static int piterator = 0; //this iterates though "sec_avg[][]"'s columns 
    private static StatisticsAnalyzer stats = new StatisticsAnalyzer(); //used in runStats() and computeZScore()
    public static int counter = 1; //iterator through the main loop of the program
    public static final int maxRuns = 10; // max amount of runs 
    public static boolean tempCatch = false;
    public static boolean aCatch = false;
    public static boolean fCatch = false;
    public static String tempSec = "";
    private static String sendOver;
    
    
    /*
     * THIS METHOD SHOULD BE THE ONE RAN FOR THE INTERFACE
     */
    
    
    
    public static void main(String[] args) throws FileNotFoundException {
    	Scanner input = new Scanner(System.in);
    	
     
    	//This part is made 
    	
    	System.out.println("Please enter the .RUN file you wish to run"); //YOU CAN TAKE THIS LINE
    	String s = args[0];
    	System.out.println("WORKING....");//YOU CAN TAKE THIS LINE
    	
        String[] arr = setRunFile(s); //This gets the Array of .grp files
       
        
        try {
        	FileWriter fw = new FileWriter(fileParent + "\\Output"+ counter + ".txt");
        	
        	fw.write(runName + "\n\n\n\n");
        for(int x = 0; x < arr.length; x++){ //runs for the amount of files there are within the .run
        totalCH = 0;
        groupGPA = 0;
        individual = new LinkedList<Double>(); //this is essential to make sure that totalStudents would reset with every run
        String[] grpArray = runGRP(arr[x]); // makes an array of SEC in the x-th spot in the RUN array
        //System.out.println("Group Name: " + groupName + "\n"); 
        sec_avg = new double[grpArray.length][10]; //?
        studentNums = new String[grpArray.length]; //stores the name of each section in the grp. 
        for(int i = 0; i < grpArray.length; i++) { //loops through the length of the GRP file
        	sectionGPA = 0.0;
	        String[][] secArray = runSEC(grpArray[i]); //making a 2D array for the student's Name[i][0], student's ID [i][1], and student's letter grade [i][2];
	        
           
            
	        String[] grades = new String[secArray.length]; // stores all of the letter grades
            String[] names = new String[secArray.length]; // will store all of the names and will be used for the A and F students
            String[] ids = new String[secArray.length]; //will store id of the students 
            
            /*
             * This loops is responisble for filling the paramaters for runStats, and the whole program depends on runStats running correctly
             */
            
	        for(int j = 0; j < secArray.length; j++){ //stores grades and names in respective array
	        	grades[j] = secArray[j][2];
                ids[j] = secArray[j][1];
                names[j] = secArray[j][0];
	            }
	        
	      
	        
	        
	        
	        studentNums[i] = sectionName;  //stores each individual section name for the purposes of formatting the output file
	        
            runStats(ids, names, grades, sectionName); 
            
            
            groupGPA += sectionGPA * sectionCH;
            totalCH += sectionCH;
	        
            sec_avg[i][piterator] = sectionGPA; 
            
        
            }
        	double gpaAvg = groupGPA/totalCH; //gets the groups GPA, sorry for the bad naming was so far in to the program I really couldn't change it. 
        	
           // System.out.println(gpaAvg);
            double stDev = standardDeviation(gpaAvg); //Standarad Deviation of the Sections compared to the Group
            
            //FROM HERE IS MOSTLY JUST FOMATING THE FILE WRITING PORTION
            
            
            	fw.write("GROUP: " + groupName + " GPA: " + String.format("%,.2f", gpaAvg) + " \n");
				fw.write("Total Significant (NOT “I”, “W”, “P”, “NP” ) Students: " + totalStudents + "\nSTDEV: "  + stDev + "\n");
				
				for(int k = 0; k < sec_avg.length; k++) {// used to go through and pull out individual section grades
	                fw.write(studentNums[k] + " GPA: " +  String.format("%,.2f",sec_avg[k][piterator]));
	                double z = stats.calculateZScore(sec_avg[k][piterator], gpaAvg, stDev );
	                if(z >= 2.0){
	                	fw.write(" | Z Score: " + z + " | ");
	                    fw.write("Significantly different: TRUE\n");
	                    
	                }else{
	                    if(z<= -2.0){
	                    	fw.write(" | Z Score: " + z + " | ");
	                    	fw.write("Significantly different: TRUE\n");     
	                       
	                    }else {
	                    	fw.write("\n");
	                    }
	                }
	                
	        		//System.out.println("Section "+ studentNums[k] + " is: " + stats.isSectionGPASignificantlyDifferent(sec_avg[k][iterator], gpaAvg, stDev, totalStudents));
	        	}
				fw.write("\n");
	        	
	        	aList = fixList(firstaList); //this takes the list created in the runStats method and fixes it to see only the student more tha
	        	aCatch = tempCatch; //this is to catch a certain error and promotes the message below.
	        	if(aCatch) {
        			fw.write("There was an error in the file which occurred when the same student was in the same section with the same grade\nPlease check " + tempSec +" and make sure that the file has correct data\n\n");
        		}
	        	if(aList.isEmpty()) {
	        		fw.write("There were no students in " + groupName + " that had more than one A(+ or -)\n");
	        	}else {
		        	fw.write("List of Students with more than one A(+ or -) in " + groupName + ": \n\n" );
		        	while(!aList.isEmpty()) {
		        		StudentNode st = aList.remove();
		        		String t = st.name + ": " + st.section;
		        	
		        		fw.write(t + " \n");
		        			
		        	}
		        	fw.write("\n");
	        	}
	        	tempCatch = false; //this is a reset of the variable
	        	fList = fixList(firstfList);
	        	fCatch = tempCatch;
	        	if(fCatch) {
        			fw.write("There was an error in the file which occurred when the same student was in the same sectioon with the same grade");
        		}
	        	if(fList.isEmpty()) {
	        		fw.write("There were no students in " + groupName + " that had more than one D(+ or -) or F \n");
	        	}else {
	        		fw.write("List of Students with more than one D(+ or -) or F in " + groupName + ": \n");
		        	while(!fList.isEmpty()) {
		        		
		        		StudentNode st  = fList.remove();
		        		String t = st.name +": "+st.section;
		        		fw.write(t + " \n");
		        		
		        	}
	        	}
	        	System.out.println("");
        	//end main loop
	        	fw.write("\n\n");
	        	piterator++; //piterator is used for the 2-D array 'sec_avg" as a way to go to the next column of the array
        	}
        	sendOver = "RUN COMPLETE!\n file is stored as " + fileParent + "\\Output" + counter + ".txt";
        	fw.close();
        	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		
			}
       ///THIS IS FOR THE LOOP IN THE CONSOLE BASED PROGRAM AND CAN BE TAKEN OUT OF THE UI PORTION PLESE CHECK 
        //LINE 6 AND CORRECT ACCORDINGLY
       	
        counter++;
    	
        input.close();
    }
    	
    
    
            
     public static String getSendOver(){
        return sendOver;
     }       
            
        
        	
            

                
        
        
        
    

    /*
     * Method: setRunFile
     * Arguments: String s
     * 
     * Purpose: This method will set the run file up by scanning 
     * through the .RUN file to get the respective GRP files. Does this by utilaizing the 
     * FileScanner class and its ParseRun(File f) method.  
     * 
     *  
     *
     * 
     * 
     * 
     */

    public static String[] setRunFile(String s){
        
       
        File f;
        FileScanner fScanner;
        String[] grpArray;
       
        try{
            
            String x = s;
            
            f = new File(x);
            runName = f.getName();
            
            fScanner = new FileScanner(f);

            fScanner.setFileParent(f);
            fileParent = fScanner.getFileParent();
            //String directory = fScanner.getFileParent();

            fScanner.parseRunFile(f);

            grpArray = fScanner.getArray();

            return grpArray;

            
        }catch(Exception ex){
            System.out.println("Please make sure that the file you wish to run exists\n"+ ex);
        }
        
        return grpArray = new String[0];
        
    }
    /*
     * runGRP: returns String[]
     * Arguments: String s
     * Purpose: TO create an array of all SEC file with their specific paths, 
     * uses fileScanner class's method of parseRunFile(File f) 
     *
     */
    public static String[] runGRP(String s)throws FileNotFoundException{
        String[] section; 
        File f = new File(s);
        FileScanner fileScanner = new FileScanner(f);
        fileScanner.setFileParent(f);
        fileScanner.parseRunFile(f);
        groupName = fileScanner.getName();

        section = fileScanner.getArray();

        return section;


        
    }
    
    /*
     * runSEC(String s): returns String[][]
     * Arguments: String s -> filePath as a string
     * Purpose: Uses the FileScanner's parseSection method to pull all of the information out of the 
     * section file with the returning array having the following structure.
     * 		sections[x][0] -> "LastName, FirstName"
     * 		sections[x][1] -> "111111" or student ID
     * 		sections[x][2] -> "A" or "F" or any letter grade
     * This method will also fill in the global varaible of sectionName and sectionCH
     */

    public static String[][] runSEC(String s) throws FileNotFoundException{
       
        File f = new File(s);
        FileScanner fileScanner = new FileScanner(f);
        
        String[][] sections = fileScanner.sectionParse(f);
        fileScanner.getstudentNumbers();
        sectionName = fileScanner.getSection();
        sectionCH = fileScanner.getGPA();
        
        //System.out.println("Section: " + fileScanner.getSection() + " Credit Hours: " + fileScanner.getGPA());
        return sections ; 
    }

    /*
     * runStats makes a list of type Grade(StatisticAnalyzer) by adding the grades from the 2D array maybe by sectionParse(FileScanner)
     * 			it then uses the StatisticAnalyzer function of computeSectionGPA with the names array and Grade list plugged in and adds it to the section GPA
     */
    public static void runStats(String[] ids, String[] names, String[] grades, String section){
        List<Grade> list = new LinkedList<Grade>();
          for(int i = 0; i < grades.length; i++) {
        	  Grade grade = new Grade(grades[i]);
        	  list.add(grade);
          }
          StatisticsAnalyzer stat = new StatisticsAnalyzer();
          sectionGPA += stat.computeSectionGPA(list, names, section, ids);
          
    }
    /*
     * STANDARD DEVIATION = Sqrt( SUM( (x-X)^2)/N)
     * Where: 
     * 		x is the sectionGPA
     * 		X is the groupGPA
     * 		N is the number of sections
     */
    public static double standardDeviation(double gpaAvg){
    // calculate the standard deviation
    
    double mean = gpaAvg;
    double standardDeviation = 0.0;
    int iterator = 0;
    totalStudents = individual.size();
    
    while(iterator < sec_avg.length) {
    	//System.out.println("STDEV TOTAL STUDENTS: " + sec_avg[iterator][piterator]);
    	double num = sec_avg[iterator][piterator];
    	double temp = Math.pow((num - mean), 2);
    	standardDeviation += temp;
    	iterator++;
    	
    }
   
    return Math.sqrt(standardDeviation / sec_avg.length);

    }
    
    //these 3 methods are called in the Statistic analyzer class as grades are being changed from letter to grade-point

    public void addElement(double e){
    	individual.add(e);
    }

    public void addA(String n, String s, String x){
    	StudentNode stnd = new StudentNode(n,s,x); //creates a new studnetNode (Name, ID, Section)
        firstaList.add(stnd);
      
    }
    public void addF(String n, String s, String x){
    	StudentNode stnd = new StudentNode(n,s,x); //creates a new studnetNode (Name, ID, Section)
        firstfList.add(stnd);
      
    }
    public static void setCatch(String s) {
    	tempCatch = true;
    	tempSec = s;
    }
    
    /*
     * Method: fixList -> returns LinkedList<StudentNode>
     * Arguments: LinkedList<StudentNode> l
     * 
     * Purpose:
     * 		This method finds the students that have more than one A or (D or F)
     * 		goes through the list and if the the student's ID is still in the list after removing the node
     * 		There is a problem in which some id's are being showed up in the same class and that error is being resolved by calling setCatch();
     * 
     * 
     * 
     */
    public static LinkedList<StudentNode> fixList(LinkedList<StudentNode> l){
    	LinkedList<StudentNode> temp = new LinkedList<StudentNode>();
    	LinkedList<String> checkList = new LinkedList<String>();
    	
    	while(!l.isEmpty()) {
    		int count = 1;
    		StudentNode check = l.remove();
    		String id = check.getID();
    		
    		
	    	while(count <= l.size()) {
	    		StudentNode tempNode2 = l.get(count-1);
	    		if(id.equalsIgnoreCase(tempNode2.getID())) {
	    			
	    			if(check.section.equalsIgnoreCase(tempNode2.section)){
	    				setCatch(check.section);
	    				check.setSection(check.section + ", " + tempNode2.section);
	    				temp.add(check);
	    				break;
	    			}
	    			
	    			if(checkList.contains(id)) {
	    				check.setSection(check.section + ", " + tempNode2.section);
    				}
	    			if(!checkList.contains(id)) {	
		    		check.setSection(check.section + ", " + tempNode2.section);
		    		temp.add(check);
		    		checkList.add(id);
	    				
	    			}
	    		}
	    		count++;
	    	}
	    	
    	}
    	return temp;
    	}
    	
    }







class StudentNode{
	String name;
	String id;
	String section;
	
	public StudentNode(String n, String s, String x) {
		this.name = n;
		this.id = s;
		this.section = x; 
	}
	
	public void setID(String s) {
		this.id =s;
	}
	
	public void setSection(String s) {
		this.section = s;
	}
	
	public String getID() {
		return this.id;
	}
	
	public String getSection() {
		return this.section;
	}
}



