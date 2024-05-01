package jsjf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsAnalyzer {
    // Map to store grades for each student
    private Map<String, List<Grade>> studentGradesMap;

    public StatisticsAnalyzer() {
        studentGradesMap = new HashMap<>();
    }

    // Method to add grades for a student
    public void addGrade(String studentId, Grade grade) {
        if (!studentGradesMap.containsKey(studentId)) {
            studentGradesMap.put(studentId, new ArrayList<>());
        }
        studentGradesMap.get(studentId).add(grade);
    }

    // Method to compute GPA for a section
    public double computeSectionGPA(List<Grade> grades, String[] names, String section, String[] ids) {
		Main m = new Main();
		
    	int count = 0;
    	double avg = 0.0;
    	int size = grades.size();
        while(count < grades.size()) {
        	
        	String name = names[count];
        	String id = ids[count];
        	Grade grade = grades.get(count);
        	String g = grade.getGrade();
        	char letter = g.charAt(1);
        	char symbol = g.charAt(2);;
        	
        	switch(letter) {
        	case 'A':
        		m.addA(name, id, section);
        		if(symbol == '-') {
        			avg += 3.7;
        			m.addElement(3.7);
        		}
        		else{
        			avg += 4.0;
        			m.addElement(4.0);
        		}
        		break;
        	case 'B':
        		if(symbol == '+') {
        			avg += 3.3;
        			m.addElement(3.3);
        		}
        		else if(symbol == '-') {
        			avg += 2.7;
        			m.addElement(2.7);
        		}
        		else {
        			avg += 3.0;
        			m.addElement(3.0);
        		}
        		break;
        	case 'C': 
        		if(symbol == '+') {
        			avg += 2.3;
        			m.addElement(2.3);
        		}
        		else if(symbol == '-') {
        			avg += 1.7;
        			m.addElement(1.7);
        		}
        		else {
        			avg += 2.0;
        			m.addElement(2.0);
        		}
        		break;
        	case 'D':
        		m.addF(name, id, section);
        		if(symbol == '+') {
        			avg += 1.3;
        			m.addElement(1.3);
        		}
        		else if(symbol == '-') {
        			avg += 0.7;
        			m.addElement(0.7);
        		}
        		else {
        			avg += 1.0;
        			m.addElement(1.0);
        		}
        		break;
        	case 'F':
        		m.addF(name,id,section);
        		avg += 0.0;
        		m.addElement(0.0);
        		
        		break;
        	default:
        		size--;
        		break;
        	}
        	count++;
        }
		
        return avg/size; // Placeholder, implement actual calculation
    }

    // Method to compute GPA for a group
    

    // Method to perform Z-test and determine if section GPA is significantly different than group GPA
    public boolean isSectionGPASignificantlyDifferent(double sectionGPA, double groupGPA, double groupStdDev) {
        double zScore = (sectionGPA - groupGPA) / (groupStdDev);
        return Math.abs(zScore) >= 2.0; // Significance level set to 2
    }

    // Method to calculate Z-score
    public double calculateZScore(double sectionGPA, double groupGPA, double groupStdDev) {
        return (sectionGPA - groupGPA) / (groupStdDev);
        
		//(sectionGPA - groupGPA)/(groupStdDev / Math.sqrt(numStudents))
    }

    // Other methods for analyzing grades and generating reports as per project requirements...

}
/*
class Section {
    private String sectionId;
    private List<Grade> grades;

    public Section(String sectionId, List<Grade> grades) {
        this.sectionId = sectionId;
        this.grades = grades;
    }

    public List<Grade> getGrades() {
        return grades;
    }
}
*/

class Grade {
   
    private String grade;

    public Grade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
}