import java.util.*;

public class Report {
	
	public Report() {
	}

	Hashtable<Integer, ArrayList<String>> offeringToName = new Hashtable<Integer, ArrayList<String>>();

	public void findStudentsAndTheirOfferings() throws Exception {
		List<Schedule> schedules = SchedulePersistence.all();
		for (Schedule schedule : schedules) {
			for (Offering offering : schedule.offerings) {
				populateStudentsAndTheirOfferingsList(schedule, offering);
			}
		}
	}

	private void populateStudentsAndTheirOfferingsList(Schedule schedule, Offering offering) {
		ArrayList<String> list = (ArrayList<String>)offeringToName.get(new Integer(offering.getId()));
		if (list == null) {
			list = new ArrayList<String>();
			offeringToName.put(new Integer(offering.getId()), list);
		}
		list.add(schedule.getName());
	}

	public void addOfferingToReport(StringBuffer buffer, ArrayList<String> list, Offering offering) {
		buffer.append(offering.getCourse().getName() + " " + offering.getDaysTimes() + "\n");
		for (String string : list) {
			buffer.append("\t" + string + "\n");
		}
	}

	public void generateReport(StringBuffer buffer) throws Exception {
		findStudentsAndTheirOfferings();
		ArrayList<Integer> offeringIds = (ArrayList<Integer>)offeringToName.keys();
		for (Integer offeringId : offeringIds) {
			ArrayList<String> list = (ArrayList<String>)offeringToName.get(offeringId);
			addOfferingToReport(buffer, list, OfferingPersistence.find(offeringId.intValue()));
		}
		buffer.append("Number of scheduled offerings: ");
		buffer.append(offeringToName.size());
		buffer.append("\n");
	}
}