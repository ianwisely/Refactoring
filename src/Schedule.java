import java.util.*;
import java.sql.*;

public class Schedule {
	
	private String name;	
	public ArrayList<Offering> offerings = new ArrayList<Offering>();	//renamed to offerings
	private boolean extraCreditsPermission = false;
	private int credits = 0;
	public static final int minCredits = 12;
	public static final int maxCredits = 18;
	
	public Schedule(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return "Schedule " + name + ": " + offerings;
	}
	
	public void addOffering(Offering offering) {
		credits += offering.getCourse().getCredits();
		offerings.add(offering);
	}

	public void allowStudentExtraCredits(boolean allow) {
		extraCreditsPermission = allow;
	}

	public List<String> analysis() {
		ArrayList<String> result = new ArrayList<String>();
		if (credits < minCredits)
			result.add("Too few credits");
		if (credits > maxCredits && !extraCreditsPermission)
			result.add("Too many credits");
		checkDuplicateCourses(result);
		checkOverlap(result);
		return result;
	}

	public void checkDuplicateCourses(ArrayList<String> analysis) {
		List<Course> courses = new ArrayList<Course>();
		for (Offering offering : offerings) {
			Course course = offering.getCourse();
			if (courses.contains(course))
				analysis.add("Same course twice - " + course.getName());
			courses.add(course);
		}
	}

	public void checkOverlap(ArrayList<String> analysis) {
		List<String> times = new ArrayList<String>();
		for (Offering offering : offerings) {
			StringTokenizer tokens = new StringTokenizer(offering.getDaysTimes(), ",");
			while (tokens.hasMoreTokens()) {
				String dayTime = tokens.nextToken();
				if (times.contains(dayTime))
					analysis.add("Course overlap - " + dayTime);
				times.add(dayTime);
			}
		}
	}
}