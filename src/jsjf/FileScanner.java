package jsjf;
/*
 * FileScanner Class
 * Author: Jack Noyes
 * Date: 2/9/2024
 * 
 * Purpose
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileScanner {
    private String filePath = "";
    private String filename = "";
    private String fileParent ="";
    private File file;
    private String sectionName = "";
    private String gpa = "";
    private int studentNumbers = 0;


    String name = "";

    public String[] classNames;

    //Non-Argument Constructor: Allows user to input the .RUN file they wish
    public FileScanner() throws FileNotFoundException{
    	
        Scanner kybd = new Scanner(System.in);
        
        System.out.println("Please enter the complete path of the .RUN file");
       
        filePath = kybd.nextLine();
        int length = 0;
        try{
            file = new File(filePath);
            setFileName(file);
            setFileParent(file);
            Scanner fileReader = new Scanner(file);
            while(fileReader.hasNext()){
                fileReader.nextLine();
                length++;
            }
            fileReader.close();
           
        }catch(Exception ex){
            System.err.println("Please make sure that the file is in the right directory and ends with .run");
        }
        classNames = new String[length-1];
        kybd.close();
    }
    //Constructor, File argument: This constructor will be primarily used for the .GRP & .SEC files that will be parsed through later
    public FileScanner(File f) throws FileNotFoundException{
        try{
            
            Scanner fileReader = new Scanner(f);
            int length = 0;
            while(fileReader.hasNext()){
                fileReader.nextLine();
                length++;
            }
            classNames = new String[length-1];
            fileReader.close();
        }catch(Exception ex){
            System.err.println("Please make sure that the file is in the right directory and ends with .run");
        }
 
    


    //Getter and Setters
    }

    
    public String getName(){
       return this.name; 
    }
    public void setFileParent(File f){
        this.fileParent = f.getParent();
    }
    public String getFileParent(){
        return this.fileParent;
    }
    public void setFileName(File f){
        this.filename = f.getName();
    }
    public String getFileName(){
        return this.filename;
    }

    public String[] getArray(){
        return classNames;
    }

    public String getSection(){
        return sectionName;
    }

    public double getGPA(){
        return Double.parseDouble(gpa);
    }

    public int getstudentNumbers(){
        return studentNumbers;
    }
 
    /*
     * Method: parseRunFile
     * Argument: File obj
     * Return: void
     * 
     * Purpose: This will get the individual .GRP files from the .RUN file 
     *          And this will be used to get the .SEC files form the .GRP files;
     * 
     * Notes: during the loop the hope is that the exact file can be found by using the fileParent
     * 
     */
    public void parseRunFile(File f)throws FileNotFoundException{
        Scanner fileReader = new Scanner(f);
        name = fileReader.nextLine();
        String parent = this.fileParent;

        int iterator = 0;
        while(fileReader.hasNext()){
        		classNames[iterator] = parent + "\\" + fileReader.nextLine();;
        		iterator++;
        }
        
        

        fileReader.close();
    }
    /*
     * Method: sectionParse
     * Arguments: File object
     * Purpose: To seperate part of the section file and put them in a 2D array
     * 
     * Structure of Array: 
     *      1st Col: Student Last Name
     *      2nd Col: Student First Name (caused by the ","  between the first and last name due how split works)
     *      2nd Col: Student ID
     *      3rd Col: Letter Grade
     */

    /**
     * @param f
     * @return STRING[][] 
     * @throws FileNotFoundException
     */
    public String[][] sectionParse(File f) throws FileNotFoundException{
        Scanner scan = new Scanner(f);
        String s = scan.nextLine();
        
        String[] temp = s.split(" ");
        this.sectionName = temp[0];
        
        gpa = temp[temp.length-1];
        int iterator = 0;
        
        while(scan.hasNextLine()){
        scan.nextLine();
          iterator++; 
        }
         
        
        scan.close();
        studentNumbers = iterator;
        
        
        Scanner input = new Scanner(f);
        input.nextLine();
        String[][] section = new String[iterator][3];
        for(int i = 0; i < iterator; i++){
            String x = input.nextLine();
            String[] array = x.split(",");
            
            section[i][0] = array[0] + "," + array[1];
            section[i][1] = array[2];
            section[i][2] = array[array.length-1]; //letterGrade
            
        }
        input.close();
        return section;
    }



       
    

    


}