import java.util.*;

public class Report {
	
	public Report() {
	}

	private Hashtable<Integer, ArrayList<String>> offerings = new Hashtable<Integer, ArrayList<String>>();

	public void findStudentsAndTheirOfferings() throws Exception {
		List<Schedule> schedules = SchedulePersistence.all();
		for (Schedule schedule : schedules) {
			for (Offering offering : schedule.offerings) {
				populateStudentsAndTheirOfferingsList(schedule, offering);
			}
		}
	}

	private void populateStudentsAndTheirOfferingsList(Schedule schedule, Offering offering) {
		ArrayList<String> students = (ArrayList<String>)offerings.get(offering.getId());
		if (students == null) {
			students = new ArrayList<String>();
			offerings.put(new Integer(offering.getId()), students);
		}
		students.add(schedule.getName());
	}

	public void addOfferingToReport(StringBuffer buffer, ArrayList<String> students, Offering offering) {
		buffer.append(offering.getCourse().getName() + " " + offering.getDaysTimes() + "\n");
		for (String string : students) {
			buffer.append("\t" + string + "\n");
		}
	}

	public void generateReport(StringBuffer buffer) throws Exception {
		findStudentsAndTheirOfferings();
		Enumeration<Integer> enumeration  = (Enumeration<Integer>)offerings.keys();
		while (enumeration.hasMoreElements()) {
			Integer offeringId = (Integer) enumeration.nextElement();
			ArrayList<String> students = (ArrayList<String>)offerings.get(offeringId);
			addOfferingToReport(buffer, students, OfferingPersistence.find(offeringId.intValue()));
		}
		buffer.append("Number of scheduled offerings: ");
		buffer.append(offerings.size());
		buffer.append("\n");
	}
}